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
import com.smartprocessrefusao.erprefusao.dto.InputDTO;
import com.smartprocessrefusao.erprefusao.tests.InputFactory;
import com.smartprocessrefusao.erprefusao.tests.TokenUtil;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class InputResourceIT {

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
	private InputDTO dto;

	@BeforeEach
	void setUp() throws Exception {

		existingId = 1L;

		dto = new InputDTO(null, "SCRAP", "Perfil de processo", (long) 1, "kg", (long) 1, "Sucata de alumínio", 7602000,
				(long) 1, "Sucata de alumínio");

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

		InputDTO dto = new InputDTO(null, "SCRAP", "Perfil de processo", (long) 1, "kg", (long) 1, "Sucata de alumínio",
				7602000, (long) 1, "Sucata de alumínio");
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/inputs").header("Authorization", "Bearer " + invalidToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnauthorized());
	}

	// 2 - Unauthorized
	@Test
	public void insertShouldReturn403WhenClientLogged() throws Exception {

		InputDTO dto = new InputDTO(null, "SCRAP", "Perfil de processo", (long) 1, "kg", (long) 1, "Sucata de alumínio",
				7602000, (long) 1, "Sucata de alumínio");
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/inputs").header("Authorization", "Bearer " + clientToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isForbidden());
	}

	// 3 - SortedByNameMaterial
	@Test
	public void findAllShouldReturnAllResourcesPageadMaterialSortedByName() throws Exception {

		ResultActions result = mockMvc.perform(get("/inputs/report").header("Authorization", "Bearer " + adminToken)
				.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].description").value("FILTRO - CAIXA FILTRO"));
		result.andExpect(jsonPath("$.content[1].description").value("GLP - GAS LIQUEFEITO"));
		result.andExpect(jsonPath("$.content[2].description").value("PAPEL SULFITE"));

	}

	// 4 - FindByIdMaterial
	@Test
	public void findByIdShouldReturnAddressWhenIdExistsAndAdminRole() throws Exception {
		mockMvc.perform(get("/inputs/{id}", existingId).header("Authorization", "Bearer " + adminToken)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	// 5 - AdminLoggedAndCorrectData
	@Test
	public void insertShouldInsertResourceWhenAdminLoggedAndCorrectData() throws Exception {
		InputDTO dto = InputFactory.createInputDTO();
		String jsonBody = objectMapper.writeValueAsString(dto);

		System.out.println("==== JSON enviado ====");
		System.out.println(jsonBody);

		MvcResult result = mockMvc
				.perform(post("/inputs").header("Authorization", "Bearer " + adminToken).content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andReturn();

		String responseContent = result.getResponse().getContentAsString();
		System.out.println("==== RESPOSTA DO SERVIDOR ====");
		System.out.println(responseContent);

	}

	// 6 - AdminLoggedAndInvalidDescriptionMaterial
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndInvalidDescription() throws Exception {
		InputDTO dto = new InputDTO(null, "SCRAP", "Pe", (long) 1, "kg", (long) 1, "Sucata de alumínio", 7602000,
				(long) 1, "Sucata de alumínio");

		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/inputs").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("description"));
		result.andExpect(jsonPath("$.errors[0].message").value("O campo descrição deve ter entre 3 a 30 caracteres"));

	}

	// 7 - AdminLoggedAndBlankDescriptionMaterial
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndBlankDescription() throws Exception {
		InputDTO dto = new InputDTO(null, "SCRAP", "", (long) 1, "kg", (long) 1, "Sucata de alumínio", 7602000,
				(long) 1, "Sucata de alumínio");

		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/inputs").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("description"));
		result.andExpect(jsonPath("$.errors[0].message").value("O campo descrição deve ter entre 3 a 30 caracteres"));

	}

	// 8 - AdminLoggedAndInvalidUnit
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndInvalidUnit() throws Exception {
		InputDTO dto = new InputDTO(null, "SCRAP", "Perfil de processo", null, "unidadeInvalid", (long) 1,
				"Sucata de alumínio", 7602000, (long) 1, "Sucata de alumínio");

		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/inputs").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("uomId"));
		result.andExpect(jsonPath("$.errors[0].message").value("Campo Unidade de Medida requerido"));

	}

	// 9 - AdminLoggedAndInvalidClassificationTax
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndInvalidClassificationTax() throws Exception {
		InputDTO dto = new InputDTO(null, "SCRAP", "Perfil de processo", (long) 1, "kg", null,
				"Tax Classification Invalid", 7602000, (long) 1, "Sucata de alumínio");

		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/inputs").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("taxClassId"));
		result.andExpect(jsonPath("$.errors[0].message").value("Campo Classificação Fiscal requerida"));

	}

	// 10 - AdminLoggedAndInvalidProdGroup
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndInvalidProdGroup() throws Exception {
		InputDTO dto = new InputDTO(null, "SCRAP", "Perfil de processo", (long) 1, "kg", (long) 1, "Sucata de alumínio",
				7602000, null, "Group Invalid");

		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/inputs").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("matGroupId"));
		result.andExpect(jsonPath("$.errors[0].message").value("Campo Grupo de Material requerido"));

	}

}
