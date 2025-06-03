package com.smartprocessrefusao.erprefusao.entities;

import java.util.HashSet;
import java.util.Set;

import com.smartprocessrefusao.erprefusao.enumerados.StateBrazil;
import com.smartprocessrefusao.erprefusao.projections.IdProjection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_city")
public class City implements IdProjection<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nameCity;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StateBrazil ufState;
	
	@OneToMany(mappedBy = "city")
	Set<Address> address = new HashSet<>();
	
	public City() {
		
	}

	public City(Long id, String nameCity, StateBrazil ufState) {
		this.id = id;
		this.nameCity = nameCity;
		this.ufState = ufState;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameCity() {
		return nameCity;
	}

	public void setNameCity(String nameCity) {
		this.nameCity = nameCity;
	}

	public StateBrazil getUfState() {
		return ufState;
	}

	public void setUfState(StateBrazil ufState) {
		this.ufState = ufState;
	}

	public Set<Address> getAddress() {
		return address;
	}

	public void setAddress(Set<Address> address) {
		this.address = address;
	}
	
}
