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
import com.smartprocessrefusao.erprefusao.dto.TaxClassificationDTO;
import com.smartprocessrefusao.erprefusao.tests.TaxClassificationFactory;
import com.smartprocessrefusao.erprefusao.tests.TokenUtil;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class TaxClassificationResourceIT {

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
	private TaxClassificationDTO dto;

	@BeforeEach
	void setUp() throws Exception {

		existingId = 1L;

		dto = new TaxClassificationDTO(null, "Sucata de alumínio", 7602000);

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

		TaxClassificationDTO dto = new TaxClassificationDTO(null, "Sucata de alumínio", 7602000);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/taxclass").header("Authorization", "Bearer " + invalidToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnauthorized());
	}

	// 2 - Unauthorized
	@Test
	public void insertShouldReturn403WhenClientLogged() throws Exception {

		TaxClassificationDTO dto = new TaxClassificationDTO(null, "Sucata de alumínio", 7602000);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/taxclass").header("Authorization", "Bearer " + clientToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isForbidden());
	}

	// 3 - SortedByIdTaxClassification
	@Test
	public void findAllShouldReturnAllResourcesPageadTaxClassificationSortedById() throws Exception {

		ResultActions result = mockMvc.perform(get("/taxclass").header("Authorization", "Bearer " + adminToken)
				.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$[0].description").value("Sucata de alumínio"));
		result.andExpect(jsonPath("$[0].number").value(7602000));

	}

	// 4 - FindByIdTaxClassification
	@Test
	public void findByIdShouldReturnAddressWhenIdExistsAndAdminRole() throws Exception {
		mockMvc.perform(get("/taxclass/{id}", existingId).header("Authorization", "Bearer " + adminToken)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	// 5 - AdminLoggedAndCorrectData
	@Test
	public void insertShouldInsertResourceWhenAdminLoggedAndCorrectData() throws Exception {
		TaxClassificationDTO dto = TaxClassificationFactory.createTaxDTO();
		String jsonBody = objectMapper.writeValueAsString(dto);

		System.out.println("==== JSON enviado ====");
		System.out.println(jsonBody);

		MvcResult result = mockMvc
				.perform(post("/taxclass").header("Authorization", "Bearer " + adminToken).content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andReturn();

		String responseContent = result.getResponse().getContentAsString();
		System.out.println("==== RESPOSTA DO SERVIDOR ====");
		System.out.println(responseContent);

	}

	// 6 - AdminLoggedAndBlankDescriptionTaxClassification
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndBlankDescription() throws Exception {
		TaxClassificationDTO dto = new TaxClassificationDTO(null, "", 7602000);

		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/taxclass").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("description"));
		result.andExpect(jsonPath("$.errors[0].message").value("O campo nome deve ter entre 5 a 30 caracteres"));

	}

	// 7 - AdminLoggedAndInvalidDescriptionProdGroup
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndInvalidDescription() throws Exception {
		TaxClassificationDTO dto = new TaxClassificationDTO(null, "Sucata de alumínio", null);

		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/taxclass").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("number"));
		result.andExpect(jsonPath("$.errors[0].message").value("A numeração fiscal deve ser composta de 7 dígitos"));

	}

}
