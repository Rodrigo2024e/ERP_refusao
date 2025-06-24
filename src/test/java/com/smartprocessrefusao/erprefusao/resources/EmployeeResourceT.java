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
import com.smartprocessrefusao.erprefusao.dto.EmployeeDTO;
import com.smartprocessrefusao.erprefusao.tests.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EmployeeResourceT {

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
		
		clientUsername = "ana@gmail.com";
		clientPassword = "123456";
		adminUsername = "luciano@gmail.com";
		adminPassword = "123456";
		clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
		adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
		invalidToken = adminToken + "xpto"; // Simulates a wrong token
	}
//1

	@Test
	public void insertShouldReturn401WhenInvalidToken() throws Exception {

		EmployeeDTO dto = new EmployeeDTO(null, "Giovana", "giovana@gmail.com", "44-12345-7652", "00-0000-0000", "198.149.318-29", "20.533.347-45", true, (long) 1, "Produção", "Recebimento e classificação de sucata");
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/employees")
					.header("Authorization", "Bearer " + invalidToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnauthorized());
	}

//2	
	@Test
	public void insertShouldReturn403WhenClientLogged() throws Exception {

		EmployeeDTO dto = new EmployeeDTO(null, "Giovana", "giovana@gmail.com", "44-12345-7652", "00-0000-0000", "198.149.318-29", "20.533.347-45", true, (long) 1, "Producão", "Recebimento e classificação de sucata");
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/employees")
					.header("Authorization", "Bearer " + clientToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isForbidden());
	}
//3	
	@Test
	public void insertShouldInsertResourceWhenAdminLoggedAndCorrectData() throws Exception {

		EmployeeDTO dto = new EmployeeDTO(null, "Giovana", "giovana@gmail.com", "44-12345-7652", "00-0000-0000", "198.149.318-29", "20.533.347-45", true, (long) 1, "Produção", "Recebimento e classificação de sucata");
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/employees")
					.header("Authorization", "Bearer " + adminToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").exists()); 
		result.andExpect(jsonPath("$.name").value("Giovana"));
		result.andExpect(jsonPath("$.email").value("giovana@gmail.com"));
		result.andExpect(jsonPath("$.cellPhone").value("44-12345-7652"));
		result.andExpect(jsonPath("$.telephone").value("00-0000-0000"));
		result.andExpect(jsonPath("$.cpf").value("198.149.318-29"));
		result.andExpect(jsonPath("$.rg").value("20.533.347-45"));
		result.andExpect(jsonPath("$.sysUser").value("true"));
		result.andExpect(jsonPath("$.sectorId").value((long) 1));
		result.andExpect(jsonPath("$.nameSector").value("Produção"));
		result.andExpect(jsonPath("$.process").value("Recebimento e classificação de sucata"));
	}
//4
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndBlankName() throws Exception {

		EmployeeDTO dto = new EmployeeDTO(null, "    ", "giovana@gmail.com", "44-12345-7652", "00-0000-0000", "198.149.318-29", "20.533.347-45", true, (long) 1, "Produção", "Recebimento e classificação de sucata");
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/employees")
					.header("Authorization", "Bearer " + adminToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("name"));
		result.andExpect(jsonPath("$.errors[0].message").value("Campo requerido"));

	}
//5	
	@Test
	public void findAllShouldReturnAllResourcesPageable() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/employees")
					.header("Authorization", "Bearer " + adminToken)
					.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());


	}

//6
	@Test
	public void findAllShouldReturnAllResourcesSortedByName() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/employees")
					.header("Authorization", "Bearer " + adminToken)
					.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].name").value("Luciano R Carvalho"));
		result.andExpect(jsonPath("$.content[1].name").value("Marcos Paulo"));
		result.andExpect(jsonPath("$.content[2].name").value("Michele Souza"));
		result.andExpect(jsonPath("$.content[3].name").value("Pateta Souza"));
		result.andExpect(jsonPath("$.content[4].name").value("Zickey Mouse"));
	}
	
	
	
	
}
