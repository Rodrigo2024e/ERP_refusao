package com.smartprocessrefusao.erprefusao.cadastros.dto;

import org.hibernate.validator.constraints.br.CPF;

import com.smartprocessrefusao.erprefusao.cadastros.entities.Employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EmployeeDTO {
	
	private Long id;
	
	@Size(min = 3, max = 50, message = "O campo nome deve ter entre 5 a 50 caracteres")
	@NotBlank(message = "Campo requerido")
	private String name;
	
	@NotEmpty(message = "Informe um e-mail válido")
    @Email
	private String email;
	
	@NotBlank(message = "O celular é obrigatório")
	@Pattern(regexp = "\\d{2}-\\d{5}-\\d{4}", message = "O celular deve estar no formato 00-00000-0000")
	private String cellPhone;
	
	@NotBlank(message = "O telefone é obrigatório")
	@Pattern(regexp = "\\d{2}-\\d{4}-\\d{4}", message = "O telefone fixo deve estar no formato 00-0000-0000")
	private String telephone;
	
	@NotBlank(message = "O campo CPF é obrigatório")
	@CPF
	private String cpf;
	
	@NotBlank(message = "O campo RG é obrigatório")
	private String rg;
	
	@NotNull(message = "Informe se o funcionário é usuário do sistema")
	private Boolean sysUser;
	
	@NotNull(message = "Informe o setor do funcionário")
	private Long sectorId;
	private String nameSector;
	private String process;

	public EmployeeDTO() {
		
	}

	public EmployeeDTO(Employee entity) {
		id = entity.getId();
		name = entity.getName();
		email = entity.getEmail();
		cellPhone = entity.getCellPhone();
		telephone = entity.getTelephone();
		cpf = entity.getCpf();
		rg = entity.getRg();
		sysUser = entity.getSysUser();
		sectorId = entity.getSector().getId();
		nameSector = entity.getSector().getNameSector();
		process = entity.getSector().getProcess();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Boolean getSysUser() {
		return sysUser;
	}

	public void setSysUser(Boolean sysUser) {
		this.sysUser = sysUser;
	}

	public Long getSectorId() {
		return sectorId;
	}

	public void setSectorId(Long sectorId) {
		this.sectorId = sectorId;
	}

	public String getNameSector() {
		return nameSector;
	}

	public void setNameSector(String nameSector) {
		this.nameSector = nameSector;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

}