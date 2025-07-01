package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.Product;

public class ProductDTO {

	private Long id;
	private String description;
	private Integer alloy;
	private Integer inch;
	private Integer length;
	private Long uom_id;
	private String acronym;
	private Long taxClass_id;
	private String taxClass;
	private String number;
	private Long prodGroup_id;
	private String prodGroup;
	
	public ProductDTO() {
		
	}

	public ProductDTO(Long id, String description, Integer alloy, Integer inch, Integer length, Long uom_id,
			String acronym, Long taxClass_id, String taxClass, String number, Long prodGroup_id, String prodGroup) {
		this.id = id;
		this.description = description;
		this.alloy = alloy;
		this.inch = inch;
		this.length = length;
		this.uom_id = uom_id;
		this.acronym = acronym;
		this.taxClass_id = taxClass_id;
		this.taxClass = taxClass;
		this.number = number;
		this.prodGroup_id = prodGroup_id;
		this.prodGroup = prodGroup;
	}



	public ProductDTO(Product entity) {
		id = entity.getId();
		description = entity.getDescription();
		alloy = entity.getAlloy();
		inch = entity.getInch();
		length = entity.getLength();
		uom_id = entity.getUom().getId();
		acronym = entity.getUom().getAcronym();
		taxClass_id = entity.getTaxclass().getId();
		taxClass = entity.getTaxclass().getDescription();
		number = entity.getTaxclass().getNumber();
		prodGroup_id = entity.getProdGroup().getId();
		prodGroup = entity.getProdGroup().getDescription();
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

	public Long getUom_id() {
		return uom_id;
	}

	public String getAcronym() {
		return acronym;
	}

	public Long getTaxClass_id() {
		return taxClass_id;
	}

	public String getTaxClass() {
		return taxClass;
	}

	public String getNumber() {
		return number;
	}

	public Long getProdGroup_id() {
		return prodGroup_id;
	}

	public String getProdGroup() {
		return prodGroup;
	}
	
	
}
