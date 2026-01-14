package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.projections.ProductReportProjection;

public class ProductReportDTO {

	private Long productCode;
	private String description;
	private String alloy;
	private String alloyPol;
	private String alloyFootage;
	private String acronym;
	private Long taxClassId;
	private String descriptionTaxclass;
	private Integer ncmCode;
	private Long matGroupId;
	private String descriptionMatGroup;

	public ProductReportDTO() {

	}

	public ProductReportDTO(ProductReportProjection p) {
		productCode = p.getProductCode();
		description = p.getDescription();
		alloy = p.getAlloy();
		alloyPol = p.getAlloyPol();
		alloyFootage = p.getAlloyFootage();
		acronym = p.getAcronym();
		taxClassId = p.getTaxClassId();
		descriptionTaxclass = p.getDescriptionTaxclass();
		ncmCode = p.getNcmCode();
		matGroupId = p.getMatGroupId();
		descriptionMatGroup = p.getDescriptionMatGroup();
	}

	public Long getProductCode() {
		return productCode;
	}

	public String getDescription() {
		return description;
	}

	public String getAlloy() {
		return alloy;
	}

	public String getAlloyPol() {
		return alloyPol;
	}

	public String getAlloyFootage() {
		return alloyFootage;
	}

	public String getAcronym() {
		return acronym;
	}

	public Long getTaxClassId() {
		return taxClassId;
	}

	public String getDescriptionTaxclass() {
		return descriptionTaxclass;
	}

	public Integer getNcmCode() {
		return ncmCode;
	}

	public Long getMatGroupId() {
		return matGroupId;
	}

	public String getDescriptionMatGroup() {
		return descriptionMatGroup;
	}

}
