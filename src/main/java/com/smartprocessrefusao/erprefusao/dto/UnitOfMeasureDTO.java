package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.UnitOfMeasure;

public class UnitOfMeasureDTO {

	private Long id;
	private String description;
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
