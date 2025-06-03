package com.smartprocessrefusao.erprefusao.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_address")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAddress;

	private String street;
	private Integer numberAddress;
	private String complement;
	private String neighborhood;
	private String zipCode;
	
	@ManyToOne
	@JoinColumn(name = "city_id")
	private City city;
	
	@OneToOne
	@JoinColumn(name = "people_id")
	private People people;
	
	public Address() {
		
	}

	public Address(Long idAddress, String street, Integer numberAddress, String complement, String neighborhood, String zipCode,
			City city, People people) {
		this.idAddress = idAddress;
		this.street = street;
		this.numberAddress = numberAddress;
		this.complement = complement;
		this.neighborhood = neighborhood;
		this.zipCode = zipCode;
		this.city = city;
		this.people = people;
	}

	public Long getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(Long idAddress) {
		this.idAddress = idAddress;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getNumberAddress() {
		return numberAddress;
	}

	public void setNumberAddress(Integer number) {
		this.numberAddress = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public People getPeople() {
		return people;
	}

	public void setPeople(People people) {
		this.people = people;
	}

}


