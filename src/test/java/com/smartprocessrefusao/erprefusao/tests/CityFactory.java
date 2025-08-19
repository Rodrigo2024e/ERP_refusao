package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.dto.CityDTO;
import com.smartprocessrefusao.erprefusao.entities.City;
import com.smartprocessrefusao.erprefusao.enumerados.StateBrazil;

public class CityFactory {

	public static City createCity() {
		City city = new City();
		city.setId(1L);
		city.setNameCity("SÃO PAULO");
		city.setUfState(StateBrazil.SP);
		return city;
	}

	public static City createUpdatedCity() {
		City city = new City();
		city.setId(1L);
		city.setNameCity("CAMPINAS");
		city.setUfState(StateBrazil.SP);
		return city;
	}

	public static CityDTO createCityDTO() {
		return new CityDTO(1L, "SÃO PAULO", "SP", 26L, "SÃO PAULO", "BRASIL");
	}

	public static CityDTO createUpdatedCityDTO() {
		return new CityDTO(1L, "SÃO PAULO", "SP", 26L, "SÃO PAULO", "BRASIL");
	}

	public static CityDTO createInvalidUfDTO() {
		return new CityDTO(999L, "SÃO PAULO", "XX", 26L, "SÃO PAULO", "BRASIL");
	}
}
