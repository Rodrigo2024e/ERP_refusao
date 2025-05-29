package com.smartprocessrefusao.erprefusao.resources;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartprocessrefusao.erprefusao.tests.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class FuncionarioResourceT {

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
/*
	@Test
	public void insertShouldReturn401WhenInvalidToken() throws Exception {

		FuncionarioDTO dto = new FuncionarioDTO(null, "Giovana", "giovana@gmail.com", "44-12345-7652", "00-0000-0000", "198.149.318-29", "20.533.347-45", true, (long) 1, "Produção", "Recebimento e classificação de sucata");
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/funcionarios")
					.header("Authorization", "Bearer " + invalidToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnauthorized());
	}

//2	
	@Test
	public void insertShouldReturn403WhenClientLogged() throws Exception {

		FuncionarioDTO dto = new FuncionarioDTO(null, "Giovana", "giovana@gmail.com", "44-12345-7652", "00-0000-0000", "198.149.318-29", "20.533.347-45", true, (long) 1, "Producão", "Recebimento e classificação de sucata");
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/funcionarios")
					.header("Authorization", "Bearer " + clientToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isForbidden());
	}
//3	
	@Test
	public void insertShouldInsertResourceWhenAdminLoggedAndCorrectData() throws Exception {

		FuncionarioDTO dto = new FuncionarioDTO(null, "Giovana", "giovana@gmail.com", "44-12345-7652", "00-0000-0000", "198.149.318-29", "20.533.347-45", true, (long) 1, "Produção", "Recebimento e classificação de sucata");
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/funcionarios")
					.header("Authorization", "Bearer " + adminToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.idPessoa").exists()); 
		result.andExpect(jsonPath("$.nomePessoa").value("Giovana"));
		result.andExpect(jsonPath("$.email").value("giovana@gmail.com"));
		result.andExpect(jsonPath("$.celular").value("44-12345-7652"));
		result.andExpect(jsonPath("$.telefone").value("00-0000-0000"));
		result.andExpect(jsonPath("$.cpf").value("198.149.318-29"));
		result.andExpect(jsonPath("$.rg").value("20.533.347-45"));
		result.andExpect(jsonPath("$.usuarioSistema").value("true"));
		result.andExpect(jsonPath("$.setorId").value((long) 1));
		result.andExpect(jsonPath("$.setorNome").value("Produção"));
		result.andExpect(jsonPath("$.setorProcesso").value("Recebimento e classificação de sucata"));
	}
//4
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndBlankName() throws Exception {

		FuncionarioDTO dto = new FuncionarioDTO(null, "    ", "giovana@gmail.com", "44-12345-7652", "00-0000-0000", "198.149.318-29", "20.533.347-45", true, (long) 1, "Produção", "Recebimento e classificação de sucata");
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/funcionarios")
					.header("Authorization", "Bearer " + adminToken)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("nomePessoa"));
		result.andExpect(jsonPath("$.errors[0].message").value("Campo requerido"));

	}
//5	
	@Test
	public void findAllShouldReturnAllResourcesPageable() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/funcionarios")
					.header("Authorization", "Bearer " + adminToken)
					.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());


	}
/*
//6
	@Test
	public void findAllShouldReturnAllResourcesSortedByName() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/funcionarios")
					.header("Authorization", "Bearer " + adminToken)
					.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$[0].nomePessoa").value("Pateta Souza"));
		result.andExpect(jsonPath("$[1].nomePessoa").value("Zickey Mouse"));
	}
	
*/	
	
	
}
