package com.smartprocessrefusao.erprefusao.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_expenses")
public class TypeExpenses {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private boolean expenses;
	private boolean directCosts;
	private boolean indirectCosts;
	private boolean investiment;

	@ManyToMany(mappedBy = "expenses")
	private Set<Movement> movements = new HashSet<>();

	public TypeExpenses() {

	}

	public TypeExpenses(Long id, boolean expenses, boolean directCosts, boolean indirectCosts, boolean investiment) {
		this.id = id;
		this.expenses = expenses;
		this.directCosts = directCosts;
		this.indirectCosts = indirectCosts;
		this.investiment = investiment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isExpenses() {
		return expenses;
	}

	public void setExpenses(boolean expenses) {
		this.expenses = expenses;
	}

	public boolean isDirectCosts() {
		return directCosts;
	}

	public void setDirectCosts(boolean directCosts) {
		this.directCosts = directCosts;
	}

	public boolean isIndirectCosts() {
		return indirectCosts;
	}

	public void setIndirectCosts(boolean indirectCosts) {
		this.indirectCosts = indirectCosts;
	}

	public boolean isInvestiment() {
		return investiment;
	}

	public void setInvestiment(boolean investiment) {
		this.investiment = investiment;
	}

	public Set<Movement> getMovements() {
		return movements;
	}

	public void setMovements(Set<Movement> movements) {
		this.movements = movements;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TypeExpenses other = (TypeExpenses) obj;
		return Objects.equals(id, other.id);
	}

}
