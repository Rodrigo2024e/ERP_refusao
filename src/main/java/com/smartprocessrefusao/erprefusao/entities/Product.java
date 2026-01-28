package com.smartprocessrefusao.erprefusao.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.smartprocessrefusao.erprefusao.audit.Auditable;
import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloy;
import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloyFootage;
import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloyPol;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_product")
@Inheritance(strategy = InheritanceType.JOINED)
public class Product extends Auditable<String> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long productCode;
	private String description;

	@Enumerated(EnumType.STRING)
	private AluminumAlloy alloy;

	@Enumerated(EnumType.STRING)
	private AluminumAlloyPol alloyPol;

	@Enumerated(EnumType.STRING)
	private AluminumAlloyFootage alloyFootage;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_id", nullable = false)
	private Unit unit;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "tax_class_id", nullable = false)
	private TaxClassification taxClass;

	@ManyToOne
	@JoinColumn(name = "material_group_id")
	private MaterialGroup materialGroup;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DispatchItem> dispatchItems = new ArrayList<>();

	public Product() {

	}

	public Product(Long id, Long productCode, String description, AluminumAlloy alloy, AluminumAlloyPol alloyPol,
			AluminumAlloyFootage alloyFootage, Unit unit, TaxClassification taxClass, MaterialGroup materialGroup) {
		this.id = id;
		this.productCode = productCode;
		this.description = description;
		this.alloy = alloy;
		this.alloyPol = alloyPol;
		this.alloyFootage = alloyFootage;
		this.unit = unit;
		this.taxClass = taxClass;
		this.materialGroup = materialGroup;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductCode() {
		return productCode;
	}

	public void setProductCode(Long productCode) {
		this.productCode = productCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AluminumAlloy getAlloy() {
		return alloy;
	}

	public void setAlloy(AluminumAlloy alloy) {
		this.alloy = alloy;
	}

	public AluminumAlloyPol getAlloyPol() {
		return alloyPol;
	}

	public void setAlloyPol(AluminumAlloyPol alloyPol) {
		this.alloyPol = alloyPol;
	}

	public AluminumAlloyFootage getAlloyFootage() {
		return alloyFootage;
	}

	public void setAlloyFootage(AluminumAlloyFootage alloyFootage) {
		this.alloyFootage = alloyFootage;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public TaxClassification getTaxClass() {
		return taxClass;
	}

	public void setTaxClass(TaxClassification taxClass) {
		this.taxClass = taxClass;
	}

	public MaterialGroup getMaterialGroup() {
		return materialGroup;
	}

	public void setMaterialGroup(MaterialGroup materialGroup) {
		this.materialGroup = materialGroup;
	}

	public List<DispatchItem> getDispatchItems() {
		return dispatchItems;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(productCode);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(productCode, other.productCode);
	}

}
