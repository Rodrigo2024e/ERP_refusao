package com.smartprocessrefusao.erprefusao.dto;

import java.io.Serializable;

import com.smartprocessrefusao.erprefusao.entities.Product;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotNull(message = "Favor Informar o código do produto ")
	@Min(value = 1000, message = "O código do material deve ter 4 dígitos.")
	@Max(value = 9999, message = "O código do material deve ter 4 dígitos.")
	@Column(name = "code", unique = true)
	private Long productCode;

	@Size(min = 3, max = 30, message = "O campo descrição deve ter entre 3 a 30 caracteres")
	private String description;

	@NotNull(message = "Favor informar a liga do tarugo.")
	private String alloy;

	@NotNull(message = "Favor informar a polegada do tarugo.")
	private String alloyPol;

	@NotNull(message = "Favor informar o comprimento do tarugo.")
	private String alloyFootage;

	@NotNull(message = "Campo Unidade de Medida requerido")
	private Long unitId;
	private String acronym;

	@NotNull(message = "Campo Classificação Fiscal requerida")
	private Long taxClassId;
	private String description_taxclass;
	private Integer ncmCode;

	@NotNull(message = "Campo Grupo de Material requerido")
	private Long matGroupId;
	private String description_matGroup;

	public ProductDTO() {

	}

	public ProductDTO(Long id, Long productCode, String description, String alloy, String alloyPol, String alloyFootage,
			Long unitId, String acronym, Long taxClassId, String description_taxclass, Integer ncmCode, Long matGroupId,
			String description_matGroup) {
		this.id = id;
		this.productCode = productCode;
		this.description = description;
		this.alloy = alloy;
		this.alloyPol = alloyPol;
		this.alloyFootage = alloyFootage;
		this.unitId = unitId;
		this.acronym = acronym;
		this.taxClassId = taxClassId;
		this.description_taxclass = description_taxclass;
		this.ncmCode = ncmCode;
		this.matGroupId = matGroupId;
		this.description_matGroup = description_matGroup;
	}

	public ProductDTO(Product entity) {
		id = entity.getId();
		productCode = entity.getProductCode();
		description = entity.getDescription();
		alloy = entity.getAlloy().toString();
		alloyPol = entity.getAlloyPol().toString();
		alloyFootage = entity.getAlloyFootage().toString();
		unitId = entity.getUnit().getId();
		acronym = entity.getUnit().getAcronym();
		taxClassId = entity.getTaxClass().getId();
		description_taxclass = entity.getTaxClass().getDescription();
		ncmCode = entity.getTaxClass().getNcmCode();
		matGroupId = entity.getMaterialGroup().getId();
		description_matGroup = entity.getMaterialGroup().getDescription();

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
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
