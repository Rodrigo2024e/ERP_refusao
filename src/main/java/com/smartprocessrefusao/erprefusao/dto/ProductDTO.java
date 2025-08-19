package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.Product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductDTO {

	private Long id;

	private String typeMaterial;

	@Size(min = 3, max = 30, message = "O campo descrição deve ter entre 3 a 30 caracteres")
	private String description;

	@NotNull(message = "A liga deve ser composta de 4 dígitos")
	private Integer alloy;

	@NotNull(message = "A polegada deve ser composta de 1 dígito")
	private Integer billetDiameter;

	@NotNull(message = "A metragen deve ser informada")
	private Double billetLength;

	@NotNull(message = "Campo Unidade de Medida requerido")
	private Long uomId;
	private String acronym;

	@NotNull(message = "Campo Classificação Fiscal requerida")
	private Long taxClassId;
	private String description_taxclass;
	private Integer number;

	@NotNull(message = "Campo Grupo de Material requerido")
	private Long matGroupId;
	private String description_matGroup;

	public ProductDTO() {

	}

	public ProductDTO(Long id, String typeMaterial, String description, Integer alloy, Integer billetDiameter,
			Double billetLength, Long uomId, String acronym, Long taxClassId, String description_taxclass,
			Integer number, Long matGroupId, String description_matGroup) {
		this.id = id;
		this.typeMaterial = typeMaterial;
		this.description = description;
		this.alloy = alloy;
		this.billetDiameter = billetDiameter;
		this.billetLength = billetLength;
		this.uomId = uomId;
		this.acronym = acronym;
		this.taxClassId = taxClassId;
		this.description_taxclass = description_taxclass;
		this.number = number;
		this.matGroupId = matGroupId;
		this.description_matGroup = description_matGroup;
	}

	public ProductDTO(Product entity) {
		id = entity.getId();
		typeMaterial = entity.getTypeMaterial().toString();
		description = entity.getDescription();
		alloy = entity.getAlloy();
		billetDiameter = entity.getBilletDiameter();
		billetLength = entity.getBilletLength();
		uomId = entity.getUomMaterial().getId();
		acronym = entity.getUomMaterial().getAcronym();
		taxClassId = entity.getTaxClassMaterial().getId();
		description_taxclass = entity.getTaxClassMaterial().getDescription();
		number = entity.getTaxClassMaterial().getNumber();
		matGroupId = entity.getMaterialGroup().getId();
		description_matGroup = entity.getMaterialGroup().getDescription();
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

	public Double getBilletLength() {
		return billetLength;
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
