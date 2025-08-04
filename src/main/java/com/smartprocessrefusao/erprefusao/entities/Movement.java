package com.smartprocessrefusao.erprefusao.entities;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_movement")
public class Movement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private BigDecimal  amountMaterial;
	private BigDecimal  unitValue;
	private BigDecimal  totalValue;
	private BigDecimal  metalYield;
	private BigDecimal  metalWeight;
	private BigDecimal  slag;
		
	@ManyToOne
	@JoinColumn(name = "numTicket_id")
	private Ticket numTicket;

	@ManyToMany
	@JoinTable(name = "tb_movement_material",
			joinColumns = @JoinColumn(name = "movement_mat_id"),
			inverseJoinColumns = @JoinColumn(name = "material_id"))
	Set<Material> materials = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "tb_movement_partner",
			joinColumns = @JoinColumn(name = "movement_par_id"),
			inverseJoinColumns = @JoinColumn(name = "partner_id"))
	Set<Partner> partners = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "tb_movement_transaction",
			joinColumns = @JoinColumn(name = "movement_tra_id"),
			inverseJoinColumns = @JoinColumn(name = "transaction_id"))
	Set<TypeOfTransaction> transactions = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name = "tb_movement_expense",
			joinColumns = @JoinColumn(name = "movement_exp_id"),
			inverseJoinColumns = @JoinColumn(name = "expense_id"))
	Set<TypeExpenses> expenses = new HashSet<>();

	public Movement() {
		
		}

	public Movement(Long id, BigDecimal amountMaterial, BigDecimal unitValue, BigDecimal totalValue,
			BigDecimal metalYield, BigDecimal metalWeight, BigDecimal slag, Ticket numTicket) {

		this.id = id;
		this.amountMaterial = amountMaterial;
		this.unitValue = unitValue;
		this.totalValue = totalValue;
		this.metalYield = metalYield;
		this.metalWeight = metalWeight;
		this.slag = slag;
		this.numTicket = numTicket;
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

	public Set<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(Set<Material> materials) {
		this.materials = materials;
	}

	public Set<Partner> getPartners() {
		return partners;
	}

	public void setPartners(Set<Partner> partners) {
		this.partners = partners;
	}

	public Set<TypeOfTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<TypeOfTransaction> transactions) {
		this.transactions = transactions;
	}

	public Set<TypeExpenses> getExpenses() {
		return expenses;
	}

	public void setExpenses(Set<TypeExpenses> expenses) {
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
