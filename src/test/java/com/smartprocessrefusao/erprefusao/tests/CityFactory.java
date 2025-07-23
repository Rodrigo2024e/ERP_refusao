package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.dto.CityDTO;
import com.smartprocessrefusao.erprefusao.entities.City;
import com.smartprocessrefusao.erprefusao.enumerados.StateBrazil;

public class CityFactory {

	public static City createCity() {
		City city = new City();
		city.setId(1L);
		city.setNameCity("São Paulo");
		city.setUfState(StateBrazil.SP);
		return city;
	}

	public static City createUpdatedCity() {
		City city = new City();
		city.setId(1L);
		city.setNameCity("Campinas");
		city.setUfState(StateBrazil.SP);
		return city;
	}

	public static CityDTO createCityDTO() {
		return new CityDTO(1L, "São Paulo", "SP", 26L, "São Paulo", "Brasil");
	}

	public static CityDTO createUpdatedCityDTO() {
		return new CityDTO(1L, "São Paulo", "SP", 26L, "São Paulo", "Brasil");
	}

	public static CityDTO createInvalidUfDTO() {
		return new CityDTO(999L, "São Paulo", "XX", 26L, "São Paulo", "Brasil");
	}
}
