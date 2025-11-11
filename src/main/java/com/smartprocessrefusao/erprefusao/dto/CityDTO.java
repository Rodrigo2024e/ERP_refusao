
package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.City;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CityDTO {

	private Long id;

	@Size(min = 5, max = 100, message = "O nome deve ter entre 5 a 100 caracteres")
	private String name;

	@NotNull(message = "O campo UF do Estado é obrigatório")
	private String state;
	private String nameState;
	private String country;

	public CityDTO() {

	}

	public CityDTO(Long id, String name, String state, Long idState, String nameState, String country) {
		this.id = id;
		this.name = name;
		this.state = state;
		this.nameState = nameState;
		this.country = country;
	}

	public CityDTO(City entity) {
		id = entity.getId();
		name = entity.getName();
		state = entity.getState().getUf();
		nameState = entity.getState().getNameState();
		country = entity.getState().getCountry();

	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getState() {
		return state;
	}

	public String getNameState() {
		return nameState;
	}

	public String getCountry() {
		return country;
	}

}
