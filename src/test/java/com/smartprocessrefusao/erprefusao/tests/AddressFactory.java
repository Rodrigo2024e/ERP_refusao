package com.smartprocessrefusao.erprefusao.tests;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.smartprocessrefusao.erprefusao.dto.AddressDTO;
import com.smartprocessrefusao.erprefusao.entities.Address;

	public class AddressFactory {

	    public static Address createAddress() {
	        Address address = new Address();
	        address.setIdAddress(1L);
	        address.setStreet("Rua A");
	        address.setNumberAddress(123);
	        address.setComplement("Casa");
	        address.setNeighborhood("Centro");
	        address.setZipCode("12345-678");
	        address.setCity(CityFactory.createCity());
	        address.setPeople(PeopleFactory.createPeople());
	        return address;
	    }
	    
	    public static AddressDTO createAddressDTO() {
	        return new AddressDTO(
	            1L, // idAddress
	            "Rua Teste", 
	            123, 
	            "Complemento", 
	            "Bairro", 
	            "12345-678",
	            1L, // cityId
	            "São Paulo",
	            "StateBrazil.SP",
	            "São Paulo",
	            "Brasil",
	            1L //peopleId
	        );
	    }
	    
	    public static Pageable createPageable() {
	        return PageRequest.of(0, 10);
	    }

	}

