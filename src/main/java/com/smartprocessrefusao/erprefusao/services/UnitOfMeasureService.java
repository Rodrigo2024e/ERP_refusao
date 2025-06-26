package com.smartprocessrefusao.erprefusao.services;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.UnitOfMeasureDTO;
import com.smartprocessrefusao.erprefusao.entities.UnitOfMeasure;
import com.smartprocessrefusao.erprefusao.repositories.UnitOfMeasureRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UnitOfMeasureService {

	@Autowired
	private UnitOfMeasureRepository UnitOfMeasureRepository;
	
	@Transactional(readOnly = true)
	public List<UnitOfMeasureDTO> findAll() {
	    List<UnitOfMeasure> list = UnitOfMeasureRepository.findAll();
	    return list.stream().map(UnitOfMeasureDTO::new).toList();
	}

	@Transactional(readOnly = true)
	public UnitOfMeasureDTO findById(Long id) {
		try {
		Optional<UnitOfMeasure> obj = UnitOfMeasureRepository.findById(id);
		UnitOfMeasure entity = obj.orElseThrow(()-> new EntityNotFoundException("Entity not found"));
		return new UnitOfMeasureDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}	
		
	}

	@Transactional
	public UnitOfMeasureDTO insert(UnitOfMeasureDTO dto) {
		UnitOfMeasure entity = new UnitOfMeasure();
		copyDtoToEntity(dto, entity);
		entity = UnitOfMeasureRepository.save(entity);
		return new UnitOfMeasureDTO(entity);
	}
	
	@Transactional
	public UnitOfMeasureDTO update(Long id, UnitOfMeasureDTO dto) {	
		try {
			UnitOfMeasure entity = UnitOfMeasureRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = UnitOfMeasureRepository.save(entity);
			return new UnitOfMeasureDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!UnitOfMeasureRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			UnitOfMeasureRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	public void copyDtoToEntity(UnitOfMeasureDTO dto, UnitOfMeasure entity) {
	    entity.setDescription(dto.getDescription());
	    entity.setAcronym(dto.getAcronym());

	}
}
