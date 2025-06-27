package com.smartprocessrefusao.erprefusao.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.ProductGroupDTO;
import com.smartprocessrefusao.erprefusao.entities.ProductGroup;
import com.smartprocessrefusao.erprefusao.repositories.ProductGroupRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductGroupService {

	@Autowired
	private ProductGroupRepository productGroupRepository;
	
	@Transactional(readOnly = true)
	public List<ProductGroupDTO> findAll() {
	    List<ProductGroup> list = productGroupRepository.findAll();
	    return list.stream().map(ProductGroupDTO::new).toList();
	}

	@Transactional(readOnly = true)
	public ProductGroupDTO findById(Long id) {
		try {
		Optional<ProductGroup> obj = productGroupRepository.findById(id);
		ProductGroup entity = obj.orElseThrow(()-> new EntityNotFoundException("Entity not found"));
		return new ProductGroupDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}	
		
	}

	@Transactional
	public ProductGroupDTO insert(ProductGroupDTO dto) {
		ProductGroup entity = new ProductGroup();
		copyDtoToEntity(dto, entity);
		entity = productGroupRepository.save(entity);
		return new ProductGroupDTO(entity);
	}
	
	@Transactional
	public ProductGroupDTO update(Long id, ProductGroupDTO dto) {	
		try {
			ProductGroup entity = productGroupRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = productGroupRepository.save(entity);
			return new ProductGroupDTO(entity);
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
	
	public void copyDtoToEntity(ProductGroupDTO dto, ProductGroup entity) {
	    entity.setDescription(dto.getDescription());

	}
}
