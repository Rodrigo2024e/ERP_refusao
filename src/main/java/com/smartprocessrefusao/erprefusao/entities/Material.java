package com.smartprocessrefusao.erprefusao.entities;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_material")
public class Material {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "uom_id")
	private UnitOfMeasure uom;
	
	@ManyToOne
	@JoinColumn(name = "taxclass_id")
	private TaxClassification taxclass;
	
	@ManyToOne
	@JoinColumn(name = "prodGroup_id")
	private ProductGroup prodGroup;
	
	public Material () {
		
	}

	public Material(Long id, String description, UnitOfMeasure uom, TaxClassification taxclass,
			ProductGroup prodGroup) {
		super();
		this.id = id;
		this.description = description;
		this.uom = uom;
		this.taxclass = taxclass;
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

	public UnitOfMeasure getUom() {
		return uom;
	}

	public void setUom(UnitOfMeasure uom) {
		this.uom = uom;
	}

	public TaxClassification getTaxclass() {
		return taxclass;
	}

	public void setTaxclass(TaxClassification taxclass) {
		this.taxclass = taxclass;
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
