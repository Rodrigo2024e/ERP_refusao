package com.smartprocessrefusao.erprefusao.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.TaxClassificationDTO;
import com.smartprocessrefusao.erprefusao.entities.TaxClassification;
import com.smartprocessrefusao.erprefusao.repositories.TaxClassificationRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TaxClassificationService {

	@Autowired
	private TaxClassificationRepository taxClassificationRepository;
	
	@Transactional(readOnly = true)
	public List<TaxClassificationDTO> findAll() {
	    List<TaxClassification> list = taxClassificationRepository.findAll();
	    return list.stream().map(TaxClassificationDTO::new).toList();
	}

	@Transactional(readOnly = true)
	public TaxClassificationDTO findById(Long id) {
		try {
		Optional<TaxClassification> obj = taxClassificationRepository.findById(id);
		TaxClassification entity = obj.orElseThrow(()-> new EntityNotFoundException("Entity not found"));
		return new TaxClassificationDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}	
		
	}

	@Transactional
	public TaxClassificationDTO insert(TaxClassificationDTO dto) {
		TaxClassification entity = new TaxClassification();
		copyDtoToEntity(dto, entity);
		entity = taxClassificationRepository.save(entity);
		return new TaxClassificationDTO(entity);
	}
	
	@Transactional
	public TaxClassificationDTO update(Long id, TaxClassificationDTO dto) {	
		try {
			TaxClassification entity = taxClassificationRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = taxClassificationRepository.save(entity);
			return new TaxClassificationDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!taxClassificationRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			taxClassificationRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	public void copyDtoToEntity(TaxClassificationDTO dto, TaxClassification entity) {
	    entity.setDescription(dto.getDescription());
	    entity.setNumber(dto.getNumber());

	}
}
