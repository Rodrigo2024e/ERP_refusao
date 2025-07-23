package com.smartprocessrefusao.erprefusao.services;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.UnitDTO;
import com.smartprocessrefusao.erprefusao.entities.Unit;
import com.smartprocessrefusao.erprefusao.repositories.UnitRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UnitService {

	@Autowired
	private UnitRepository unitRepository;
	
	@Transactional(readOnly = true)
	public List<UnitDTO> findAll() {
	    List<Unit> list = unitRepository.findAll();
	    return list.stream().map(UnitDTO::new).toList();
	}

	@Transactional(readOnly = true)
	public UnitDTO findById(Long id) {
		try {
		Optional<Unit> obj = unitRepository.findById(id);
		Unit entity = obj.orElseThrow(()-> new EntityNotFoundException("Entity not found"));
		return new UnitDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}	
		
	}

	@Transactional
	public UnitDTO insert(UnitDTO dto) {
		Unit entity = new Unit();
		copyDtoToEntity(dto, entity);
		entity = unitRepository.save(entity);
		return new UnitDTO(entity);
	}
	
	@Transactional
	public UnitDTO update(Long id, UnitDTO dto) {	
		try {
			Unit entity = unitRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = unitRepository.save(entity);
			return new UnitDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!unitRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			unitRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	public void copyDtoToEntity(UnitDTO dto, Unit entity) {
	    entity.setDescription(dto.getDescription());
	    entity.setAcronym(dto.getAcronym());

	}
}
