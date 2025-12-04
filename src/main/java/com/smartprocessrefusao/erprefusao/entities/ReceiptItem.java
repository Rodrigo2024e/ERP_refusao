package com.smartprocessrefusao.erprefusao.entities;

import java.math.BigDecimal;
import java.util.Objects;

import com.smartprocessrefusao.erprefusao.entities.PK.ReceiptItemPK;
import com.smartprocessrefusao.erprefusao.enumerados.TypeCosts;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionReceipt;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_receipt_item")
public class ReceiptItem {

	@EmbeddedId
	private ReceiptItemPK id = new ReceiptItemPK();

	private String documentNumber;

	private BigDecimal recoveryYield;
	private BigDecimal quantity;
	private BigDecimal price;
	private BigDecimal totalValue;
	private BigDecimal quantityMco;
	private String observation;

	@Enumerated(EnumType.STRING)
	private TypeTransactionReceipt typeReceipt;

	@Enumerated(EnumType.STRING)
	private TypeCosts typeCosts;

	public ReceiptItem() {
	}

	public ReceiptItem(Receipt receipt, Partner partner, Material material, String documentNumber,
			BigDecimal recoveryYield, BigDecimal quantity, BigDecimal price, BigDecimal totalValue,
			BigDecimal quantityMco, String observation, TypeTransactionReceipt typeReceipt, TypeCosts typeCosts) {
		id.setReceipt(receipt);
		id.setPartner(partner);
		id.setMaterial(material);
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

	public ReceiptItemPK getId() {
		return id;
	}

	public void setId(ReceiptItemPK id) {
		this.id = id;
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

	@PrePersist
	@PreUpdate
	private void calculateTotalValue() {
		if (quantity != null && price != null) {
			totalValue = quantity.multiply(price);
		} else {
			totalValue = BigDecimal.ZERO;
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
		ReceiptItem other = (ReceiptItem) obj;
		return Objects.equals(id, other.id);
	}

}
