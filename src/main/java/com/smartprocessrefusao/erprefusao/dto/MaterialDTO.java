package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.Material;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MaterialDTO {

	private Long id;
	
	@Size(min = 3, max = 30, message = "O campo nome deve ter entre 3 a 30 caracteres")
	@NotBlank(message = "Campo requerido")
	private String description;
	
	@NotNull(message = "Campo Unidade de Medida requerido")
	private Long uomId;
	private String description_uom;
	private String acronym;
	
	@NotNull(message = "Campo Classificação Fiscal requerida")
	private Long taxclassId;
	private String description_taxclass;
	private String number;
	
	@NotNull(message = "Campo Grupo de Mercadoria requerido")
	private Long prodGroupId;
	private String description_prodGroup;
	
	public MaterialDTO() {
		
	}

	public MaterialDTO(Long id, String description, Long uomId, String description_uom, String acronym, Long taxclassId, 
			String description_taxclass, String number, Long prodGroupId, String description_prodGroup) {
		this.id = id;
		this.description = description;
		this.uomId = uomId;
		this.description_uom = description_uom;
		this.acronym = acronym;
		this.taxclassId = taxclassId;
		this.description_taxclass = description_taxclass;
		this.number = number;
		this.prodGroupId = prodGroupId;
		this.description_prodGroup = description_prodGroup;
	}

	public MaterialDTO(Material entity) {
		id = entity.getId();
		description = entity.getDescription();
		uomId = entity.getUom().getId();
		description_uom = entity.getUom().getDescription();
		acronym = entity.getUom().getAcronym();
		taxclassId = entity.getTaxclass().getId();
		description_taxclass = entity.getTaxclass().getDescription();
		number = entity.getTaxclass().getNumber();
		prodGroupId = entity.getProdGroup().getId();
		description_prodGroup =  entity.getProdGroup().getDescription();
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public Long getUomId() {
		return uomId;
	}

	public String getDescription_uom() {
		return description_uom;
	}

	public String getAcronym() {
		return acronym;
	}

	public Long getTaxclassId() {
		return taxclassId;
	}

	public String getDescription_taxclass() {
		return description_taxclass;
	}

	public String getNumber() {
		return number;
	}

	public Long getProdGroupId() {
		return prodGroupId;
	}

	public String getDescription_prodGroup() {
		return description_prodGroup;
	}

	
}
