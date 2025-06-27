package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.TaxClassification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaxClassificationDTO {
	
	private Long id;

	@Size(min = 5, max = 30, message = "O campo nome deve ter entre 5 a 30 caracteres")
	@NotBlank(message = "Campo requerido")
	private String description;
	
	@Size(min = 7, max = 7, message = "O campo nome deve ter 7 caracteres")
	@NotBlank(message = "Campo requerido")
	private String number;
	
	public TaxClassificationDTO() {
		
	}

	public TaxClassificationDTO(Long id, String description, String number) {
		this.id = id;
		this.description = description;
		this.number = number;
	}
	
	public TaxClassificationDTO(TaxClassification entity) {
		id = entity.getId();
		description = entity.getDescription();
		number = entity.getNumber();
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public String getNumber() {
		return number;
	}
	
	
}
