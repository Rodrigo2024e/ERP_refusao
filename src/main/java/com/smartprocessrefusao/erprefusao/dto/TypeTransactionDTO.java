package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.TypeTransaction;

import jakarta.validation.constraints.NotBlank;

public class TypeTransactionDTO {

	private Long id;

	@NotBlank(message = "Campo requerido")
	private String description;

	public TypeTransactionDTO() {

	}

	public TypeTransactionDTO(Long id, String description) {
		this.id = id;
		this.description = description;
	}

	public TypeTransactionDTO(TypeTransaction entity) {
		id = entity.getId();
		description = entity.getDescription();
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

}
