package com.smartprocessrefusao.erprefusao.entities;

import java.util.HashSet;
import java.util.Set;

import com.smartprocessrefusao.erprefusao.enumerados.TypeMaterial;
import com.smartprocessrefusao.erprefusao.projections.IdProjection;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_input")
public class Input extends Material implements IdProjection<Long> {
	private static final long serialVersionUID = 1L;

	private String description;

	@OneToMany(mappedBy = "input")
	private Set<ScrapReceipt> scraptReceipts = new HashSet<>();

	@OneToMany(mappedBy = "input")
	private Set<SupplierReceipt> supplierReceipts = new HashSet<>();

	public Input() {

	}

	public Input(Long id, TypeMaterial typeMaterial, Unit uomMaterial, TaxClassification taxClassMaterial,
			MaterialGroup prodGroupMaterial, String description) {
		super(id, typeMaterial, uomMaterial, taxClassMaterial, prodGroupMaterial);
		this.description = description;

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<ScrapReceipt> getScraptReceipts() {
		return scraptReceipts;
	}

	public Set<SupplierReceipt> getSupplierReceipts() {
		return supplierReceipts;
	}

}
