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
import com.smartprocessrefusao.erprefusao.dto.AddressDTO;
import com.smartprocessrefusao.erprefusao.dto.EmployeeDepartamentDTO;
import com.smartprocessrefusao.erprefusao.tests.AddressFactory;
import com.smartprocessrefusao.erprefusao.tests.TokenUtil;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class AddressResourceIT {

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
	private EmployeeDepartamentDTO employeeDTO;

	@BeforeEach
	void setUp() throws Exception {

		existingId = 1L;

		employeeDTO = new EmployeeDepartamentDTO(null, "Luciano R Carvalho", "luciano@gmail.com.br", "44-14244-1222",
				"44-1442-2222", "111.000.111-49", 1L, "Produção", "Recebimento e classificação de sucata");

		clientUsername = "michele@alunova.com";
		clientPassword = "123456";
		adminUsername = "luciano@alunova.com";
		adminPassword = "123456";
		clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
		adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
		invalidToken = adminToken + "xpto"; // Simulates a wrong token
	}

	// 1 - Token invalid
	@Test
	public void insertShouldReturn401WhenInvalidToken() throws Exception {

		AddressDTO dto = new AddressDTO(null, "Avenida sem fim", 62, "casa B", "Residencial Moreschi", "87.080-127",
				(long) 1, "São Roque", "SP", "São Paulo", "Brasil", (long) 3);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/addresses").header("Authorization", "Bearer " + invalidToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnauthorized());
	}

	// 2 - Unauthorized
	@Test
	public void insertShouldReturn403WhenClientLogged() throws Exception {

		AddressDTO dto = new AddressDTO(null, "Avenida sem fim", 62, "casa B", "Residencial Moreschi", "87.080-127",
				(long) 1, "São Roque", "SP", "São Paulo", "Brasil", (long) 3);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/addresses").header("Authorization", "Bearer " + clientToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isForbidden());
	}

	// 3 - SortedByNameCity
	@Test
	public void findAllShouldReturnAllResourcesPageadCitySortedByNameCity() throws Exception {

		ResultActions result = mockMvc.perform(get("/addresses/search").header("Authorization", "Bearer " + adminToken)
				.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].nameCity").value("Campo Largo"));
		result.andExpect(jsonPath("$.content[1].nameCity").value("Maringá"));
		result.andExpect(jsonPath("$.content[2].nameCity").value("Maringá"));
		result.andExpect(jsonPath("$.content[3].nameCity").value("Maringá"));
		result.andExpect(jsonPath("$.content[4].nameCity").value("Maringá"));
		result.andExpect(jsonPath("$.content[5].nameCity").value("Paiçandu"));
		result.andExpect(jsonPath("$.content[6].nameCity").value("Paiçandu"));

	}

	// 4 - AdminLoggedAndCorrectData
	@Test
	public void findByIdShouldReturnAddressWhenIdExistsAndAdminRole() throws Exception {
		mockMvc.perform(get("/employees/{id}", existingId).header("Authorization", "Bearer " + adminToken)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	// 5 - AdminLoggedAndCorrectData
	@Test
	public void insertShouldInsertResourceWhenAdminLoggedAndCorrectData() throws Exception {
		AddressDTO dto = AddressFactory.createAddressDTO();
		String jsonBody = objectMapper.writeValueAsString(dto);

		System.out.println("==== JSON enviado ====");
		System.out.println(jsonBody);

		MvcResult result = mockMvc
				.perform(post("/addresses").header("Authorization", "Bearer " + adminToken).content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andReturn();

		String responseContent = result.getResponse().getContentAsString();
		System.out.println("==== RESPOSTA DO SERVIDOR ====");
		System.out.println(responseContent);

	}

	// 6 - AdminLoggedAndInvalidStreet
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndInvalidStreet() throws Exception {
		AddressDTO dto = new AddressDTO(null, "ab", 62, "casa B", "Residencial Moreschi", "87.080-127", (long) 1,
				"São Roque", "SP", "São Paulo", "Brasil", (long) 3);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/addresses").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("street"));
		result.andExpect(jsonPath("$.errors[0].message").value("O nome deve ter entre 3 a 30 caracteres"));

	}

	// 7 - AdminLoggedAndBlankStreet
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndBlankStreet() throws Exception {
		AddressDTO dto = new AddressDTO(null, "", 62, "casa B", "Residencial Moreschi", "87.080-127", (long) 1,
				"São Roque", "SP", "São Paulo", "Brasil", (long) 3);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/addresses").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("street"));
		result.andExpect(jsonPath("$.errors[0].message").value("O nome deve ter entre 3 a 30 caracteres"));

	}

	// 8 - AdminLoggedAndNullNumber
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndNullNumber() throws Exception {
		AddressDTO dto = new AddressDTO(null, "Rua sem fim", null, "casa B", "Residencial Moreschi", "87.080-127",
				(long) 1, "São Roque", "SP", "São Paulo", "Brasil", (long) 3);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/addresses").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("numberAddress"));
		result.andExpect(jsonPath("$.errors[0].message").value("O numero é obrigatório"));

	}

	// 9 - AdminLoggedAndInvalidNeighborhood
	@Test
	public void insertShouldReturn422WhenAdminLoggedInvalidNeighborhood() throws Exception {
		AddressDTO dto = new AddressDTO(null, "Rua sem fim", 162, "casa B", "ab", "87.080-127", (long) 1, "São Roque",
				"SP", "São Paulo", "Brasil", (long) 3);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/addresses").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("neighborhood"));
		result.andExpect(jsonPath("$.errors[0].message").value("O nome deve ter entre 3 a 30 caracteres"));

	}

	// 10 - AdminLoggedAndInvalidZipCode
	@Test
	public void insertShouldReturn422WhenAdminLoggedInvalidZipCode() throws Exception {
		AddressDTO dto = new AddressDTO(null, "Rua sem fim", 162, "casa B", "Residencial Moreschi", "87080127",
				(long) 1, "São Roque", "SP", "São Paulo", "Brasil", (long) 8);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/addresses").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("zipCode"));
		result.andExpect(jsonPath("$.errors[0].message").value("O CEP deve estar no formato 00.000-000."));

	}

	// 11 - AdminLoggedAndBlankZipCode
	@Test
	public void insertShouldReturn422WhenAdminLoggedBlankZipCode() throws Exception {
		AddressDTO dto = new AddressDTO(null, "Rua sem fim", 162, "casa B", "Residencial Moreschi", "", (long) 1,
				"São Roque", "SP", "São Paulo", "Brasil", (long) 8);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/addresses").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("zipCode"));
		result.andExpect(jsonPath("$.errors[0].message").value("O CEP deve estar no formato 00.000-000."));

	}

	// 12 - AdminLoggedAndBlankCity
	@Test
	public void insertShouldReturn422WhenAdminLoggedNullCity() throws Exception {
		AddressDTO dto = new AddressDTO(null, "Rua sem fim", 162, "casa B", "Residencial Moreschi", "87.080-127", null,
				"São Roque", "SP", "São Paulo", "Brasil", (long) 3);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/addresses").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("cityId"));
		result.andExpect(jsonPath("$.errors[0].message").value("O campo cidade não pode ser nulo"));

	}

	// 13 - AdminLoggedAndBlankPeople
	@Test
	public void insertShouldReturn422WhenAdminLoggedNullPeople() throws Exception {
		AddressDTO dto = new AddressDTO(null, "Rua sem fim", 162, "casa B", "Residencial Moreschi", "87.080-127",
				(long) 1, "São Roque", "SP", "São Paulo", "Brasil", null);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/addresses").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("peopleId"));
		result.andExpect(jsonPath("$.errors[0].message").value("O campo pessoa não pode ser nulo"));

	}

}
