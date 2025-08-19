package com.smartprocessrefusao.erprefusao.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_taxClassification")
public class TaxClassification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private Integer number;

	@OneToMany(mappedBy = "taxClassMaterial")
	private Set<Material> materials = new HashSet<>();

//	@OneToMany(mappedBy = "taxClassProduct")
//	private Set<Product> products = new HashSet<>();

	public TaxClassification() {

	}

	public TaxClassification(Long id, String description, Integer number) {
		this.id = id;
		this.description = description;
		this.number = number;
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

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Set<Material> getMaterials() {
		return materials;
	}

//	public Set<Product> getProducts() {
//		return products;
//	}

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
		TaxClassification other = (TaxClassification) obj;
		return Objects.equals(id, other.id);
	}

}
