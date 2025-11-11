package com.smartprocessrefusao.erprefusao.dto;

import java.io.Serializable;

import com.smartprocessrefusao.erprefusao.entities.Material;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MaterialDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotNull(message = "O código do material é obrigatório.")
	@Min(value = 1000, message = "O código do material deve ter 4 dígitos.")
	@Max(value = 9999, message = "O código do material deve ter 4 dígitos.")
	@Column(name = "code", unique = true)
	private Long code;

	@Size(min = 3, max = 30, message = "O campo descrição deve ter entre 3 a 30 caracteres")
	private String description;

	@NotNull(message = "Informe rendimento do material")
	private Double recoveryYield;

	@NotNull(message = "Informe o tipo de material")
	private String type;

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

	public MaterialDTO() {

	}

	public MaterialDTO(Long id, Long code, String description, Double recoveryYield, String type, Long unitId,
			String acronym, Long taxClassId, String description_taxclass, Integer ncmCode, Long matGroupId,
			String description_matGroup) {
		this.id = id;
		this.code = code;
		this.description = description;
		this.recoveryYield = recoveryYield;
		this.type = type;
		this.unitId = unitId;
		this.acronym = acronym;
		this.taxClassId = taxClassId;
		this.description_taxclass = description_taxclass;
		this.ncmCode = ncmCode;
		this.matGroupId = matGroupId;
		this.description_matGroup = description_matGroup;

	}

	public MaterialDTO(Material entity) {
		id =entity.getId();
		code = entity.getCode();
		description = entity.getDescription();
		recoveryYield = entity.getRecoveryYield();
		type = entity.getType().toString();
		unitId = entity.getUnit().getId();
		acronym = entity.getUnit().getAcronym();
		taxClassId = entity.getTaxClass().getId();
		description_taxclass = entity.getTaxClass().getDescription();
		ncmCode = entity.getTaxClass().getNcmCode();
		matGroupId = entity.getMaterialGroup().getId();
		description_matGroup = entity.getMaterialGroup().getDescription();

	}

	public Long getId() {
		return id;
	}

	public Long getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public Double getRecoveryYield() {
		return recoveryYield;
	}

	public String getType() {
		return type;
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
