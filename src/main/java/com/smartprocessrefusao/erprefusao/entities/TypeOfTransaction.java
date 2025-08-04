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
@Table(name = "tb_type_transaction")
public class TypeOfTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;

	@ManyToMany(mappedBy = "transactions")
	private Set<Movement> movements = new HashSet<>();

	public TypeOfTransaction() {

	}

	public TypeOfTransaction(Long id, String description) {

		this.id = id;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Movement> getMovements() {
		return movements;
	}

	public void setMovements(Set<Movement> movements) {
		this.movements = movements;
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
		TypeOfTransaction other = (TypeOfTransaction) obj;
		return Objects.equals(id, other.id);
	}

}
