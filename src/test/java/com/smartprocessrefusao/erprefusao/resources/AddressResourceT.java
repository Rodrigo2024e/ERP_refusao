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
import com.smartprocessrefusao.erprefusao.cadastros.dto.AddressDTO;
import com.smartprocessrefusao.erprefusao.cadastros.dto.EmployeeDTO;
import com.smartprocessrefusao.erprefusao.tests.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AddressResourceT {

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
	
	private EmployeeDTO employee;
	
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
		
		AddressDTO dto = new AddressDTO(null, "Avenida sem fim", 62, "casa B", "Residencial Moreschi", "87.080-127", (long) 1, "São Roque", "SP",  (long) 3);
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/enderecos")
					.header("Authorization", "Bearer " + invalidToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnauthorized());
	}
	
//2	
	@Test
	public void insertShouldReturn403WhenClientLogged() throws Exception {
		
		AddressDTO dto = new AddressDTO(null, "Avenida sem fim", 62, "casa B", "Residencial Moreschi", "87.080-127", (long) 1, "São Roque", "SP",  (long) 6);
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/address")
					.header("Authorization", "Bearer " + clientToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isForbidden());
	}

//3	
/*
	@Test
	public void insertShouldInsertResourceWhenAdminLoggedAndCorrectData() throws Exception {
		
		employee = new EmployeeDTO();
		AddressDTO dto = new AddressDTO(null, "Avenida sem fim", 62, "casa B", "Residencial Moreschi", "87.080-127", (long) 1, "São Roque", "SP",  (long) 6);
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/address")
					.header("Authorization", "Bearer " + adminToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").exists()); 
		result.andExpect(jsonPath("$.street").value("Rua Salomão Miguel Nasser"));
		result.andExpect(jsonPath("$.numberAddress").value(731));
		result.andExpect(jsonPath("$.complement").value("AP23"));
		result.andExpect(jsonPath("$.neighborhood").value("Guatupê"));
		result.andExpect(jsonPath("$.zipCode").value("87.080-127"));
		result.andExpect(jsonPath("$.cityId").value((long) 3));
		result.andExpect(jsonPath("$.name").value("Curitiba"));
		result.andExpect(jsonPath("$.state").value("Paraná"));
		result.andExpect(jsonPath("$.uf").value("PR"));
		result.andExpect(jsonPath("$.country").value("Brasil"));
		result.andExpect(jsonPath("$.peopleId").value((long) 6));

	}
*/	
//4
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndBlankAddress() throws Exception {

		AddressDTO dto = new AddressDTO(null, " ", 62, "casa B", "Residencial Moreschi", "87.080-127", (long) 1, "São Roque", "SP", (long) 3);
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/address")
					.header("Authorization", "Bearer " + adminToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("street"));
		result.andExpect(jsonPath("$.errors[0].message").value("O nome deve ter entre 5 a 30 caracteres"));		

	}
	
//5	
	@Test
	public void findAllShouldReturnAllResourcesPageable() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/address")
					.header("Authorization", "Bearer " + adminToken)
					.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());

	}

	@Test
	public void findAllShouldReturnAllResourcesPageadCitySortedByName() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/address")
					.header("Authorization", "Bearer " + adminToken)
					.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].street").value("Rua Argentino Moreschi"));
		result.andExpect(jsonPath("$.content[1].street").value("Rua Salomão Miguel Nasser"));
		result.andExpect(jsonPath("$.content[2].street").value("Rua Governador Carvalho Pinto"));
	}
	
}
