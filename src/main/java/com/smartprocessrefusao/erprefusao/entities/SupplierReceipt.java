package com.smartprocessrefusao.erprefusao.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.smartprocessrefusao.erprefusao.audit.Auditable;
import com.smartprocessrefusao.erprefusao.enumerados.TypeCosts;
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
@Table(name = "tb_supplier_receipt")
public class SupplierReceipt extends Auditable<String> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate dateReceipt;
	private BigDecimal amountSupplier;
	private BigDecimal unitValue;
	private BigDecimal totalValue;

	@ManyToOne
	@JoinColumn(name = "input_id")
	private Input input;

	@ManyToOne
	@JoinColumn(name = "partner_id")
	private People partner;

	@Enumerated(EnumType.STRING)
	private TypeTransactionReceipt transaction;

	@Enumerated(EnumType.STRING)
	private TypeCosts costs;

	public SupplierReceipt() {

	}

	public SupplierReceipt(Long id, LocalDate dateReceipt, BigDecimal amountSupplier, BigDecimal unitValue,
			BigDecimal totalValue, Input input, People partner, TypeTransactionReceipt transaction, TypeCosts costs) {
		this.id = id;
		this.dateReceipt = dateReceipt;
		this.amountSupplier = amountSupplier;
		this.unitValue = unitValue;
		this.totalValue = totalValue;
		this.input = input;
		this.partner = partner;
		this.transaction = transaction;
		this.costs = costs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDateReceipt() {
		return dateReceipt;
	}

	public void setDateReceipt(LocalDate dateReceipt) {
		this.dateReceipt = dateReceipt;
	}

	public BigDecimal getAmountSupplier() {
		return amountSupplier;
	}

	public void setAmountSupplier(BigDecimal amountSupplier) {
		this.amountSupplier = amountSupplier;
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

	public TypeTransactionReceipt getTransaction() {
		return transaction;
	}

	public void setTransaction(TypeTransactionReceipt transaction) {
		this.transaction = transaction;
	}

	public TypeCosts getCosts() {
		return costs;
	}

	public void setCosts(TypeCosts costs) {
		this.costs = costs;
	}

	@Override
	public int hashCode() {
		return Objects.hash(input);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SupplierReceipt other = (SupplierReceipt) obj;
		return Objects.equals(input, other.input);
	}

}
