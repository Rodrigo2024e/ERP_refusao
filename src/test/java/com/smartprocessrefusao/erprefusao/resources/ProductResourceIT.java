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
import com.smartprocessrefusao.erprefusao.dto.ProductDTO;
import com.smartprocessrefusao.erprefusao.tests.ProductFactory;
import com.smartprocessrefusao.erprefusao.tests.TokenUtil;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class ProductResourceIT {

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
	private ProductDTO dto;

	@BeforeEach
	void setUp() throws Exception {

		existingId = 1L;

		dto = new ProductDTO(null, "FINISHED_PRODUCTS", "Tarugo de Aluminio", 6060, 6, 6.0, (long) 1, "kg", (long) 1,
				"Tarugo de alumínio", 7604000, (long) 1, "Produto acabado");

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

		ProductDTO dto = new ProductDTO(null, "FINISHED_PRODUCTS", "Tarugo de Aluminio", 6060, 6, 6.0, (long) 1, "kg", (long) 1,
				"Tarugo de alumínio", 7604000, (long) 1, "Produto acabado");
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/products").header("Authorization", "Bearer " + invalidToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnauthorized());
	}

	// 2 - Unauthorized
	@Test
	public void insertShouldReturn403WhenClientLogged() throws Exception {

		ProductDTO dto = new ProductDTO(null, "FINISHED_PRODUCTS", "Tarugo de Aluminio", 6060, 6, 6.0, (long) 1, "kg", (long) 1,
				"Tarugo de alumínio", 7604000, (long) 1, "Produto acabado");
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/products").header("Authorization", "Bearer " + clientToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isForbidden());
	}

	// 3 - SortedByNameProduct
	@Test
	public void findAllShouldReturnAllResourcesPageadProductSortedByName() throws Exception {

		ResultActions result = mockMvc.perform(get("/products/report").header("Authorization", "Bearer " + adminToken)
				.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].description").value("Tarugo de alumínio"));
		result.andExpect(jsonPath("$.content[0].alloy").value(6005));
		result.andExpect(jsonPath("$.content[1].description").value("Tarugo de alumínio"));
		result.andExpect(jsonPath("$.content[1].alloy").value(6060));

	}

	// 4 - FindByIdProduct
	@Test
	public void findByIdShouldReturnProductWhenIdExistsAndAdminRole() throws Exception {
		mockMvc.perform(get("/products/6", existingId).header("Authorization", "Bearer " + adminToken)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	// 5 - AdminLoggedAndCorrectData
	@Test
	public void insertShouldInsertResourceWhenAdminLoggedAndCorrectData() throws Exception {
		ProductDTO dto = ProductFactory.createProductDTO();
		String jsonBody = objectMapper.writeValueAsString(dto);

		System.out.println("==== JSON enviado ====");
		System.out.println(jsonBody);

		MvcResult result = mockMvc
				.perform(post("/Products").header("Authorization", "Bearer " + adminToken).content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andReturn();

		String responseContent = result.getResponse().getContentAsString();
		System.out.println("==== RESPOSTA DO SERVIDOR ====");
		System.out.println(responseContent);

	}

	// 6 - AdminLoggedAndInvalidDescriptionProduct
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndInvalidDescription() throws Exception {
		ProductDTO dto = new ProductDTO(null, "FINISHED_PRODUCTS", "Ta", 6060, 6, 6.0, (long) 1, "kg", (long) 1,
				"Tarugo de alumínio", 7604000, (long) 1, "Produto acabado");

		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/products").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("description"));
		result.andExpect(jsonPath("$.errors[0].message").value("O campo descrição deve ter entre 3 a 30 caracteres"));

	}

	// 7 - AdminLoggedAndBlankDescriptionProduct
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndBlankDescription() throws Exception {
		ProductDTO dto = new ProductDTO(null, "FINISHED_PRODUCTS", ""
				+ "", 6060, 6, 6.0, (long) 1, "kg", (long) 1,
				"Tarugo de alumínio", 7604000, (long) 1, "Produto acabado");

		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/products").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("description"));
		result.andExpect(jsonPath("$.errors[0].message").value("O campo descrição deve ter entre 3 a 30 caracteres"));

	}

}
