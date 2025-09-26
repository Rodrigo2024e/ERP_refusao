package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.Input;
import com.smartprocessrefusao.erprefusao.projections.InputReportProjection;

public class InputReportDTO {

	private Long id;
	private String typeMaterial;
	private String description;
	private String unit;
	private Long taxClassId;
	private String tax_Classification;
	private Integer number;
	private Long matGroupId;
	private String material_Group;

	public InputReportDTO() {

	}

	public InputReportDTO(Input entity) {
		id = entity.getId();
		typeMaterial = entity.getTypeMaterial().toString();
		description = entity.getDescription();
		unit = entity.getUomMaterial().getAcronym();
		taxClassId = entity.getTaxClassMaterial().getId();
		tax_Classification = entity.getTaxClassMaterial().getDescription();
		number = entity.getTaxClassMaterial().getNumber();
		matGroupId = entity.getMaterialGroup().getId();
		material_Group = entity.getMaterialGroup().getDescription();

	}

	public InputReportDTO(InputReportProjection projection) {
		this.id = projection.getId();
		this.typeMaterial = projection.getType_Material();
		this.description = projection.getDescription();
		this.unit = projection.getUnit();
		this.taxClassId = projection.getTaxClassId();
		this.tax_Classification = projection.getTax_Classification();
		this.number = projection.getNumber();
		this.matGroupId = projection.getMatGroupId();
		this.material_Group = projection.getMaterial_Group();

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

	public String getUnit() {
		return unit;
	}

	public Long getTaxClassId() {
		return taxClassId;
	}

	public String getTax_Classification() {
		return tax_Classification;
	}

	public Integer getNumber() {
		return number;
	}

	public Long getMatGroupId() {
		return matGroupId;
	}

	public String getMaterial_Group() {
		return material_Group;
	}

	
}
