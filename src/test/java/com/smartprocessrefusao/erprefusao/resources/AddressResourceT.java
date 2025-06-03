package com.smartprocessrefusao.erprefusao.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartprocessrefusao.erprefusao.dto.AddressDTO;
import com.smartprocessrefusao.erprefusao.dto.EmployeeDTO;
import com.smartprocessrefusao.erprefusao.entities.People;
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
	
	@Mock
	private People people;
	
	@SuppressWarnings("unused")
	private EmployeeDTO employee;
	
	@BeforeEach
	void setUp() throws Exception {
		
		employee = new EmployeeDTO(7L, "Luciano R Carvalho","luciano@gmail.com.br", "44-14244-1222","44-1442-2222", "111.000.111-49", "10.113.147.42", true, 1L, "Produção", "Recebimento e classificação de sucata");
	
		clientUsername = "ana@gmail.com";
		clientPassword = "123456";
		adminUsername = "luciano@gmail.com";
		adminPassword = "123456";
		clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
		adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
		invalidToken = adminToken + "xpto"; // Simulates a wrong token
	}
//ok
	@Test
	public void insertShouldReturn401WhenInvalidToken() throws Exception {
		
		AddressDTO dto = new AddressDTO(null, "Avenida sem fim", 62, "casa B", "Residencial Moreschi", "87.080-127", (long) 1, "São Roque", "SP", "São Paulo", "Brasil",  (long) 3);
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/addresses")
					.header("Authorization", "Bearer " + invalidToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnauthorized());
	}
	
//
	@Test
	public void insertShouldReturn403WhenClientLogged() throws Exception {
		
		AddressDTO dto = new AddressDTO(null, "Avenida sem fim", 62, "casa B", "Residencial Moreschi", "87.080-127", (long) 1, "São Roque", "SP", "São Paulo", "Brasil",  (long) 3);
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/addresses")
					.header("Authorization", "Bearer " + clientToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isForbidden());
	}

	@Test
	public void insertShouldInsertResourceWhenAdminLoggedAndCorrectData() throws Exception {
		
		AddressDTO dto = new AddressDTO(null, "Avenida sem fim", 62, "casa B", "Residencial Moreschi", "87.080-127", (long) 1, "São Roque", "SP", "São Paulo", "Brasil",  (long) 7);
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/addresses")
					.header("Authorization", "Bearer " + adminToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.idAddress").exists()); 
		result.andExpect(jsonPath("$.street").value("Avenida sem fim"));
		result.andExpect(jsonPath("$.numberAddress").value(62));
		result.andExpect(jsonPath("$.complement").value("casa B"));
		result.andExpect(jsonPath("$.neighborhood").value("Residencial Moreschi"));
		result.andExpect(jsonPath("$.zipCode").value("87.080-127"));
		result.andExpect(jsonPath("$.cityId").value((long) 1));
		result.andExpect(jsonPath("$.nameCity").value("São Roque"));
		result.andExpect(jsonPath("$.ufState").value("SP"));
		result.andExpect(jsonPath("$.nameState").value("São Paulo"));
		result.andExpect(jsonPath("$.country").value("Brasil"));
		result.andExpect(jsonPath("$.people_id").value((long) 7));

	}

//4
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndBlankAddress() throws Exception {

		AddressDTO dto = new AddressDTO(null, "", 62, "casa B", "Residencial Moreschi", "87.080-127", (long) 1, "São Roque", "SP", "São Paulo", "Brasil",  (long) 3);
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/addresses")
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
				mockMvc.perform(get("/addresses")
					.header("Authorization", "Bearer " + adminToken)
					.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());

	}

	@Test
	public void findAllShouldReturnAllResourcesPageadCitySortedByName() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/addresses")
					.header("Authorization", "Bearer " + adminToken)
					.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].nameCity").value("Maringá"));
		result.andExpect(jsonPath("$.content[1].nameCity").value("Maringá"));
		result.andExpect(jsonPath("$.content[2].nameCity").value("Maringá"));
		result.andExpect(jsonPath("$.content[3].nameCity").value("São José dos Pinhais"));
		result.andExpect(jsonPath("$.content[4].nameCity").value("São Roque"));
	}

}
