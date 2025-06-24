package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.dto.CityDTO;
import com.smartprocessrefusao.erprefusao.entities.City;
import com.smartprocessrefusao.erprefusao.enumerados.StateBrazil;

	public class CityFactory {

	    public static City createCity(Long id, String nameCity, StateBrazil ufState) {
	        return new City(id, nameCity, ufState);
	    }

	    // Overload for when ID is not yet known (for insertion)
	    public static City createCity(String nameCity, StateBrazil ufState) {
	        return new City(null, nameCity, ufState);
	    }

	    public static CityDTO createCityDTO(Long id, String nameCity, String ufState) {
	        CityDTO dto = new CityDTO();
	        dto.setId(id);
	        dto.setNameCity(nameCity);
	        dto.setUfState(ufState);
	        return dto;
	    }

	    // Overload for when ID is not yet known (for insertion)
	    public static CityDTO createCityDTO(String nameCity, String ufState) {
	        CityDTO dto = new CityDTO();
	        dto.setNameCity(nameCity);
	        dto.setUfState(ufState);
	        return dto;
	    }

	    // Constructor in CityDTO to create from Entity
	    public static CityDTO createCityDTO(City entity) {
	        CityDTO dto = new CityDTO();
	        dto.setId(entity.getId());
	        dto.setNameCity(entity.getNameCity());
	        dto.setUfState(entity.getUfState() != null ? entity.getUfState().name() : null);
	        return dto;
	    }
	
}
