package com.smartprocessrefusao.erprefusao.dto;

import org.hibernate.validator.constraints.br.CNPJ;

import com.smartprocessrefusao.erprefusao.entities.Partner;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PartnerDTO {

	private Long id;
	
//	@NotBlank
	@Size(min = 3, max = 50, message = "O campo nome deve ter entre 3 a 50 caracteres")
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
	
	@NotBlank(message = "O campo CNPJ é obrigatório")
	@CNPJ
	private String cnpj;
	
	private String ie;
	
	@NotNull(message = "Informe se o parceiro é um fornecedor")
	private Boolean supplier;
	
	@NotNull(message = "Informe se o parceiro é um cliente")
	private Boolean client;
	
	@NotNull(message = "Informe se o parceiro está ativo no sistema")
	private Boolean active;
	
	public PartnerDTO() {
		
	}

	public PartnerDTO(Long id, String name, String email, String cellPhone, String telephone, 
			String cnpj, String ie, Boolean supplier, Boolean client, Boolean active) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.cellPhone = cellPhone;
		this.telephone = telephone;
		this.cnpj = cnpj;
		this.ie = ie;
		this.supplier = supplier;
		this.client = client;
		this.active = active;
	}

	public PartnerDTO(Partner entity) {
		id = entity.getId();
		name = entity.getName();
		email = entity.getEmail();
		cellPhone = entity.getCellPhone();
		telephone = entity.getTelephone();
		cnpj = entity.getCnpj();
		ie = entity.getIe();
		supplier = entity.getSupplier();
		client = entity.getClient();
		active = entity.getActive();
		
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

	public String getCnpj() {
		return cnpj;
	}

	public String getIe() {
		return ie;
	}

	public Boolean getSupplier() {
		return supplier;
	}

	public Boolean getClient() {
		return client;
	}

	public Boolean getActive() {
		return active;
	}

	

}
