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
import com.smartprocessrefusao.erprefusao.cadastros.dto.PartnerDTO;
import com.smartprocessrefusao.erprefusao.tests.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ParceiroResourceT {

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

		PartnerDTO dto = new PartnerDTO(null, "Apple", "apple@gmail.com", "44-12345-7652", "00-0000-0000", "00.252.457/000-45", "114.115.225", true, true, true);
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/parceiros")
					.header("Authorization", "Bearer " + invalidToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnauthorized());
	}
	
//2	
	@Test
	public void insertShouldReturn403WhenClientLogged() throws Exception {
		
		PartnerDTO dto = new PartnerDTO(null, "Apple", "apple@gmail.com.br", "44-12244-1222", "44-1442-2222", "07.911.773/0001-79", "114.115.225", true, true, true);
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/parceiros")
					.header("Authorization", "Bearer " + clientToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isForbidden());
	}

//3	
	@Test
	public void insertShouldInsertResourceWhenAdminLoggedAndCorrectData() throws Exception {

		PartnerDTO dto = new PartnerDTO(null, "Apple", "apple@gmail.com.br", "44-12244-1222", "44-1442-2222", "07.911.773/0001-79", "114.115.225", true, true, true);
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/parceiros")
					.header("Authorization", "Bearer " + adminToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").exists()); //Parceiro herda id de pessoa
		result.andExpect(jsonPath("$.nomePessoa").value("Apple"));
		result.andExpect(jsonPath("$.email").value("apple@gmail.com.br"));
		result.andExpect(jsonPath("$.celular").value("44-12244-1222"));
		result.andExpect(jsonPath("$.telefone").value("44-1442-2222"));
		result.andExpect(jsonPath("$.cnpj").value("07.911.773/0001-79"));
		result.andExpect(jsonPath("$.ie").value("114.115.225"));
		result.andExpect(jsonPath("$.fornecedor").value("true"));
		result.andExpect(jsonPath("$.cliente").value("true"));
		result.andExpect(jsonPath("$.ativo").value("true"));
	}


//4
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndBlankName() throws Exception {

		PartnerDTO dto = new PartnerDTO(null, " ", "apple@gmail.com.br", "44-12244-1222", "44-1442-2222", "07.911.773/0001-79", "114.115.225", true, true, true);
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/parceiros")
					.header("Authorization", "Bearer " + adminToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("nomePessoa"));
		result.andExpect(jsonPath("$.errors[0].message").value("O campo nome deve ter entre 3 a 50 caracteres"));		

	}
//5	
	@Test
	public void findAllShouldReturnAllResourcesPageable() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/parceiros")
					.header("Authorization", "Bearer " + adminToken)
					.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());

	}
/*
	@Test
	public void findAllShouldReturnAllResourcesSortedByName() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/parceiros")
					.header("Authorization", "Bearer " + adminToken)
					.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$[0].nomePessoa").value("Pateta Souza"));
		result.andExpect(jsonPath("$[1].nomePessoa").value("Zickey Mouse"));

	}
*/	
}
