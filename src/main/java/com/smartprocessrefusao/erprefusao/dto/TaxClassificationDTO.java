package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.TaxClassification;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TaxClassificationDTO {
	
	private Long id;

	@Size(min = 5, max = 30, message = "O campo nome deve ter entre 5 a 30 caracteres")
	private String description;
	
	@NotNull(message = "A numeração fiscal deve ser composta de 7 dígitos")
	private Integer ncmCode;
	
	public TaxClassificationDTO() {
		
	}

	public TaxClassificationDTO(Long id, String description, Integer ncmCode) {
		this.id = id;
		this.description = description;
		this.ncmCode = ncmCode;
	}
	
	public TaxClassificationDTO(TaxClassification entity) {
		id = entity.getId();
		description = entity.getDescription();
		ncmCode = entity.getNcmCode();
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public Integer getNcmCode() {
		return ncmCode;
	}
	
}
