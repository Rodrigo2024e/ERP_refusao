package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.Product;

public class ProductDTO {

	private Long id;
	private String description;
	private Integer alloy;
	private Integer inch;
	private Integer length;
	private Long uomId;
	private String acronym;
	private Long taxClassId;
	private String taxClass;
	private String number;
	private Long prodGroupId;
	private String prodGroup;
	
	public ProductDTO() {
		
	}

	public ProductDTO(Long id, String description, Integer alloy, Integer inch, Integer length, Long uomId,
			String acronym, Long taxClassId, String taxClass, String number, Long prodGroupId, String prodGroup) {
		this.id = id;
		this.description = description;
		this.alloy = alloy;
		this.inch = inch;
		this.length = length;
		this.uomId = uomId;
		this.acronym = acronym;
		this.taxClassId = taxClassId;
		this.taxClass = taxClass;
		this.number = number;
		this.prodGroupId = prodGroupId;
		this.prodGroup = prodGroup;
	}

	public ProductDTO(Product entity) {
		id = entity.getId();
		description = entity.getDescription();
		alloy = entity.getAlloy();
		inch = entity.getInch();
		length = entity.getLength();
		uomId = entity.getUom().getId();
		acronym = entity.getUom().getAcronym();
		taxClassId = entity.getTaxclass().getId();
		taxClass = entity.getTaxclass().getDescription();
		number = entity.getTaxclass().getNumber();
		prodGroupId = entity.getProdGroup().getId();
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

	public Long getUomId() {
		return uomId;
	}

	public String getAcronym() {
		return acronym;
	}

	public Long getTaxClassId() {
		return taxClassId;
	}

	public String getTaxClass() {
		return taxClass;
	}

	public String getNumber() {
		return number;
	}

	public Long getProdGroupId() {
		return prodGroupId;
	}

	public String getProdGroup() {
		return prodGroup;
	}
	
	
}
