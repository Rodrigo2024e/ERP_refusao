package com.smartprocessrefusao.erprefusao.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

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
import com.smartprocessrefusao.erprefusao.dto.EmployeeDepartamentDTO;
import com.smartprocessrefusao.erprefusao.enumerados.EmployeePosition;
import com.smartprocessrefusao.erprefusao.tests.EmployeeFactory.Factory;
import com.smartprocessrefusao.erprefusao.tests.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EmployeeResourceIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TokenUtil tokenUtil;

	@Autowired
	private ObjectMapper objectMapper;

	private String clientToken;
	private String adminToken;
	private String invalidToken;

	private final String clientUsername = "michele@alunova.com";
	private final String clientPassword = "123456";
	private final String adminUsername = "luciano@alunova.com";
	private final String adminPassword = "123456";

	private Long existingId = 1L;

	@BeforeEach
	void setUp() throws Exception {
		clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
		adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
		invalidToken = adminToken + "xpto";
	}

	// 1 - Token invalid
	@Test
	public void insertShouldReturn401WhenInvalidToken() throws Exception {

		EmployeeDepartamentDTO dto = new EmployeeDepartamentDTO(
				null, 
				"Luciano R Carvalho", 
				"luciano@gmail.com.br", 
				"44-14244-1222",
				"44-1442-2222", 
				"111.000.111-49", 
				LocalDate.now(),  
				1L, 
				"Produção", 
				"Recebimento e classificação de sucata",
				EmployeePosition.GERENTE_INDUSTRIAL);
		
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/employees").header("Authorization", "Bearer " + invalidToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnauthorized());
	}

	// 2 - Unauthorized
	@Test
	public void insertShouldReturn403WhenClientLogged() throws Exception {

		EmployeeDepartamentDTO dto = new EmployeeDepartamentDTO(null, 
				"Luciano R Carvalho", 
				"luciano@gmail.com.br", 
				"44-14244-1222",
				"44-1442-2222", 
				"111.000.111-49", 
				LocalDate.now(),  
				1L, 
				"Produção", 
				"Recebimento e classificação de sucata",
				EmployeePosition.GERENTE_INDUSTRIAL);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/employees").header("Authorization", "Bearer " + clientToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isForbidden());

	}

	// 3 - SortedByNameEmployee
	@Test
	public void findAllShouldReturnAllResourcesPageadEmployeeSortedByName() throws Exception {

		ResultActions result = mockMvc.perform(get("/employees").header("Authorization", "Bearer " + adminToken)
				.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].name").value("Giovana Gonçalves"));
		result.andExpect(jsonPath("$.content[1].name").value("Luciano Rodrigo de Carvalho"));
		result.andExpect(jsonPath("$.content[2].name").value("Michele Pereira da Silva"));
		result.andExpect(jsonPath("$.content[3].name").value("Natal da Silva Bueno"));

	}

	// 4 - FindByIdExistsAndAdminRole
	@Test
	public void findByIdShouldReturnEmployeeWhenIdExistsAndAdminRole() throws Exception {
		mockMvc.perform(get("/employees/{id}", existingId).header("Authorization", "Bearer " + adminToken)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	// 5 - AdminLoggedAndCorrectData
	@Test
	public void insertShouldReturnCreatedWhenAdminInsertValidData() throws Exception {
		EmployeeDepartamentDTO dto = Factory.createEmployeeDTO();
		String jsonBody = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post("/employees").header("Authorization", "Bearer " + adminToken).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.idPessoa").exists());
	}

	// 6 - ForbiddenWhenClientTriesToInsert
	@Test
	public void insertShouldReturnForbiddenWhenClientTriesToInsert() throws Exception {
		EmployeeDepartamentDTO dto = Factory.createEmployeeDTO();
		String jsonBody = objectMapper.writeValueAsString(dto);

		System.out.println("==== JSON enviado ====");
		System.out.println(jsonBody);

		MvcResult result = mockMvc
				.perform(post("/employees").header("Authorization", "Bearer " + clientToken).content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isForbidden()).andReturn();

		String responseContent = result.getResponse().getContentAsString();
		System.out.println("==== RESPOSTA DO SERVIDOR ====");
		System.out.println(responseContent);
	}

	// 7 - AdminLoggedAndInvalidName
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndInvalidName() throws Exception {
		EmployeeDepartamentDTO dto = new EmployeeDepartamentDTO(
				null, 
				"Luciano R Carvalho", 
				"luciano@gmail.com.br", 
				"44-14244-1222",
				"44-1442-2222", 
				"111.000.111-49", 
				LocalDate.now(),  
				1L, 
				"Produção", 
				"Recebimento e classificação de sucata",
				EmployeePosition.GERENTE_INDUSTRIAL);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/employees").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("name"));
		result.andExpect(jsonPath("$.errors[0].message").value("O nome deve ter entre 3 a 50 caracteres"));

	}

	// 8 - AdminLoggedAndInvalidEmail
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndInvalidEmail() throws Exception {
		EmployeeDepartamentDTO dto = new EmployeeDepartamentDTO(
				null, 
				"Luciano R Carvalho", 
				"luciano@gmail.com.br", 
				"44-14244-1222",
				"44-1442-2222", 
				"111.000.111-49", 
				LocalDate.now(),  
				1L, 
				"Produção", 
				"Recebimento e classificação de sucata",
				EmployeePosition.GERENTE_INDUSTRIAL);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/employees").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("email"));
		result.andExpect(jsonPath("$.errors[0].message").value("must be a well-formed email address"));

	}

	// 9 - AdminLoggedAndInvalidCellPhone
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndInvalidCellPhone() throws Exception {
		EmployeeDepartamentDTO dto = new EmployeeDepartamentDTO(null, 
				"Luciano R Carvalho", 
				"luciano@gmail.com.br", 
				"44-14244-1222",
				"44-1442-2222", 
				"111.000.111-49", 
				LocalDate.now(),  
				1L, 
				"Produção", 
				"Recebimento e classificação de sucata",
				EmployeePosition.GERENTE_INDUSTRIAL);
		
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/employees").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("cellPhone"));
		result.andExpect(jsonPath("$.errors[0].message").value("O celular deve estar no formato 00-00000-0000"));

	}

	// 10 - AdminLoggedAndInvalidTelephone
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndInvalidTelephone() throws Exception {
		EmployeeDepartamentDTO dto = new EmployeeDepartamentDTO(
				null, 
				"Luciano R Carvalho", 
				"luciano@gmail.com.br", 
				"44-14244-1222",
				"44-1442-2222", 
				"111.000.111-49", 
				LocalDate.now(),  
				1L, 
				"Produção", 
				"Recebimento e classificação de sucata",
				EmployeePosition.GERENTE_INDUSTRIAL);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/employees").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("telephone"));
		result.andExpect(jsonPath("$.errors[0].message").value("O telefone fixo deve estar no formato 00-0000-0000"));

	}

	// 11 - AdminLoggedAndInvalidCpf
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndInvalidCpf() throws Exception {
		EmployeeDepartamentDTO dto = new EmployeeDepartamentDTO(
				null, 
				"Luciano R Carvalho", 
				"luciano@gmail.com.br", 
				"44-14244-1222",
				"44-1442-2222", 
				"111.000.111-49", 
				LocalDate.now(),  
				1L, 
				"Produção", 
				"Recebimento e classificação de sucata",
				EmployeePosition.GERENTE_INDUSTRIAL);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/employees").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("cpf"));
		result.andExpect(
				jsonPath("$.errors[0].message").value("invalid Brazilian individual taxpayer registry number (CPF)"));

	}

	// 12 - AdminLoggedAndBlankRg
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndBlankRg() throws Exception {

		EmployeeDepartamentDTO dto = new EmployeeDepartamentDTO(
				null, 
				"Luciano R Carvalho", 
				"luciano@gmail.com.br", 
				"44-14244-1222",
				"44-1442-2222", 
				"111.000.111-49", 
				LocalDate.now(),  
				1L, 
				"Produção", 
				"Recebimento e classificação de sucata",
				EmployeePosition.GERENTE_INDUSTRIAL);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/employees").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("rg"));
		result.andExpect(jsonPath("$.errors[0].message").value("O campo RG é obrigatório"));

	}

	// 13 - AdminLoggedAndIsUser
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndIsUser() throws Exception {
		EmployeeDepartamentDTO dto = new EmployeeDepartamentDTO(
				null, 
				"Luciano R Carvalho", 
				"luciano@gmail.com.br", 
				"44-14244-1222",
				"44-1442-2222", 
				"111.000.111-49", 
				LocalDate.now(),  
				1L, 
				"Produção", 
				"Recebimento e classificação de sucata",
				EmployeePosition.GERENTE_INDUSTRIAL);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/employees").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("sysUser"));
		result.andExpect(jsonPath("$.errors[0].message").value("Informe se o funcionário é usuário do sistema"));

	}

	// 14 - AdminLoggedAndNullSector
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndNullSector() throws Exception {
		EmployeeDepartamentDTO dto = new EmployeeDepartamentDTO(
				null, 
				"Luciano R Carvalho", 
				"luciano@gmail.com.br", 
				"44-14244-1222",
				"44-1442-2222", 
				"111.000.111-49", 
				LocalDate.now(),  
				1L, 
				"Produção", 
				"Recebimento e classificação de sucata",
				EmployeePosition.GERENTE_INDUSTRIAL);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/employees").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("sectorId"));
		result.andExpect(jsonPath("$.errors[0].message").value("Informe o setor do funcionário"));

	}

	// 15 - ReportShouldReturnOkForAdminAndUser
	@Test
	public void getReportShouldReturnOkForAdminAndUser() throws Exception {
		mockMvc.perform(get("/employees/report").header("Authorization", "Bearer " + adminToken)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		mockMvc.perform(get("/employees/report").header("Authorization", "Bearer " + adminToken)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	// 16 - SectorShouldReturnOkForAdminAndUser
	@Test
	public void reportBySectorShouldReturnOkForAdminAndUser() throws Exception {
		mockMvc.perform(
				get("/employees").header("Authorization", "Bearer " + adminToken).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mockMvc.perform(
				get("/employees").header("Authorization", "Bearer " + adminToken).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
