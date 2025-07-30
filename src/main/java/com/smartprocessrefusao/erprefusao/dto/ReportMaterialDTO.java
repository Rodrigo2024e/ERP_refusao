package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.projections.ReportMaterialProjection;

public class ReportMaterialDTO {

	private Long id;
	private String description;
	private String unit;
	private String tax_Classification;
	private Integer number;
	private String product_Group;
    
    public ReportMaterialDTO() {
    	
    }

	public ReportMaterialDTO(Material entity) {
		id = entity.getId();
		description = entity.getDescription();
		unit = entity.getUom().getAcronym();
		tax_Classification = entity.getTaxClass().getDescription();
		number = entity.getTaxClass().getNumber();
		product_Group = entity.getProdGroup().getDescription();
	    		
	}

	public ReportMaterialDTO(ReportMaterialProjection projection) {
		this.id = projection.getId();
		this.description = projection.getDescription();
		this.unit = projection.getUnit();
		this.tax_Classification = projection.getTax_Classification();
		this.number = projection.getNumber();
		this.product_Group = projection.getProduct_Group();
	    		
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public String getUnit() {
		return unit;
	}

	public String getTax_Classification() {
		return tax_Classification;
	}

	public Integer getNumber() {
		return number;
	}

	public String getProduct_Group() {
		return product_Group;
	}

	
}
