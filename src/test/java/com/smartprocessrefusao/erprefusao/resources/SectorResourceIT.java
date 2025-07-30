package com.smartprocessrefusao.erprefusao.resources;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartprocessrefusao.erprefusao.dto.SectorDTO;
import com.smartprocessrefusao.erprefusao.tests.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SectorResourceIT {

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

	@BeforeEach
	void setUp() throws Exception {

		existingId = 1L;
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

		SectorDTO dto = new SectorDTO(null, "Produção", "Expedição");
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/sectors").header("Authorization", "Bearer " + invalidToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnauthorized());
	}

	// 2 - Unauthorized
	@Test
	public void insertShouldReturn403WhenClientLogged() throws Exception {

		SectorDTO dto = new SectorDTO(null, "Gerência Industrial", "Administração");
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/sectors").header("Authorization", "Bearer " + clientToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isForbidden());
	}

	// 3 - SortedByNameEmployee
	@Test
	public void findAllShouldReturnAllResourcesSortedByName() throws Exception {

		ResultActions result = mockMvc.perform(get("/sectors").header("Authorization", "Bearer " + adminToken)
				.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$[0].nameSector").value("Administração"));
		result.andExpect(jsonPath("$[1].nameSector").value("Administração"));
		result.andExpect(jsonPath("$[2].nameSector").value("Administração"));

	}

	// 4 - FindByIdExistsAndAdminRole
	@Test
	public void findByIdShouldReturnEmployeeWhenIdExistsAndAdminRole() throws Exception {
		mockMvc.perform(get("/sectors/{id}", existingId).header("Authorization", "Bearer " + adminToken)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	// 5 - AdminLoggedAndCorrectData
	@Test
	public void insertShouldResourceWhenAdminLoggedAndCorrectData() throws Exception {

		SectorDTO dto = new SectorDTO(null, "Gerência Industrial", "Administração");
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/sectors").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.nameSector").value("Gerência Industrial"));
		result.andExpect(jsonPath("$.process").value("Administração"));

	}
	// 6 - AdminLoggedAndBlankNameSector
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndBlankSector() throws Exception {

		SectorDTO dto = new SectorDTO(null, "", "Carregamento de fornos");
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/sectors").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[*].fieldName", hasItem("nameSector")));
		result.andExpect(jsonPath("$.errors[*].message", hasItem("O nome do setor deve ter entre 3 a 15 caracteres")));

	}
	
	// 7 - AdminLoggedAndBlankNameProcess
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndBlankProcess() throws Exception {

		SectorDTO dto = new SectorDTO(null, "Produção", "");
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/sectors").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[*].fieldName", hasItem("process")));
		result.andExpect(
				jsonPath("$.errors[*].message", hasItem("O nome do processo deve ter entre 3 a 16 caracteres")));

	}


}
