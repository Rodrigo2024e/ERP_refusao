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
import com.smartprocessrefusao.erprefusao.dto.MaterialGroupDTO;
import com.smartprocessrefusao.erprefusao.tests.MatGroupFactory;
import com.smartprocessrefusao.erprefusao.tests.TokenUtil;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class MaterialGroupResourceIT {

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
	private MaterialGroupDTO dto;

	@BeforeEach
	void setUp() throws Exception {

		existingId = 1L;

		dto = new MaterialGroupDTO(null, "Sucata de alumínio");

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

		MaterialGroupDTO dto = new MaterialGroupDTO(null, "Sucata de alumínio");
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/matGroups").header("Authorization", "Bearer " + invalidToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnauthorized());
	}

	// 2 - Unauthorized
	@Test
	public void insertShouldReturn403WhenClientLogged() throws Exception {

		MaterialGroupDTO dto = new MaterialGroupDTO(null, "Sucata de alumínio");
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/matGroups").header("Authorization", "Bearer " + clientToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isForbidden());
	}

	// 3 - SortedByIdMaterialGroup
	@Test
	public void findAllShouldReturnAllResourcesPageadMaterialGroupSortedById() throws Exception {

		ResultActions result = mockMvc.perform(get("/matGroups").header("Authorization", "Bearer " + adminToken)
				.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$[0].description").value("Sucata de alumínio"));
		result.andExpect(jsonPath("$[1].description").value("Insumos"));

	}

	// 4 - FindByIdProductGroup
	@Test
	public void findByIdShouldReturnAddressWhenIdExistsAndAdminRole() throws Exception {
		mockMvc.perform(get("/matGroups/{id}", existingId).header("Authorization", "Bearer " + adminToken)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	// 5 - AdminLoggedAndCorrectData
	@Test
	public void insertShouldInsertResourceWhenAdminLoggedAndCorrectData() throws Exception {
		MaterialGroupDTO dto = MatGroupFactory.createGroupDTO();
		String jsonBody = objectMapper.writeValueAsString(dto);

		System.out.println("==== JSON enviado ====");
		System.out.println(jsonBody);

		MvcResult result = mockMvc
				.perform(post("/matGroups").header("Authorization", "Bearer " + adminToken).content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andReturn();

		String responseContent = result.getResponse().getContentAsString();
		System.out.println("==== RESPOSTA DO SERVIDOR ====");
		System.out.println(responseContent);

	}

	// 6 - AdminLoggedAndBlankDescriptionMatGroup
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndBlankDescription() throws Exception {
		MaterialGroupDTO dto = new MaterialGroupDTO(null, "");

		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/matGroups").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("description"));
		result.andExpect(jsonPath("$.errors[0].message").value("O campo nome deve ter entre 3 a 30 caracteres"));

	}

	// 7 - AdminLoggedAndInvalidDescriptionProdGroup
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndInvalidDescription() throws Exception {
		MaterialGroupDTO dto = new MaterialGroupDTO(null, "ab");

		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/matGroups").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("description"));
		result.andExpect(jsonPath("$.errors[0].message").value("O campo nome deve ter entre 3 a 30 caracteres"));

	}

}
