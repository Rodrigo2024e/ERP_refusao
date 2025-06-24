package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.dto.AddressDTO;
import com.smartprocessrefusao.erprefusao.entities.Address;
import com.smartprocessrefusao.erprefusao.entities.City;
import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.enumerados.StateBrazil;

public class AddressFactory {

	  public static City createCity() {
	        City city = new City();
	        city.setId(1L);
	        city.setNameCity("São Paulo");
	        city.setUfState(StateBrazil.SP);
	        return city;
	    }

	    public static Employee createPeople() {
	        Employee employee = new Employee();
	        employee.setId(10L);
	        return employee;
	    }

	    public static Address createAddress() {
	        Address address = new Address();
	        address.setIdAddress(100L);
	        address.setStreet("Rua das Flores");
	        address.setNumberAddress(123);
	        address.setComplement("Apto 202");
	        address.setNeighborhood("Centro");
	        address.setZipCode("12.345-678");
	        address.setCity(createCity());
	        address.setPeople(createPeople());
	        return address;
	    }

	    public static AddressDTO createAddressDTO() {
	        AddressDTO dto = new AddressDTO();
	        dto.setStreet("Rua das Flores");
	        dto.setNumberAddress(123);
	        dto.setComplement("Apto 202");
	        dto.setNeighborhood("Centro");
	        dto.setZipCode("12.345-678");
	        dto.setCityId(1L);
	        dto.setPeople_id(10L);
	        return dto;
	    }
	
}
