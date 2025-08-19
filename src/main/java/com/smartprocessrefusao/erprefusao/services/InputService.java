package com.smartprocessrefusao.erprefusao.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.InputDTO;
import com.smartprocessrefusao.erprefusao.dto.ReportInputDTO;
import com.smartprocessrefusao.erprefusao.entities.Input;
import com.smartprocessrefusao.erprefusao.entities.MaterialGroup;
import com.smartprocessrefusao.erprefusao.entities.TaxClassification;
import com.smartprocessrefusao.erprefusao.entities.Unit;
import com.smartprocessrefusao.erprefusao.enumerados.TypeMaterial;
import com.smartprocessrefusao.erprefusao.projections.ReportInputProjection;
import com.smartprocessrefusao.erprefusao.repositories.InputRepository;
import com.smartprocessrefusao.erprefusao.repositories.MaterialGroupRepository;
import com.smartprocessrefusao.erprefusao.repositories.TaxClassificationRepository;
import com.smartprocessrefusao.erprefusao.repositories.UnitRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class InputService {

	@Autowired
	private InputRepository inputRepository;
	
	@Autowired
	private UnitRepository unitRepository;
	
	@Autowired
	private TaxClassificationRepository taxRepository;
	
	@Autowired
	private MaterialGroupRepository materialGroupRepository;

	   @Transactional(readOnly = true) 
	    public Page<ReportInputDTO> reportInput(String description, Long groupId, Pageable pageable) {
	        Page<ReportInputProjection> page = inputRepository.searchMaterialByNameOrGroup(description, groupId, pageable);
	        return page.map(ReportInputDTO::new);
	    }
	
	@Transactional(readOnly = true)
	public InputDTO findById(Long id) {
		try {
		Optional<Input> obj = inputRepository.findById(id);
		Input entity = obj.orElseThrow(()-> new EntityNotFoundException("Entity not found"));
		return new InputDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}	
		
	}

	@Transactional
	public InputDTO insert(InputDTO dto) {
		Input entity = new Input();
		copyDtoToEntity(dto, entity);
		entity = inputRepository.save(entity);
		return new InputDTO(entity);
	}
	
	@Transactional
	public InputDTO update(Long id, InputDTO dto) {	
		try {
			Input entity = inputRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = inputRepository.save(entity);
			return new InputDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!inputRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			inputRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	public void copyDtoToEntity(InputDTO dto, Input entity) {
		
		try {
			if (dto.getTypeMaterial() != null) {
				TypeMaterial typeMaterial = TypeMaterial.valueOf(dto.getTypeMaterial());
				entity.setTypeMaterial(typeMaterial);
			}
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de material inválido: " + dto.getTypeMaterial());
		}
		
	    entity.setDescription(dto.getDescription().toUpperCase());
	  
	    Optional.ofNullable(dto.getUomId())
	    .ifPresent(id -> {
	        Unit unit = unitRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Unidade de medida não encontrada"));
	        entity.setUomMaterial(unit);
	    });
	    
	    Optional.ofNullable(dto.getTaxClassId())
	    .ifPresent(id -> {
	        TaxClassification taxClass = taxRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Classificação Fiscal não encontrada"));
	        entity.setTaxClassMaterial(taxClass);
	    });

	    Optional.ofNullable(dto.getMatGroupId())
	    .ifPresent(id -> {
	        MaterialGroup matGroup = materialGroupRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Grupo de Mercadoria não encontrada"));
	        entity.setMaterialGroup(matGroup);
	    });
					  
	  
	}
	
}
	

