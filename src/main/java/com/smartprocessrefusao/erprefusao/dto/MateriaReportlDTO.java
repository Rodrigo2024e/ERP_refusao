package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.projections.MaterialReportProjection;

public class MateriaReportlDTO {

	private Long materialCode;
	private String description;
	private String type;
	private Long unitId;
	private String acronym;
	private Long taxClassId;
	private String taxClassification;
	private Integer ncmCode;
	private Long matGroupId;
	private String materialGroup;

	public MateriaReportlDTO() {

	}

	public MateriaReportlDTO(MaterialReportProjection projection) {
		this.materialCode = projection.getMaterialCode();
		this.description = projection.getDescription();
		this.type = projection.getType();
		this.unitId = projection.getUnitId();
		this.acronym = projection.getAcronym();
		this.taxClassId = projection.getTaxClassId();
		this.taxClassification = projection.getTaxClassification();
		this.ncmCode = projection.getNcmCode();
		this.matGroupId = projection.getMatGroupId();
		this.materialGroup = projection.getMaterialGroup();

	}

	public Long getMaterialCode() {
		return materialCode;
	}

	public String getDescription() {
		return description;
	}

	public String getType() {
		return type;
	}

	public Long getUnitId() {
		return unitId;
	}

	public String getAcronym() {
		return acronym;
	}

	public Long getTaxClassId() {
		return taxClassId;
	}

	public String getTaxClassification() {
		return taxClassification;
	}

	public Integer getNcmCode() {
		return ncmCode;
	}

	public Long getMatGroupId() {
		return matGroupId;
	}

	public String getMaterialGroup() {
		return materialGroup;
	}

}
