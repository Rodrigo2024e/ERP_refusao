package com.smartprocessrefusao.erprefusao.entities;

import java.util.Objects;

import com.smartprocessrefusao.erprefusao.projections.IdProjection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_product")
public class Product implements IdProjection<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private Integer alloy;
	private Integer inch;
	private Integer length;
	
	@ManyToOne
	@JoinColumn(name = "uomId")
	private Unit uom;
	
	@ManyToOne
	@JoinColumn(name = "taxclassId")
	private TaxClassification taxclass;
	
	@ManyToOne
	@JoinColumn(name = "prodGroupId")
	private ProductGroup prodGroup;
	
	public Product () {
		
	}

	public Product(Long id, String description, Integer alloy, Integer inch, Integer length, Unit uom,
			TaxClassification taxclass, ProductGroup prodGroup) {
		this.id = id;
		this.description = description;
		this.alloy = alloy;
		this.inch = inch;
		this.length = length;
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

	public Integer getAlloy() {
		return alloy;
	}

	public void setAlloy(Integer alloy) {
		this.alloy = alloy;
	}

	public Integer getInch() {
		return inch;
	}

	public void setInch(Integer inch) {
		this.inch = inch;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Unit getUom() {
		return uom;
	}

	public void setUom(Unit uom) {
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
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}
	
}
