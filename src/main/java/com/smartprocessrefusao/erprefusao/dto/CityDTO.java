
package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.City;

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

	public String getNameCity() {
		return nameCity;
	}

	public String getUfState() {
		return ufState;
	}

	public Long getIdState() {
		return idState;
	}

	public String getNameState() {
		return nameState;
	}

	public String getCountry() {
		return country;
	}

	
}







