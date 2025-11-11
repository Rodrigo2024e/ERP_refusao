package com.smartprocessrefusao.erprefusao.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.AddressDTO;
import com.smartprocessrefusao.erprefusao.entities.Address;
import com.smartprocessrefusao.erprefusao.entities.City;
import com.smartprocessrefusao.erprefusao.entities.People;
import com.smartprocessrefusao.erprefusao.projections.AddressProjection;
import com.smartprocessrefusao.erprefusao.repositories.AddressRepository;
import com.smartprocessrefusao.erprefusao.repositories.CityRepository;
import com.smartprocessrefusao.erprefusao.repositories.PeopleRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;


@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CityRepository cityRepository; 
    
    @Autowired
    private PeopleRepository peopleRepository; 
    

    @Transactional(readOnly = true)
    public Page<AddressDTO> searchAddresses(String city, Long addressId, Pageable pageable) {
        Page<AddressProjection> result = addressRepository.searchAddressesByCityNameOrId(city, addressId, pageable);
        return result.map(AddressDTO::new);
    }

	@Transactional(readOnly = true)
	public AddressDTO findById(Long id) {
		Optional<Address> obj = addressRepository.findById(id);
		Address entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
		return new AddressDTO(entity);
	}
    
    @Transactional
    public AddressDTO insert(AddressDTO dto) {
        Address entity = new Address();
        copyDtoToEntity(dto, entity); 
        entity = addressRepository.save(entity); 
        return new AddressDTO(entity); 
    }
 
    @Transactional
	public AddressDTO update(Long id, AddressDTO dto) {
		try {
			Address entity = addressRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = addressRepository.save(entity);
			return new AddressDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
		if (!addressRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			addressRepository.deleteById(id);    		
		}
	    	catch (DataIntegrityViolationException e) {
	        	throw new DatabaseException("Integrity violation");
	   	}
	} 
    
    private void copyDtoToEntity(AddressDTO dto, Address entity) {
        entity.setStreet(dto.getStreet().toUpperCase());
        entity.setNumber(dto.getNumber());
        entity.setComplement(dto.getComplement().toUpperCase());
        entity.setNeighborhood(dto.getNeighborhood().toUpperCase());
        entity.setZipCode(dto.getZipCode());
        
        Optional.ofNullable(dto.getCityId())
	    .ifPresent(id -> {
	    	City city = cityRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrada"));
	    	entity.setCity(city);
	    });
	    
        Optional.ofNullable(dto.getPeopleId())
	    .ifPresent(id -> {
	    	 People people = peopleRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));
	    	 entity.setPeople(people);
	    });
	     
 
    	}

  }
