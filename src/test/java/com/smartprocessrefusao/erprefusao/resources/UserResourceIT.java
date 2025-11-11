package com.smartprocessrefusao.erprefusao.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartprocessrefusao.erprefusao.dto.UserDTO;
import com.smartprocessrefusao.erprefusao.tests.TokenUtil;
import com.smartprocessrefusao.erprefusao.tests.UserFactory;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class UserResourceIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TokenUtil tokenUtil;

	private String clientUsername;
	private String clientPassword;
	private String adminUsername;
	private String adminPassword;
	private String clientToken;
	private String adminToken;
	private String invalidToken;
	private Long existingId;

	@SuppressWarnings("unused")
	private UserDTO dto;

	@BeforeEach
	void setUp() throws Exception {

		existingId = 1L;

		dto = new UserDTO(null, "nomeUsuario", (long) 1, "João Carlos");

		clientUsername = "michele@alunova.com";
		clientPassword = "123456";
		adminUsername = "luciano@alunova.com";
		adminPassword = "123456";
		clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
		adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
		invalidToken = adminToken + "xpto";
	}

	// 1 - Token invalid
	@Test
	public void insertShouldReturn401WhenInvalidToken() throws Exception {

		UserDTO dto = new UserDTO(null, "nomeUsuario", (long) 1, "João Carlos");
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/users").header("Authorization", "Bearer " + invalidToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnauthorized());
	}

	// 2 - Unauthorized
	@Test
	public void insertShouldReturn403WhenClientLogged() throws Exception {

		UserDTO dto = new UserDTO(null, "nomeUsuario", (long) 1, "João Carlos");
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/users").header("Authorization", "Bearer " + clientToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isForbidden());
	}

	// 3 - SortedByIdUnit
	@Test
	public void findAllShouldReturnAllResourcesPageadUnitSortedById() throws Exception {

		ResultActions result = mockMvc.perform(
				get("/users").header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].email").value("luciano@alunova.com"));
		result.andExpect(jsonPath("$.content[0].employee_id").value(1));
		result.andExpect(jsonPath("$.content[0].nameEmployee").value("Luciano Rodrigo de Carvalho"));
		result.andExpect(jsonPath("$.content[1].email").value("michele@alunova.com"));
		result.andExpect(jsonPath("$.content[1].employee_id").value(3));
		result.andExpect(jsonPath("$.content[1].nameEmployee").value("Michele Pereira da Silva"));
		
	}

	// 4 - FindByIdEmail
	@Test
	public void findByIdShouldReturnAddressWhenIdExistsAndAdminRole() throws Exception {
		mockMvc.perform(get("/users/{id}", existingId).header("Authorization", "Bearer " + adminToken)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	// 5 - AdminLoggedAndCorrectData
	@Test
	public void insertShouldInsertResourceWhenAdminLoggedAndCorrectData() throws Exception {
		UserDTO dto = UserFactory.createUserInsertDTO();
		String jsonBody = objectMapper.writeValueAsString(dto);

		System.out.println("==== JSON enviado ====");
		System.out.println(jsonBody);

		MvcResult result = mockMvc
				.perform(post("/users").header("Authorization", "Bearer " + adminToken).content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andReturn();

		String responseContent = result.getResponse().getContentAsString();
		System.out.println("==== RESPOSTA DO SERVIDOR ====");
		System.out.println(responseContent);

	}

	// 6 - AdminLoggedAndBlankEmail
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndBlankEmail() throws Exception {
		UserDTO dto = new UserDTO(null, "nomeUsuario", (long) 1, "João Carlos");

		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/users").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("email"));
		result.andExpect(jsonPath("$.errors[0].message").value("must not be blank"));

	}

	// 7 - AdminLoggedAndInvalidDescriptionUser
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndInvalidDescription() throws Exception {
		UserDTO dto = new UserDTO(null, "nomeUsuario", (long) 1, "João Carlos");

		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/users").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("email"));
		result.andExpect(jsonPath("$.errors[0].message").value("must not be blank"));

	}

}
