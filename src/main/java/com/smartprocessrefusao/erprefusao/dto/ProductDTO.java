package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.Product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductDTO {

	private Long id;

	@Size(min = 3, max = 30, message = "O campo descrição deve ter entre 3 a 30 caracteres")
	private String description;

	@NotNull(message = "A liga deve ser composta de 4 dígitos")
	private Integer alloy;

	@NotNull(message = "A polegada deve ser composta de 1 dígito")
	private Integer inch;

	@NotNull(message = "A metragen deve ser informada")
	private Double length;

	@NotNull(message = "Campo Unidade de Medida requerido")
	private Long uomId;
	private String acronym;

	@NotNull(message = "Campo Classificação Fiscal requerida")
	private Long taxClassId;
	private String taxClass;
	private Integer number;

	@NotNull(message = "Campo Grupo de Mercadoria requerido")
	private Long prodGroupId;
	private String prodGroup;

	public ProductDTO() {

	}

	public ProductDTO(Long id, String description, Integer alloy, Integer inch, Double length, Long uomId,
			String acronym, Long taxClassId, String taxClass, Integer number, Long prodGroupId, String prodGroup) {
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

	public Double getLength() {
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

	public Integer getNumber() {
		return number;
	}

	public Long getProdGroupId() {
		return prodGroupId;
	}

	public String getProdGroup() {
		return prodGroup;
	}

}
