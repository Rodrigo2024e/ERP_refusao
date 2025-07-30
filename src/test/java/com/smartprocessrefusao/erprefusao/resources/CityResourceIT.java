package com.smartprocessrefusao.erprefusao.resources;

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
import com.smartprocessrefusao.erprefusao.dto.CityDTO;
import com.smartprocessrefusao.erprefusao.tests.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CityResourceIT {

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

	@BeforeEach
	void setUp() throws Exception {

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

		CityDTO dto = new CityDTO(null, "Sorocoba", "SP", (long) 1, "São Paulo", "Brasil");
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/citiesS").header("Authorization", "Bearer " + invalidToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnauthorized());
	}

	// 2 - Unauthorized
	@Test
	public void insertShouldReturn403WhenClientLogged() throws Exception {

		CityDTO dto = new CityDTO(null, "Sorocoba", "SP", (long) 1, "São Paulo", "Brasil");
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/cities").header("Authorization", "Bearer " + clientToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isForbidden());
	}

	// 3 - Findall City
	@Test
	public void findAllShouldReturnAllResourcesList() throws Exception {
		ResultActions result = mockMvc.perform(
				get("/cities").header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());

	}

	// 4 - SortedByNameCity
	@Test
	public void findAllShouldReturnAllResourcesSortedByName() throws Exception {
		ResultActions result = mockMvc.perform(
				get("/cities").header("Authorization", "Bearer " + adminToken).contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$[0].nameCity").value("Campo Largo"));
		result.andExpect(jsonPath("$[1].nameCity").value("Guararema"));
		result.andExpect(jsonPath("$[2].nameCity").value("Maringá"));

	}

	// 5 - AdminLoggedAndCorrectData
	@Test
	public void insertShouldInsertResourceWhenAdminLoggedAndCorrectData() throws Exception {

		CityDTO dto = new CityDTO(null, "Sorocoba", "SP", (long) 26, "São Paulo", "Brasil");
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/cities").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.nameCity").value("Sorocoba"));
		result.andExpect(jsonPath("$.ufState").value("SP"));
		result.andExpect(jsonPath("$.idState").value((long) 26));
		result.andExpect(jsonPath("$.nameState").value("São Paulo"));
		result.andExpect(jsonPath("$.country").value("Brasil"));

	}

	// 6 - AdminLoggedAndBlankCity
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndBlankCity() throws Exception {

		CityDTO dto = new CityDTO(null, "", "SP", (long) 1, "São Paulo", "Brasil");
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/cities").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("nameCity"));
		result.andExpect(jsonPath("$.errors[0].message").value("O nome deve ter entre 5 a 100 caracteres"));

	}

	// 7 - AdminLoggedAndInvalidCity
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndInvalidCity() throws Exception {

		CityDTO dto = new CityDTO(null, "abcd", "SP", (long) 1, "São Paulo", "Brasil");
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/cities").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("nameCity"));
		result.andExpect(jsonPath("$.errors[0].message").value("O nome deve ter entre 5 a 100 caracteres"));

	}

}
