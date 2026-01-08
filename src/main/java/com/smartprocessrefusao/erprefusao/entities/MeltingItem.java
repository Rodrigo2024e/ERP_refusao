package com.smartprocessrefusao.erprefusao.entities;

import java.math.BigDecimal;
import java.util.Objects;

import com.smartprocessrefusao.erprefusao.entities.PK.MeltingItemPK;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_melting_item")
public class MeltingItem {

	@EmbeddedId
	private MeltingItemPK id = new MeltingItemPK();

	private BigDecimal quantity;
	private BigDecimal averagePrice;
	private BigDecimal totalValue;
	private BigDecimal averageRecoveryYield;
	private BigDecimal quantityMco;
	private BigDecimal slagWeight;

	public MeltingItem() {
	}

	public MeltingItem(Melting melting, Material materialCode, BigDecimal quantity, BigDecimal averagePrice,
			BigDecimal totalValue, BigDecimal averageRecoveryYield, BigDecimal quantityMco, BigDecimal slagWeight) {
		id.setMelting(melting);
		id.setMaterial(materialCode);
		this.quantity = quantity;
		this.averagePrice = averagePrice;
		this.totalValue = totalValue;
		this.averageRecoveryYield = averageRecoveryYield;
		this.quantityMco = quantityMco;
		this.slagWeight = slagWeight;

	}

	public MeltingItemPK getId() {
		return id;
	}

	public void setId(MeltingItemPK id) {
		this.id = id;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(BigDecimal averagePrice) {
		this.averagePrice = averagePrice;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}

	public BigDecimal getAverageRecoveryYield() {
		return averageRecoveryYield;
	}

	public void setAverageRecoveryYield(BigDecimal averageRecoveryYield) {
		this.averageRecoveryYield = averageRecoveryYield;
	}

	public BigDecimal getQuantityMco() {
		return quantityMco;
	}

	public void setQuantityMco(BigDecimal quantityMco) {
		this.quantityMco = quantityMco;
	}

	public BigDecimal getSlagWeight() {
		return slagWeight;
	}

	public void setSlagWeight(BigDecimal slagWeight) {
		this.slagWeight = slagWeight;
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
		MeltingItem other = (MeltingItem) obj;
		return Objects.equals(id, other.id);
	}

}
