package com.smartprocessrefusao.erprefusao.config.customgrant;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClaimAccessor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;

public class CustomPasswordAuthenticationProvider implements AuthenticationProvider {

	private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";
	private final OAuth2AuthorizationService authorizationService;
	private final UserDetailsService userDetailsService;
	private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;
	private final PasswordEncoder passwordEncoder;

	public CustomPasswordAuthenticationProvider(OAuth2AuthorizationService authorizationService,
			OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
			UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		
		Assert.notNull(authorizationService, "authorizationService cannot be null");
		Assert.notNull(tokenGenerator, "TokenGenerator cannot be null");
		Assert.notNull(userDetailsService, "UserDetailsService cannot be null");
		Assert.notNull(passwordEncoder, "PasswordEncoder cannot be null");
		this.authorizationService = authorizationService;
		this.tokenGenerator = tokenGenerator;
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		CustomPasswordAuthenticationToken customPasswordAuthenticationToken = (CustomPasswordAuthenticationToken) authentication;
		OAuth2ClientAuthenticationToken clientPrincipal = getAuthenticatedClientElseThrowInvalidClient(customPasswordAuthenticationToken);
		RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
		
		String username = customPasswordAuthenticationToken.getUsername();
		String password = customPasswordAuthenticationToken.getPassword();
		
		UserDetails userDetails = null;
		try {
			userDetails = userDetailsService.loadUserByUsername(username);
		} catch (UsernameNotFoundException e) {
			throw new OAuth2AuthenticationException("Invalid credentials");
		}
				
		if (!passwordEncoder.matches(password, userDetails.getPassword()) || !userDetails.getUsername().equals(username)) {
			throw new OAuth2AuthenticationException("Invalid credentials");
		}
		
		Set<String> authorizedScopes = userDetails.getAuthorities().stream()
				.map(scope -> scope.getAuthority())
				.filter(scope -> registeredClient.getScopes().contains(scope))
				.collect(Collectors.toSet());
		
		// --- INÍCIO DO CÓDIGO AJUSTADO ---
		
		// 1. Crie um novo token de autenticação com o UserDetails como principal.
		UsernamePasswordAuthenticationToken userAuthToken = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());

		// 2. Crie um novo contexto de segurança e defina o novo token de autenticação.
		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		securityContext.setAuthentication(userAuthToken);
		SecurityContextHolder.setContext(securityContext);
		
		// --- FIM DO CÓDIGO AJUSTADO ---
		
		//-----------TOKEN BUILDERS----------
		DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
				.registeredClient(registeredClient)
				.principal(userAuthToken) // AQUI DEVE SER USADO O NOVO TOKEN
				.authorizationServerContext(AuthorizationServerContextHolder.getContext())
				.authorizedScopes(authorizedScopes)
				.authorizationGrantType(new AuthorizationGrantType("password"))
				.authorizationGrant(customPasswordAuthenticationToken);
		
		OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient)
				.attribute(Principal.class.getName(), userAuthToken) // AQUI TAMBÉM DEVE SER USADO O NOVO TOKEN
				.principalName(userDetails.getUsername())
				.authorizationGrantType(new AuthorizationGrantType("password"))
				.authorizedScopes(authorizedScopes);
		
		//-----------ACCESS TOKEN----------
		OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
		OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
		if (generatedAccessToken == null) {
			OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
					"The token generator failed to generate the access token.", ERROR_URI);
			throw new OAuth2AuthenticationException(error);
		}

		OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
				generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(),
				generatedAccessToken.getExpiresAt(), tokenContext.getAuthorizedScopes());
		if (generatedAccessToken instanceof ClaimAccessor) {
			authorizationBuilder.token(accessToken, (metadata) ->
					metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, ((ClaimAccessor) generatedAccessToken).getClaims()));
		} else {
			authorizationBuilder.accessToken(accessToken);
		}
				
		OAuth2Authorization authorization = authorizationBuilder.build();
		this.authorizationService.save(authorization);
		
		return new OAuth2AccessTokenAuthenticationToken(registeredClient, userAuthToken, accessToken);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return CustomPasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

	private static OAuth2ClientAuthenticationToken getAuthenticatedClientElseThrowInvalidClient(Authentication authentication) {
		
		OAuth2ClientAuthenticationToken clientPrincipal = null;
		if (OAuth2ClientAuthenticationToken.class.isAssignableFrom(authentication.getPrincipal().getClass())) {
			clientPrincipal = (OAuth2ClientAuthenticationToken) authentication.getPrincipal();
		}
		if (clientPrincipal != null && clientPrincipal.isAuthenticated()) {
			return clientPrincipal;
		}
		throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
	}	
}

