package com.smartprocessrefusao.erprefusao.dto;

import java.util.Objects;

import com.smartprocessrefusao.erprefusao.entities.MaterialGroup;

import jakarta.validation.constraints.Size;

public class MaterialGroupDTO {

	private Long id;

	@Size(min = 3, max = 30, message = "O campo nome deve ter entre 3 a 30 caracteres")
	private String description;

	public MaterialGroupDTO() {

	}

	public MaterialGroupDTO(Long id, String description) {
		this.id = id;
		this.description = description;
	}

	public MaterialGroupDTO(MaterialGroup entity) {
		id = entity.getId();
		description = entity.getDescription();
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MaterialGroupDTO other = (MaterialGroupDTO) obj;
		return Objects.equals(id, other.id);
	}

}
