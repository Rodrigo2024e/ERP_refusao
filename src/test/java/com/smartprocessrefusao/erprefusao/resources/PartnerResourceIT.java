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
import com.smartprocessrefusao.erprefusao.dto.PartnerDTO;
import com.smartprocessrefusao.erprefusao.tests.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PartnerResourceIT {

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
		existingId = 4L;

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

		PartnerDTO dto = new PartnerDTO(null, "Apple", "apple@gmail.com", "44-12345-7652", "00-0000-0000",
				"00.252.457/000-45", "114.115.225", true, true, true);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/partners").header("Authorization", "Bearer " + invalidToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnauthorized());
	}

	// 2 - Unauthorized
	@Test
	public void insertShouldReturn403WhenClientLogged() throws Exception {

		PartnerDTO dto = new PartnerDTO(null, "Apple", "apple@gmail.com.br", "44-12244-1222", "44-1442-2222",
				"07.911.773/0001-79", "114.115.225", true, true, true);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/partners").header("Authorization", "Bearer " + clientToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isForbidden());
	}

	// 3 - FindAllPartner
	@Test
	public void findAllShouldReturnAllResourcesPageable() throws Exception {

		ResultActions result = mockMvc.perform(get("/partners").header("Authorization", "Bearer " + adminToken)
				.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());

	}

	// 4 - ReportPartner
	@Test
	public void findAllShouldReturnReportResourcesPageable() throws Exception {

		ResultActions result = mockMvc.perform(get("/partners/report").header("Authorization", "Bearer " + adminToken)
				.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());

	}

	// 5 - SortedByNameEmployee
	@Test
	public void findAllShouldReturnAllResourcesSortedByName() throws Exception {

		ResultActions result = mockMvc.perform(get("/partners/report").header("Authorization", "Bearer " + adminToken)
				.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].name").value("Ecoalumi Aluminio S/A"));
		result.andExpect(jsonPath("$.content[1].name").value("Metalurgica Reisam Industria e Comércio"));
		result.andExpect(jsonPath("$.content[2].name").value("Recimax Ltda"));

	}

	// 6 - FindByIdExistsAndAdminRole
	@Test
	public void findByIdShouldReturnEmployeeWhenIdExistsAndAdminRole() throws Exception {
		mockMvc.perform(get("/partners/{id}", existingId).header("Authorization", "Bearer " + adminToken)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	// 7 - AdminLoggedAndCorrectData
	@Test
	public void insertShouldInsertResourceWhenAdminLoggedAndCorrectData() throws Exception {

		PartnerDTO dto = new PartnerDTO(null, "Apple", "apple@gmail.com.br", "44-12244-1222", "44-1442-2222",
				"07.911.773/0001-79", "114.115.225", true, true, true);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/partners").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").value("APPLE"));
		result.andExpect(jsonPath("$.email").value("APPLE@GMAIL.COM.BR"));
		result.andExpect(jsonPath("$.cellPhone").value("44-12244-1222"));
		result.andExpect(jsonPath("$.telephone").value("44-1442-2222"));
		result.andExpect(jsonPath("$.cnpj").value("07.911.773/0001-79"));
		result.andExpect(jsonPath("$.ie").value("114.115.225"));
		result.andExpect(jsonPath("$.supplier").value("true"));
		result.andExpect(jsonPath("$.client").value("true"));
		result.andExpect(jsonPath("$.active").value("true"));
	}

	// 8 - AdminLoggedAndBlankName
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndBlankName() throws Exception {

		PartnerDTO dto = new PartnerDTO(null, "", "apple@gmail.com.br", "44-12244-1222", "44-1442-2222",
				"07.911.773/0001-79", "114.115.225", true, true, true);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/partners").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("name"));
		result.andExpect(jsonPath("$.errors[0].message").value("O campo nome deve ter entre 3 a 50 caracteres"));

	}

	// 9 - AdminLoggedAndInvalidName
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndInvalidName() throws Exception {

		PartnerDTO dto = new PartnerDTO(null, "ab", "apple@gmail.com.br", "44-12244-1222", "44-1442-2222",
				"07.911.773/0001-79", "114.115.225", true, true, true);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/partners").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("name"));
		result.andExpect(jsonPath("$.errors[0].message").value("O campo nome deve ter entre 3 a 50 caracteres"));

	}

	// 10 - AdminLoggedAndInvalidEmail
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndInvalidEmail() throws Exception {
		PartnerDTO dto = new PartnerDTO(null, "Apple", "apple@gmail.", "44-12244-1222", "44-1442-2222",
				"07.911.773/0001-79", "114.115.225", true, true, true);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/partners").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("email"));
		result.andExpect(jsonPath("$.errors[0].message").value("must be a well-formed email address"));

	}

	// 11 - AdminLoggedAndInvalidCellPhone
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndInvalidCellPhone() throws Exception {
		PartnerDTO dto = new PartnerDTO(null, "Apple", "apple@gmail.com", "44-X244-1222", "44-1442-2222",
				"07.911.773/0001-79", "114.115.225", true, true, true);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/partners").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("cellPhone"));
		result.andExpect(jsonPath("$.errors[0].message").value("O celular deve estar no formato 00-00000-0000"));
	}

	// 12 - AdminLoggedAndInvalidTelephone
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndInvalidTelephone() throws Exception {
		PartnerDTO dto = new PartnerDTO(null, "Apple", "apple@gmail.com", "44-12345-1222", "44-X442-2222",
				"07.911.773/0001-79", "114.115.225", true, true, true);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/partners").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("telephone"));
		result.andExpect(jsonPath("$.errors[0].message").value("O telefone fixo deve estar no formato 00-0000-0000"));
	}

	// 13 - AdminLoggedAndInvalidCNPJ
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndInvalidCNPJ() throws Exception {
		PartnerDTO dto = new PartnerDTO(null, "Apple", "apple@gmail.com", "44-12345-1222", "44-1442-2222",
				"07.911.773/0001-XX", "114.115.225", true, true, true);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/partners").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("cnpj"));
		result.andExpect(
				jsonPath("$.errors[0].message").value("invalid Brazilian corporate taxpayer registry number (CNPJ)"));
	}

	// 14 - AdminLoggedAndInvalidIE
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndInvalidIE() throws Exception {
		PartnerDTO dto = new PartnerDTO(null, "Apple", "apple@gmail.com", "44-12345-1222", "44-1442-2222",
				"07.911.773/0001-79", "", true, true, true);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/partners").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("ie"));
		result.andExpect(jsonPath("$.errors[0].message").value("O campo IE é obrigatório"));
	}

	// 15 - AdminLoggedAndBlankSupplier
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndBlankSupplier() throws Exception {
		PartnerDTO dto = new PartnerDTO(null, "Apple", "apple@gmail.com", "44-12345-1222", "44-1442-2222",
				"07.911.773/0001-79", "114.115.225", null, true, true);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/partners").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("supplier"));
		result.andExpect(jsonPath("$.errors[0].message").value("Informe se o parceiro é um fornecedor"));
	}

	// 16 - AdminLoggedAndBlankClient
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndBlankClient() throws Exception {
		PartnerDTO dto = new PartnerDTO(null, "Apple", "apple@gmail.com", "44-12345-1222", "44-1442-2222",
				"07.911.773/0001-79", "114.115.225", true, null, true);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/partners").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("client"));
		result.andExpect(jsonPath("$.errors[0].message").value("Informe se o parceiro é um cliente"));
	}
	
	// 17 - AdminLoggedAndBlankActive
	@Test
	public void insertShouldReturn422WhenAdminLoggedAndBlankActive() throws Exception {
		PartnerDTO dto = new PartnerDTO(null, "Apple", "apple@gmail.com", "44-12345-1222", "44-1442-2222",
				"07.911.773/0001-79", "114.115.225", true, true, null);
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(post("/partners").header("Authorization", "Bearer " + adminToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("active"));
		result.andExpect(jsonPath("$.errors[0].message").value("Informe se o parceiro está ativo no sistema"));
	}
}
