package com.smartprocessrefusao.erprefusao.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.smartprocessrefusao.erprefusao.enumerados.TypeCosts;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionReceipt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_receipt_item")
public class ReceiptItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "receipt_id", nullable = false)
	private Receipt receipt;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "partner_id", nullable = false)
	private Partner partner;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "material_id", nullable = false)
	private Material material;

	@Column(name = "item_sequence", nullable = false)
	private Integer itemSequence;

	private String documentNumber;
	private BigDecimal recoveryYield;
	private BigDecimal quantity;
	private BigDecimal price;
	@Column(nullable = false)
	private BigDecimal totalValue;
	@Column(nullable = false)
	private BigDecimal quantityMco;
	private String observation;

	@Enumerated(EnumType.STRING)
	private TypeTransactionReceipt typeReceipt;

	@Enumerated(EnumType.STRING)
	private TypeCosts typeCosts;

	public ReceiptItem() {
	}

	public ReceiptItem(Long id, Receipt receipt, Partner partner, Material material, Integer itemSequence,
			String documentNumber, BigDecimal recoveryYield, BigDecimal quantity, BigDecimal price,
			BigDecimal totalValue, BigDecimal quantityMco, String observation, TypeTransactionReceipt typeReceipt,
			TypeCosts typeCosts) {
		this.id = id;
		this.receipt = receipt;
		this.partner = partner;
		this.material = material;
		this.itemSequence = itemSequence;
		this.documentNumber = documentNumber;
		this.recoveryYield = recoveryYield;
		this.quantity = quantity;
		this.price = price;
		this.totalValue = totalValue;
		this.quantityMco = quantityMco;
		this.observation = observation;
		this.typeReceipt = typeReceipt;
		this.typeCosts = typeCosts;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Integer getItemSequence() {
		return itemSequence;
	}

	public void setItemSequence(Integer itemSequence) {
		this.itemSequence = itemSequence;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
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

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public TypeTransactionReceipt getTypeReceipt() {
		return typeReceipt;
	}

	public void setTypeReceipt(TypeTransactionReceipt typeReceipt) {
		this.typeReceipt = typeReceipt;
	}

	public TypeCosts getTypeCosts() {
		return typeCosts;
	}

	public void setTypeCosts(TypeCosts typeCosts) {
		this.typeCosts = typeCosts;
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
		ReceiptItem other = (ReceiptItem) obj;
		return Objects.equals(id, other.id);
	}

}
