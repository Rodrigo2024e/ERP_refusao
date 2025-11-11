package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.projections.ReportProductProjection;

public class ReportProductDTO {

	private Long code;
	private String description;
	private String type;
	private String alloy;
	private Integer billetDiameter;
	private Double billetLength;
	private String acronym;
	private Long taxClassId;
	private String description_taxclass;
	private Integer ncmCode;
	private Long matGroupId;
	private String description_matGroup;

	public ReportProductDTO() {

	}

	public ReportProductDTO(ReportProductProjection projection) {
		code = projection.getCode();
		description = projection.getDescription();
		type = projection.getType();
		alloy = projection.getAlloy();
		billetDiameter = projection.getBilletDiameter();
		billetLength = projection.getBilletLength();
		acronym = projection.getAcronym();
		taxClassId = projection.getTaxClassId();
		description_taxclass = projection.getDescription_taxclass();
		ncmCode = projection.getNcmCode();
		matGroupId = projection.getMatGroupId();
		description_matGroup = projection.getDescription_matGroup();

	}

	public Long getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public String getType() {
		return type;
	}

	public String getAlloy() {
		return alloy;
	}

	public Integer getBilletDiameter() {
		return billetDiameter;
	}

	public Double getBilletLength() {
		return billetLength;
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

	public Integer getNcmCode() {
		return ncmCode;
	}

	public Long getMatGroupId() {
		return matGroupId;
	}

	public String getDescription_matGroup() {
		return description_matGroup;
	}

}
