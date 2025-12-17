package com.smartprocessrefusao.erprefusao.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.smartprocessrefusao.erprefusao.audit.Auditable;
import com.smartprocessrefusao.erprefusao.projections.IdProjection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_machine")
public class Machine extends Auditable<String> implements IdProjection<Long> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private String brand;
	private String model;
	private Integer manufacturingYear;
	private Double capacity;

	@OneToMany(mappedBy = "machine")
	private List<Melting> meltings = new ArrayList<>();

	public Machine() {
	}

	public Machine(Long id, String description, String brand, String model, Integer manufacturingYear,
			Double capacity) {
		this.id = id;
		this.description = description;
		this.brand = brand;
		this.model = model;
		this.manufacturingYear = manufacturingYear;
		this.capacity = capacity;
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

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getManufacturingYear() {
		return manufacturingYear;
	}

	public void setManufacturingYear(Integer manufacturingYear) {
		this.manufacturingYear = manufacturingYear;
	}

	public Double getCapacity() {
		return capacity;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}

	public List<Melting> getMeltings() {
		return meltings;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Machine other = (Machine) obj;
		return Objects.equals(id, other.id);
	}

}
