package com.smartprocessrefusao.erprefusao.entities;

import java.math.BigDecimal;
import java.util.Objects;

import com.smartprocessrefusao.erprefusao.entities.PK.DispatchItemPK;
import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloy;
import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloyFootage;
import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloyPol;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionOutGoing;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_dispatch_item")
public class DispatchItem {

	@EmbeddedId
	private DispatchItemPK id = new DispatchItemPK();

	private String documentNumber;
	private BigDecimal quantity;
	private BigDecimal price;
	private BigDecimal totalValue;
	private String observation;

	@Enumerated(EnumType.STRING)
	private TypeTransactionOutGoing typeDispatch;

	@Enumerated(EnumType.STRING)
	private AluminumAlloy alloy;

	@Enumerated(EnumType.STRING)
	private AluminumAlloyPol alloyPol;

	@Enumerated(EnumType.STRING)
	private AluminumAlloyFootage alloyFootage;

	public DispatchItem() {
	}

	public DispatchItem(Dispatch dispatch, Partner partner, Product product, String documentNumber,
			BigDecimal quantity, BigDecimal price, BigDecimal totalValue, String observation,
			TypeTransactionOutGoing typeDispatch, AluminumAlloy alloy, AluminumAlloyPol alloyPol,
			AluminumAlloyFootage alloyFootage) {
		id.setDispatch(dispatch);
		id.setPartner(partner);
		id.setProduct(product);
		this.documentNumber = documentNumber;
		this.quantity = quantity;
		this.price = price;
		this.totalValue = totalValue;
		this.observation = observation;
		this.typeDispatch = typeDispatch;
		this.alloy = alloy;
		this.alloyPol = alloyPol;
		this.alloyFootage = alloyFootage;
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

	public DispatchItemPK getId() {
		return id;
	}

	public void setId(DispatchItemPK id) {
		this.id = id;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
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

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public TypeTransactionOutGoing getTypeDispatch() {
		return typeDispatch;
	}

	public void setTypeDispatch(TypeTransactionOutGoing typeDispatch) {
		this.typeDispatch = typeDispatch;
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
		DispatchItem other = (DispatchItem) obj;
		return Objects.equals(id, other.id);
	}

}
