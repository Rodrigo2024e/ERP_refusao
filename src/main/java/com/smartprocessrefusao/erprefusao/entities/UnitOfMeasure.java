package com.smartprocessrefusao.erprefusao.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_uom")
public class UnitOfMeasure {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private String acronym;
	
	@OneToMany(mappedBy = "uom")
	private List<Material> materials = new ArrayList<>();
	
	public UnitOfMeasure (){
		
	}

	public UnitOfMeasure(Long id, String description, String acronym) {
		super();
		this.id = id;
		this.description = description;
		this.acronym = acronym;
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

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public List<Material> getMaterials() {
		return materials;
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
		UnitOfMeasure other = (UnitOfMeasure) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
