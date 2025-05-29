
package com.smartprocessrefusao.erprefusao.cadastros.dto;

import com.smartprocessrefusao.erprefusao.cadastros.entities.City;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CityDTO {

	private Long id;
	
	@Size(min = 5, max = 100, message = "O nome deve ter entre 5 a 100 caracteres")
	private String nameCity;
	
	@NotNull(message = "O campo UF do Estado é obrigatório")
	private String ufState;
	private Long idState;
	private String nameState;
	private String country;
	
	public CityDTO() {
		
	}

	public CityDTO(Long id, String nameCity, String ufState, Long idState, 
			String nameState, String country) {
		this.id = id;
		this.nameCity = nameCity;
		this.ufState = ufState;
		this.idState = idState;
		this.nameState = nameState;
		this.country = country;
	}

	public CityDTO(City entity) {
		id = entity.getId();
		nameCity = entity.getNameCity();
		ufState = entity.getUfState().getUf();
		idState = entity.getUfState().getId();
		nameState = entity.getUfState().getNameState();
		country = entity.getUfState().getCountry();
	
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

	public String getUfState() {
		return ufState;
	}

	public void setUfState(String ufState) {
		this.ufState = ufState;
	}

	public Long getIdState() {
		return idState;
	}

	public void setIdState(Long idState) {
		this.idState = idState;
	}

	public String getNameState() {
		return nameState;
	}

	public void setNameState(String nameState) {
		this.nameState = nameState;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	
}







