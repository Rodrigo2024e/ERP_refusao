package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;

import com.smartprocessrefusao.erprefusao.entities.Inventory;
import com.smartprocessrefusao.erprefusao.entities.InventoryItem;
import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.entities.Partner;

public class InventoryItemDTO {

	private Long id;

	private String typeReceipt;

	private Integer itemSequence;

	private BigDecimal recoveryYield;
	private BigDecimal quantity;
	private BigDecimal price;
	private BigDecimal totalValue;
	private BigDecimal quantityMco;

	private Inventory inventory;

	private Material material;

	private Partner partner;

	public InventoryItemDTO(Long id, String typeReceipt, Integer itemSequence, BigDecimal recoveryYield,
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

	public InventoryItemDTO(InventoryItem entity) {
		id = entity.getId();
		typeReceipt = entity.getTypeReceipt().toString();
		itemSequence = entity.getItemSequence();
		recoveryYield = entity.getRecoveryYield();
		quantity = entity.getQuantity();
		price = entity.getPrice();
		totalValue = entity.getTotalValue();
		quantityMco = entity.getQuantityMco();
		inventory = entity.getInventory();
		material = entity.getMaterial();
		partner = entity.getPartner();
	}

	public Long getId() {
		return id;
	}

	public String getTypeReceipt() {
		return typeReceipt;
	}

	public Integer getItemSequence() {
		return itemSequence;
	}

	public BigDecimal getRecoveryYield() {
		return recoveryYield;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public BigDecimal getQuantityMco() {
		return quantityMco;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public Material getMaterial() {
		return material;
	}

	public Partner getPartner() {
		return partner;
	}

}
