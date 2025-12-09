package com.smartprocessrefusao.erprefusao.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.smartprocessrefusao.erprefusao.audit.Auditable;
import com.smartprocessrefusao.erprefusao.enumerados.TypeMaterial;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_material")
@Inheritance(strategy = InheritanceType.JOINED)
public class Material extends Auditable<String> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long code;
	private String description;

	@Enumerated(EnumType.STRING)
	private TypeMaterial type;

	@ManyToOne
	@JoinColumn(name = "unit_id")
	private Unit unit;

	@ManyToOne
	@JoinColumn(name = "tax_class_id")
	private TaxClassification taxClass;

	@ManyToOne
	@JoinColumn(name = "material_group_id")
	private MaterialGroup materialGroup;

	@OneToMany(mappedBy = "id.material", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReceiptItem> receiptItems = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "stock_balance_id")
	private StockBalance stockBalance;

	@OneToMany(mappedBy = "material")
	private List<InventoryItem> InventoryItems = new ArrayList<>();

	public Material() {

	}

	public Material(Long code, String description, TypeMaterial type, Unit unit, TaxClassification taxClass,
			MaterialGroup materialGroup) {
		this.code = code;
		this.description = description;
		this.type = type;
		this.unit = unit;
		this.taxClass = taxClass;
		this.materialGroup = materialGroup;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TypeMaterial getType() {
		return type;
	}

	public void setType(TypeMaterial type) {
		this.type = type;
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

	public List<ReceiptItem> getReceiptItems() {
		return receiptItems;

	}

	public StockBalance getStockBalance() {
		return stockBalance;
	}

	public void setStockBalance(StockBalance stockBalance) {
		this.stockBalance = stockBalance;
	}

	public List<InventoryItem> getInventoryItems() {
		return InventoryItems;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(code);
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
		Material other = (Material) obj;
		return Objects.equals(code, other.code);
	}

	
}
