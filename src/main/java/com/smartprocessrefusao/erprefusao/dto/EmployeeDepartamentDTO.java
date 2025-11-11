package com.smartprocessrefusao.erprefusao.dto;

import org.hibernate.validator.constraints.br.CPF;

import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.projections.EmployeeDepartamentProjection;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EmployeeDepartamentDTO {

	private Long id;

	@Size(min = 3, max = 50, message = "O nome deve ter entre 3 a 50 caracteres")
	private String name;

	@NotEmpty(message = "must be a well-formed email address")
	@Email
	private String email;

	@NotBlank(message = "O celular é obrigatório")
	@Pattern(regexp = "\\d{2}-\\d{5}-\\d{4}", message = "O celular deve estar no formato 00-00000-0000")
	private String cellPhone;

	@NotBlank(message = "O telefone é obrigatório")
	@Pattern(regexp = "\\d{2}-\\d{4}-\\d{4}", message = "O telefone fixo deve estar no formato 00-0000-0000")
	private String telephone;

	@NotBlank(message = "invalid Brazilian individual taxpayer registry number (CPF)")
	@CPF
	private String cpf;

	@NotNull(message = "Informe o setor do funcionário")
	private Long departamentId;
	private String departament;
	private String process;

	public EmployeeDepartamentDTO() {

	}

	public EmployeeDepartamentDTO(Long id, String name, String email, String cellPhone, String telephone, String cpf,
			 Long departamentId, String departament, String process) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.cellPhone = cellPhone;
		this.telephone = telephone;
		this.cpf = cpf;
		this.departamentId = departamentId;
		this.departament = departament;
		this.process = process;
	}

	public EmployeeDepartamentDTO(Employee entity) {
		id= entity.getId();
		name = entity.getName();
		email = entity.getEmail();
		cellPhone = entity.getCellPhone();
		telephone = entity.getTelephone();
		cpf = entity.getCpf();
		departamentId = entity.getDepartament().getId();
		departament = entity.getDepartament().getName();
		process = entity.getDepartament().getProcess();

	}

	public EmployeeDepartamentDTO(EmployeeDepartamentProjection projection) {
		this.id = projection.getId();
		this.name = projection.getName();
		this.email = projection.getEmail();
		this.cellPhone = projection.getCellPhone();
		this.telephone = projection.getTelephone();
		this.cpf = projection.getCpf();
		this.departamentId = projection.getDepartamentId();
		this.departament = projection.getDepartament();
		this.process = projection.getProcess();

	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getCpf() {
		return cpf;
	}

	public Long getDepartamentId() {
		return departamentId;
	}

	public String getDepartament() {
		return departament;
	}

	public String getProcess() {
		return process;
	}

}
