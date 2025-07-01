package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.Product;
import com.smartprocessrefusao.erprefusao.projections.ReportProductProjection;

public class ReportProductDTO {

	private Long id;
	private String description;
	private Integer alloy;
	private Integer inch;
	private Integer length;
	private String unit;
	private String tax_Classification;
	private String number;
	private String product_Group;
    
    public ReportProductDTO() {
    	
    }

	public ReportProductDTO(Product entity) {
		id = entity.getId();
		description = entity.getDescription();
		alloy = entity.getAlloy();
		inch = entity.getInch();
		length = entity.getLength();
		unit = entity.getUom().getAcronym();
		tax_Classification = entity.getTaxclass().getDescription();
		number = entity.getTaxclass().getNumber();
		product_Group = entity.getProdGroup().getDescription();
	    		
	}

	public ReportProductDTO(ReportProductProjection projection) {
		this.id = projection.getId();
		this.description = projection.getDescription();
		this.alloy = projection.getAlloy();
		this.inch = projection.getInch();
		this.length = projection.getLength();
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

	public Integer getAlloy() {
		return alloy;
	}

	public Integer getInch() {
		return inch;
	}

	public Integer getLength() {
		return length;
	}

	public String getUnit() {
		return unit;
	}

	public String getTax_Classification() {
		return tax_Classification;
	}

	public String getNumber() {
		return number;
	}

	public String getProduct_Group() {
		return product_Group;
	}
	
}
