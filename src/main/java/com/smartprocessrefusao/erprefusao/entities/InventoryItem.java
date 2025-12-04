package com.smartprocessrefusao.erprefusao.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionReceipt;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_inventory_item")
public class InventoryItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private TypeTransactionReceipt typeReceipt;

	private Integer itemSequence;

	private BigDecimal recoveryYield;
	private BigDecimal quantity;
	private BigDecimal price;
	private BigDecimal totalValue;
	private BigDecimal quantityMco;

	@ManyToOne
	@JoinColumn(name = "inventory_id")
	private Inventory inventory;

	@ManyToOne
	@JoinColumn(name = "material_id")
	private Material material;

	@ManyToOne
	@JoinColumn(name = "partner_id")
	private Partner partner;

	public InventoryItem() {
	}

	public InventoryItem(Long id, TypeTransactionReceipt typeReceipt, Integer itemSequence, BigDecimal recoveryYield,
			BigDecimal quantity, BigDecimal price, BigDecimal totalValue, BigDecimal quantityMco, Inventory inventory,
			Material material, Partner partner) {
		this.id = id;
		this.typeReceipt = typeReceipt;
		this.itemSequence = itemSequence;
		this.recoveryYield = recoveryYield;
		this.quantity = quantity;
		this.price = price;
		this.totalValue = totalValue;
		this.quantityMco = quantityMco;
		this.inventory = inventory;
		this.material = material;
		this.partner = partner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TypeTransactionReceipt getTypeReceipt() {
		return typeReceipt;
	}

	public void setTypeReceipt(TypeTransactionReceipt typeReceipt) {
		this.typeReceipt = typeReceipt;
	}

	public Integer getItemSequence() {
		return itemSequence;
	}

	public void setItemSequence(Integer itemSequence) {
		this.itemSequence = itemSequence;
	}

	public BigDecimal getRecoveryYield() {
		return recoveryYield;
	}

	public void setRecoveryYield(BigDecimal recoveryYield) {
		this.recoveryYield = recoveryYield;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}

	public BigDecimal getQuantityMco() {
		return quantityMco;
	}

	public void setQuantityMco(BigDecimal quantityMco) {
		this.quantityMco = quantityMco;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public void calculateTotalValue() {
		if (price != null && quantity != null) {
			this.totalValue = price.multiply(quantity);
		} else {
			this.totalValue = BigDecimal.ZERO;
		}
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
		InventoryItem other = (InventoryItem) obj;
		return Objects.equals(id, other.id);
	}

}
