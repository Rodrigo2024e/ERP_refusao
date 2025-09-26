package com.smartprocessrefusao.erprefusao.entities;

import java.io.Serializable;
import java.util.Objects;

import com.smartprocessrefusao.erprefusao.audit.Auditable;
import com.smartprocessrefusao.erprefusao.enumerados.TypeMaterial;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_material")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Material extends Auditable<String> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private TypeMaterial typeMaterial;

	@ManyToOne
	@JoinColumn(name = "uom_material_id")
	private Unit uomMaterial;

	@ManyToOne
	@JoinColumn(name = "tax_class_material_id")
	private TaxClassification taxClassMaterial;

	@ManyToOne
	@JoinColumn(name = "material_group_id")
	private MaterialGroup materialGroup;

	public Material() {

	}

	public Material(Long id, TypeMaterial typeMaterial, Unit uomMaterial, TaxClassification taxClassMaterial,
			MaterialGroup materialGroup) {
		this.id = id;
		this.typeMaterial = typeMaterial;
		this.uomMaterial = uomMaterial;
		this.taxClassMaterial = taxClassMaterial;
		this.materialGroup = materialGroup;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TypeMaterial getTypeMaterial() {
		return typeMaterial;
	}

	public void setTypeMaterial(TypeMaterial typeMaterial) {
		this.typeMaterial = typeMaterial;
	}

	public Unit getUomMaterial() {
		return uomMaterial;
	}

	public void setUomMaterial(Unit uomMaterial) {
		this.uomMaterial = uomMaterial;
	}

	public TaxClassification getTaxClassMaterial() {
		return taxClassMaterial;
	}

	public void setTaxClassMaterial(TaxClassification taxClassMaterial) {
		this.taxClassMaterial = taxClassMaterial;
	}

	public MaterialGroup getMaterialGroup() {
		return materialGroup;
	}

	public void setMaterialGroup(MaterialGroup materialGroup) {
		this.materialGroup = materialGroup;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Material other = (Material) obj;
		return Objects.equals(id, other.id);
	}

}
