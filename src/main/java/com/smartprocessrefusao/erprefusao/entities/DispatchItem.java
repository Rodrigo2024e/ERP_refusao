package com.smartprocessrefusao.erprefusao.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloy;
import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloyFootage;
import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloyPol;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionOutGoing;

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
@Table(name = "tb_dispatch_item")
public class DispatchItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "dispatch_id", nullable = false)
	private Dispatch dispatch;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "partner_id", nullable = false)
	private Partner partner;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(name = "item_sequence", nullable = false)
	private Integer itemSequence;

	private String documentNumber;
	private BigDecimal quantity;
	private BigDecimal price;
	@Column(nullable = false)
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

	public DispatchItem(Long id, Dispatch dispatch, Partner partner, Product product, Integer itemSequence,
			String documentNumber, BigDecimal quantity, BigDecimal price, BigDecimal totalValue, String observation,
			TypeTransactionOutGoing typeDispatch, AluminumAlloy alloy, AluminumAlloyPol alloyPol,
			AluminumAlloyFootage alloyFootage) {
		this.id = id;
		this.dispatch = dispatch;
		this.partner = partner;
		this.product = product;
		this.itemSequence = itemSequence;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Dispatch getDispatch() {
		return dispatch;
	}

	public void setDispatch(Dispatch dispatch) {
		this.dispatch = dispatch;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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
