package com.smartprocessrefusao.erprefusao.cadastros.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.cadastros.dto.CityDTO;
import com.smartprocessrefusao.erprefusao.cadastros.entities.City;
import com.smartprocessrefusao.erprefusao.cadastros.repositories.CityRepository;
import com.smartprocessrefusao.erprefusao.cadastros.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.cadastros.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.enumerados.StateBrazil;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CityService {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Transactional(readOnly = true)
	public List<CityDTO> findAll() {
	    List<City> list = cityRepository.findAllByOrderByNameCityAsc();
	    return list.stream().map(CityDTO::new).toList();
	}
	
	@Transactional(readOnly = true)
	public CityDTO findById(Long id) {
		try {
		Optional<City> obj = cityRepository.findById(id);
		City entity = obj.orElseThrow(()-> new EntityNotFoundException("Entity not found"));
		return new CityDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}	
		
	}
	
	@Transactional
	public CityDTO insert(CityDTO dto) {
		City entity = new City();
		copyDtoToEntity(dto, entity);
		entity = cityRepository.save(entity);
		return new CityDTO(entity);
	}
	
	@Transactional
	public CityDTO update(Long id, CityDTO dto) {
		try {
			City entity = cityRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = cityRepository.save(entity);
			return new CityDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!cityRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			cityRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	public void copyDtoToEntity(CityDTO dto, City entity) {
		entity.setNameCity(dto.getNameCity());

		        if (dto.getUfState() != null && !dto.getUfState().isBlank()) {
		            try {
		                StateBrazil state = StateBrazil.fromUf(dto.getUfState());
		                entity.setUfState(state);
		            } catch (IllegalArgumentException e) {
		                throw new ResourceNotFoundException("UF inválida: " + dto.getUfState());
		            }
		        } else {
		            throw new ResourceNotFoundException("UF não pode ser vazia");
		        }
		    }
	}

