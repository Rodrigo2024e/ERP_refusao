package com.smartprocessrefusao.erprefusao.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.MaterialGroupDTO;
import com.smartprocessrefusao.erprefusao.entities.MaterialGroup;
import com.smartprocessrefusao.erprefusao.repositories.MaterialGroupRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MaterialGroupService {

	@Autowired
	private MaterialGroupRepository productGroupRepository;
	
	@Transactional(readOnly = true)
	public List<MaterialGroupDTO> findAll() {
	    List<MaterialGroup> list = productGroupRepository.findAll();
	    return list.stream().map(MaterialGroupDTO::new).toList();
	}

	@Transactional(readOnly = true)
	public MaterialGroupDTO findById(Long id) {
		try {
		Optional<MaterialGroup> obj = productGroupRepository.findById(id);
		MaterialGroup entity = obj.orElseThrow(()-> new EntityNotFoundException("Entity not found"));
		return new MaterialGroupDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}	
		
	}

	@Transactional
	public MaterialGroupDTO insert(MaterialGroupDTO dto) {
		MaterialGroup entity = new MaterialGroup();
		copyDtoToEntity(dto, entity);
		entity = productGroupRepository.save(entity);
		return new MaterialGroupDTO(entity);
	}
	
	@Transactional
	public MaterialGroupDTO update(Long id, MaterialGroupDTO dto) {	
		try {
			MaterialGroup entity = productGroupRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = productGroupRepository.save(entity);
			return new MaterialGroupDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!productGroupRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			productGroupRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	public void copyDtoToEntity(MaterialGroupDTO dto, MaterialGroup entity) {
	    entity.setDescription(dto.getDescription().toUpperCase());

	}
}
