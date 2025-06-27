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
	private Long uom_id;
	private String description_uom;
	private String acronym;
	
	@NotNull(message = "Campo Classificação Fiscal requerido")
	private Long taxclass_id;
	private String description_taxclass;
	private String number;
	
	@NotNull(message = "Campo Grupo de Mercadoria requerido")
	private Long prodGroup_id;
	private String description_prodGroup;
	
	public MaterialDTO() {
		
	}

	public MaterialDTO(Long id, String description, Long uom_id, String description_uom, String acronym, Long taxclass_id, 
			String description_taxclass, String number, Long prodGroup_id, String description_prodGroup) {
		this.id = id;
		this.description = description;
		this.uom_id = uom_id;
		this.description_uom = description_uom;
		this.acronym = acronym;
		this.taxclass_id = taxclass_id;
		this.description_taxclass = description_taxclass;
		this.number = number;
		this.prodGroup_id = prodGroup_id;
		this.description_prodGroup = description_prodGroup;
	}

	public MaterialDTO(Material entity) {
		id = entity.getId();
		description = entity.getDescription();
		uom_id = entity.getUom().getId();
		description_uom = entity.getUom().getDescription();
		acronym = entity.getUom().getAcronym();
		taxclass_id = entity.getTaxclass().getId();
		description_taxclass = entity.getTaxclass().getDescription();
		number = entity.getTaxclass().getNumber();
		prodGroup_id = entity.getProdGroup().getId();
		description_prodGroup =  entity.getProdGroup().getDescription();
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public Long getUom_id() {
		return uom_id;
	}

	public String getDescription_uom() {
		return description_uom;
	}

	public String getAcronym() {
		return acronym;
	}

	public Long getTaxclass_id() {
		return taxclass_id;
	}

	public String getDescription_taxclass() {
		return description_taxclass;
	}

	public String getNumber() {
		return number;
	}

	public Long getProdGroup_id() {
		return prodGroup_id;
	}

	public String getDescription_prodGroup() {
		return description_prodGroup;
	}

	
}
