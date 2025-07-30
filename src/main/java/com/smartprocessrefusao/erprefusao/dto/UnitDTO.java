package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.Unit;

import jakarta.validation.constraints.Size;

public class UnitDTO {

	private Long id;

	@Size(min = 3, max = 20, message = "O campo nome deve ter entre 3 a 20 caracteres")
	private String description;

	@Size(min = 1, max = 3, message = "O campo nome deve ter entre 1 a 3 caracteres")
	private String acronym;

	public UnitDTO() {

	}

	public UnitDTO(Long id, String description, String acronym) {
		this.id = id;
		this.description = description;
		this.acronym = acronym;
	}

	public UnitDTO(Unit entity) {
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
