package com.smartprocessrefusao.erprefusao.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.enumerados.EmployeePosition;
import com.smartprocessrefusao.erprefusao.projections.EmployeeDepartamentReportProjection;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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
	@Column(name = "cpf", unique = true)
	private String cpf;

	@NotNull(message = "A data de nascimento é obrigatória")
	@Past(message = "A data deve estar no passado.")
	private LocalDate dateOfBirth;

	@NotNull(message = "Informe o setor do funcionário")
	private Long departamentId;
	private String departament;
	private String process;
	private EmployeePosition position;

	public EmployeeDepartamentDTO() {

	}

	public EmployeeDepartamentDTO(Long id, String name, String email, String cellPhone, String telephone, String cpf,
			LocalDate dateOfBirth, Long departamentId, String departament, String process, EmployeePosition position) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.cellPhone = cellPhone;
		this.telephone = telephone;
		this.cpf = cpf;
		this.dateOfBirth = dateOfBirth;
		this.departamentId = departamentId;
		this.departament = departament;
		this.process = process;
		this.position = position;
	}

	public EmployeeDepartamentDTO(Employee entity) {
		id = entity.getId();
		name = entity.getName();
		email = entity.getEmail();
		cellPhone = entity.getCellPhone();
		telephone = entity.getTelephone();
		cpf = entity.getCpf();
		dateOfBirth = entity.getDateOfBirth();
		departamentId = entity.getDepartament().getId();
		departament = entity.getDepartament().getName();
		process = entity.getDepartament().getProcess();
		position = entity.getDepartament().getPosition();

	}

	public EmployeeDepartamentDTO(EmployeeDepartamentReportProjection projection) {
		id = projection.getId();
		name = projection.getName();
		email = projection.getEmail();
		cellPhone = projection.getCellPhone();
		telephone = projection.getTelephone();
		cpf = projection.getCpf();
		dateOfBirth = projection.getDateOfBirth();
		departamentId = projection.getDepartamentId();
		departament = projection.getDepartament();
		process = projection.getProcess();

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

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
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

	public EmployeePosition getPosition() {
		return position;
	}

}
