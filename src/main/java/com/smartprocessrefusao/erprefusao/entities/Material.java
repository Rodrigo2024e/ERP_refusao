package com.smartprocessrefusao.erprefusao.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.smartprocessrefusao.erprefusao.audit.Auditable;
import com.smartprocessrefusao.erprefusao.enumerados.TypeMaterial;

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
@Table(name = "tb_material")
@Inheritance(strategy = InheritanceType.JOINED)
public class Material extends Auditable<String> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long materialCode;
	private String description;

	@Enumerated(EnumType.STRING)
	private TypeMaterial type;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_id", nullable = false)
	private Unit unit;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "tax_class_id", nullable = false)
	private TaxClassification taxClass;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "material_group_id", nullable = false)
	private MaterialGroup materialGroup;

	@OneToMany(mappedBy = "material", fetch = FetchType.LAZY)
	private List<MaterialStockBalance> stockBalances = new ArrayList<>();

	@OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReceiptItem> receiptItems = new ArrayList<>();

	@OneToMany(mappedBy = "id.material", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MeltingItem> meltingItems = new ArrayList<>();

	@OneToMany(mappedBy = "material")
	private List<InventoryItem> inventoryItems = new ArrayList<>();

	public Material() {

	}

	public Material(Long id, Long materialCode, String description, TypeMaterial type, Unit unit,
			TaxClassification taxClass, MaterialGroup materialGroup, MaterialStockBalance materialStockBalance) {
		this.id = id;
		this.materialCode = materialCode;
		this.description = description;
		this.type = type;
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

	public Long getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(Long materialCode) {
		this.materialCode = materialCode;
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

	public List<MaterialStockBalance> getStockBalances() {
		return stockBalances;
	}

	public void setStockBalances(List<MaterialStockBalance> stockBalances) {
		this.stockBalances = stockBalances;
	}

	public List<ReceiptItem> getReceiptItems() {
		return receiptItems;
	}

	public void setReceiptItems(List<ReceiptItem> receiptItems) {
		this.receiptItems = receiptItems;
	}

	public List<MeltingItem> getMeltingItems() {
		return meltingItems;
	}

	public void setMeltingItems(List<MeltingItem> meltingItems) {
		this.meltingItems = meltingItems;
	}

	public List<InventoryItem> getInventoryItems() {
		return inventoryItems;
	}

	public void setInventoryItems(List<InventoryItem> inventoryItems) {
		this.inventoryItems = inventoryItems;
	}

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null) return false;
	    if (getClass() != o.getClass()) return false;

	    Material other = (Material) o;
	    return id != null && id.equals(other.id);
	}

	@Override
	public int hashCode() {
	    return getClass().hashCode();
	}

}
