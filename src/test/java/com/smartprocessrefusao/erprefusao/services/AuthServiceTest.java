package com.smartprocessrefusao.erprefusao.services;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.smartprocessrefusao.erprefusao.entities.User;
import com.smartprocessrefusao.erprefusao.services.exceptions.ForbiddenException;
import com.smartprocessrefusao.erprefusao.tests.UserFactory;

@ExtendWith(SpringExtension.class)
public class AuthServiceTest {

	@InjectMocks
	private AuthService service;

	@Mock
	private UserService userService;

	private User admin, selfClient, otherClient;

	@BeforeEach
	void setUp() throws Exception {
		admin = UserFactory.createAdminUser();
		selfClient = UserFactory.createCustomClientUser(1L, "luciano");
		otherClient = UserFactory.createCustomClientUser(2L, "ana");
	}

	@Test
	public void validateSelfOrAdminShouldDoNothingWhenAdminLogged() {

		when(userService.authenticated()).thenReturn(admin);

		Long userId = admin.getId();

		Assertions.assertDoesNotThrow(() -> {
			service.validateSelfOrAdmin(userId);
		});
	}

	@Test
	public void validateSelfOrAdminShouldDoNothingWhenSelfLogged() {

		when(userService.authenticated()).thenReturn(selfClient);

		Long userId = selfClient.getId();

		Assertions.assertDoesNotThrow(() -> {
			service.validateSelfOrAdmin(userId);
		});
	}

	@Test
	public void validateSelfOrAdminThrowsForbiddenExceptionWhenClientOtherLogged() {

		when(userService.authenticated()).thenReturn(selfClient);

		Long userId = otherClient.getId();

		Assertions.assertThrows(ForbiddenException.class, () -> {
			service.validateSelfOrAdmin(userId);
		});
	}

}
