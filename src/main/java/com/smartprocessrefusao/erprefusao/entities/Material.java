package com.smartprocessrefusao.erprefusao.entities;

import java.util.Objects;

import com.smartprocessrefusao.erprefusao.projections.IdProjection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tb_material")
public class Material implements IdProjection<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;

	@ManyToOne
	@JoinColumn(name = "uom_id")
	private Unit uom;

	@ManyToOne
	@JoinColumn(name = "taxClass_id")
	private TaxClassification taxClass;

	@ManyToOne
	@JoinColumn(name = "prodGroup_id")
	private ProductGroup prodGroup;

	public Material() {

	}

	public Material(Long id, String description, Unit uom, TaxClassification taxClass, ProductGroup prodGroup) {
		super();
		this.id = id;
		this.description = description;
		this.uom = uom;
		this.taxClass = taxClass;
		this.prodGroup = prodGroup;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Unit getUom() {
		return uom;
	}

	public void setUom(Unit uom) {
		this.uom = uom;
	}

	public TaxClassification getTaxClass() {
		return taxClass;
	}

	public void setTaxClass(TaxClassification taxClass) {
		this.taxClass = taxClass;
	}

	public ProductGroup getProdGroup() {
		return prodGroup;
	}

	public void setProdGroup(ProductGroup prodGroup) {
		this.prodGroup = prodGroup;
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
