package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.Input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class InputDTO {

	private Long id;

	private String typeMaterial;

	@Size(min = 3, max = 30, message = "O campo descrição deve ter entre 3 a 30 caracteres")
	private String description;

	@NotNull(message = "Campo Unidade de Medida requerido")
	private Long uomId;
	private String acronym;

	@NotNull(message = "Campo Classificação Fiscal requerida")
	private Long taxClassId;
	private String description_taxclass;
	private Integer number;

	@NotNull(message = "Campo Grupo de Material requerido")
	private Long matGroupId;
	private String description_matGroup;

	public InputDTO() {

	}

	public InputDTO(Long id, String typeMaterial, String description, Long uomId, String acronym, Long taxClassId,
			String description_taxclass, Integer number, Long matGroupId, String description_matGroup) {
		this.id = id;
		this.typeMaterial = typeMaterial;
		this.description = description;
		this.uomId = uomId;
		this.acronym = acronym;
		this.taxClassId = taxClassId;
		this.description_taxclass = description_taxclass;
		this.number = number;
		this.matGroupId = matGroupId;
		this.description_matGroup = description_matGroup;
	}

	public InputDTO(Input entity) {
		id = entity.getId();
		typeMaterial = entity.getTypeMaterial().toString();
		description = entity.getDescription();
		uomId = entity.getUomMaterial().getId();
		acronym = entity.getUomMaterial().getAcronym();
		taxClassId = entity.getTaxClassMaterial().getId();
		description_taxclass = entity.getTaxClassMaterial().getDescription();
		number = entity.getTaxClassMaterial().getNumber();
		matGroupId = entity.getMaterialGroup().getId();
		description_matGroup = entity.getMaterialGroup().getDescription();
	}

	public Long getId() {
		return id;
	}

	public String getTypeMaterial() {
		return typeMaterial;
	}

	public String getDescription() {
		return description;
	}

	public Long getUomId() {
		return uomId;
	}

	public String getAcronym() {
		return acronym;
	}

	public Long getTaxClassId() {
		return taxClassId;
	}

	public String getDescription_taxclass() {
		return description_taxclass;
	}

	public Integer getNumber() {
		return number;
	}

	public Long getMatGroupId() {
		return matGroupId;
	}

	public String getDescription_matGroup() {
		return description_matGroup;
	}

}
