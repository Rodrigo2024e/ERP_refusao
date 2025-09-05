package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.projections.ReportProductProjection;

public class ReportProductDTO {

	private Long id;
	private String typeMaterial;
	private String description;
	private Integer alloy;
	private Integer billetDiameter;
	private double BilletLength;
	private Long unitId;
	private String acronym;
	private Long taxClassId;
	private String description_taxclass;
	private Integer number;
	private Long matGroupId;
	private String description_matGroup;

	public ReportProductDTO() {

	}

	public ReportProductDTO(ReportProductProjection projection) {
		id = projection.getId();
		typeMaterial = projection.getTypeMaterial();
		description = projection.getDescription();
		alloy = projection.getAlloy();
		billetDiameter = projection.getBilletDiameter();
		BilletLength = projection.getBilletLength();
		unitId = projection.getUnitId();
		acronym = projection.getAcronym();
		taxClassId = projection.getTaxClassId();
		description_taxclass = projection.getDescription_taxclass();
		number = projection.getNumber();
		matGroupId = projection.getMatGroupId();
		description_matGroup = projection.getDescription_matGroup();

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

	public Integer getAlloy() {
		return alloy;
	}

	public Integer getBilletDiameter() {
		return billetDiameter;
	}

	public double getBilletLength() {
		return BilletLength;
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
