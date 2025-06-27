package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.UnitOfMeasure;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UnitOfMeasureDTO {

	private Long id;
	
	@Size(min = 3, max = 20, message = "O campo nome deve ter entre 3 a 20 caracteres")
	@NotBlank(message = "Campo requerido")
	private String description;
	
	@Size(min = 1, max = 3, message = "O campo nome deve ter entre 1 a 3 caracteres")
	@NotBlank(message = "Campo requerido")
	private String acronym;
	
	public UnitOfMeasureDTO () {
		
	}

	public UnitOfMeasureDTO(Long id, String description, String acronym) {
		this.id = id;
		this.description = description;
		this.acronym = acronym;
	}
	
	public UnitOfMeasureDTO(UnitOfMeasure entity) {
		id = entity.getId();
		description = entity.getDescription();
		acronym = entity.getAcronym();
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public String getAcronym() {
		return acronym;
	}
	
	
}
