package com.smartprocessrefusao.erprefusao.entities;

import java.math.BigDecimal;
import java.util.Objects;

import com.smartprocessrefusao.erprefusao.enumerados.TypeExpenses;

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
@Table(name = "tb_movement")
public class Movement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private BigDecimal amountMaterial;
	private BigDecimal unitValue;
	private BigDecimal totalValue;
	private BigDecimal metalYield;
	private BigDecimal metalWeight;
	private BigDecimal slag;

	@ManyToOne
	@JoinColumn(name = "num_ticket_id")
	private Ticket numTicket;

	@ManyToOne
	@JoinColumn(name = "input_id")
	private Input input;

	@ManyToOne
	@JoinColumn(name = "partner_id")
	private People partner;

	@ManyToOne
	@JoinColumn(name = "transaction_id")
	private TypeTransaction transaction;

	@Enumerated(EnumType.STRING)
	private TypeExpenses expenses;

	public Movement() {

	}

	public Movement(Long id, BigDecimal amountMaterial, BigDecimal unitValue, BigDecimal totalValue,
			BigDecimal metalYield, BigDecimal metalWeight, BigDecimal slag, Ticket numTicket, Input input,
			People partner, TypeTransaction transaction, TypeExpenses expenses) {
		this.id = id;
		this.amountMaterial = amountMaterial;
		this.unitValue = unitValue;
		this.totalValue = totalValue;
		this.metalYield = metalYield;
		this.metalWeight = metalWeight;
		this.slag = slag;
		this.numTicket = numTicket;
		this.input = input;
		this.partner = partner;
		this.transaction = transaction;
		this.expenses = expenses;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmountMaterial() {
		return amountMaterial;
	}

	public void setAmountMaterial(BigDecimal amountMaterial) {
		this.amountMaterial = amountMaterial;
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

	public BigDecimal getMetalYield() {
		return metalYield;
	}

	public void setMetalYield(BigDecimal metalYield) {
		this.metalYield = metalYield;
	}

	public BigDecimal getMetalWeight() {
		return metalWeight;
	}

	public void setMetalWeight(BigDecimal metalWeight) {
		this.metalWeight = metalWeight;
	}

	public BigDecimal getSlag() {
		return slag;
	}

	public void setSlag(BigDecimal slag) {
		this.slag = slag;
	}

	public Ticket getNumTicket() {
		return numTicket;
	}

	public void setNumTicket(Ticket numTicket) {
		this.numTicket = numTicket;
	}

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	public People getPartner() {
		return partner;
	}

	public void setPartner(People partner) {
		this.partner = partner;
	}

	public TypeTransaction getTransaction() {
		return transaction;
	}

	public void setTransaction(TypeTransaction transaction) {
		this.transaction = transaction;
	}

	public TypeExpenses getExpenses() {
		return expenses;
	}

	public void setExpenses(TypeExpenses expenses) {
		this.expenses = expenses;
	}

	@Override
	public int hashCode() {
		return Objects.hash(numTicket);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movement other = (Movement) obj;
		return Objects.equals(numTicket, other.numTicket);
	}

}
