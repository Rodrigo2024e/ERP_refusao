package com.smartprocessrefusao.erprefusao.entities;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionOutGoing;

import jakarta.persistence.Column;
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
@Table(name = "tb_product_dispatch")
public class ProductDispatch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant moment;
	private BigDecimal amountProduct;
	private BigDecimal unitValue;
	private BigDecimal totalValue;

	@ManyToOne
	@JoinColumn(name = "num_ticket_id")
	private Ticket numTicket;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "partner_id")
	private People partner;

	@Enumerated(EnumType.STRING)
	private TypeTransactionOutGoing transaction;

	public ProductDispatch() {

	}

	public ProductDispatch(Long id, Instant moment, BigDecimal amountProduct, BigDecimal unitValue,
			BigDecimal totalValue, Ticket numTicket, Product product, People partner,
			TypeTransactionOutGoing transaction) {
		this.id = id;
		this.moment = moment;
		this.amountProduct = amountProduct;
		this.unitValue = unitValue;
		this.totalValue = totalValue;
		this.numTicket = numTicket;
		this.product = product;
		this.partner = partner;
		this.transaction = transaction;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
	}

	public BigDecimal getAmountProduct() {
		return amountProduct;
	}

	public void setAmountProduct(BigDecimal amountProduct) {
		this.amountProduct = amountProduct;
	}

	public BigDecimal getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(BigDecimal unitValue) {
		this.unitValue = unitValue;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}

	public Ticket getNumTicket() {
		return numTicket;
	}

	public void setNumTicket(Ticket numTicket) {
		this.numTicket = numTicket;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public People getPartner() {
		return partner;
	}

	public void setPartner(People partner) {
		this.partner = partner;
	}

	public TypeTransactionOutGoing getTransaction() {
		return transaction;
	}

	public void setTransaction(TypeTransactionOutGoing transaction) {
		this.transaction = transaction;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductDispatch other = (ProductDispatch) obj;
		return Objects.equals(numTicket, other.numTicket);
	}

}
