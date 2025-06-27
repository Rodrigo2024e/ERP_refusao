package com.smartprocessrefusao.erprefusao.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.MaterialDTO;
import com.smartprocessrefusao.erprefusao.dto.ReportMaterialDTO;
import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.entities.ProductGroup;
import com.smartprocessrefusao.erprefusao.entities.TaxClassification;
import com.smartprocessrefusao.erprefusao.entities.UnitOfMeasure;
import com.smartprocessrefusao.erprefusao.projections.ReportMaterialProjection;
import com.smartprocessrefusao.erprefusao.repositories.MaterialRepository;
import com.smartprocessrefusao.erprefusao.repositories.ProductGroupRepository;
import com.smartprocessrefusao.erprefusao.repositories.TaxClassificationRepository;
import com.smartprocessrefusao.erprefusao.repositories.UnitOfMeasureRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MaterialService {

	@Autowired
	private MaterialRepository materialRepository;
	
	@Autowired
	private UnitOfMeasureRepository unitOfMeasureRepository;
	
	@Autowired
	private TaxClassificationRepository taxClassificationRepository;
	
	@Autowired
	private ProductGroupRepository productGroupRepository;

	   @Transactional(readOnly = true) 
	    public Page<ReportMaterialDTO> reportMaterial(String description, Long materialId, Pageable pageable) {

	        Page<ReportMaterialProjection> page = materialRepository.searchMaterialByNameOrId(description, materialId, pageable);

	        return page.map(ReportMaterialDTO::new);
	    }
	
	@Transactional(readOnly = true)
	public MaterialDTO findById(Long id) {
		try {
		Optional<Material> obj = materialRepository.findById(id);
		Material entity = obj.orElseThrow(()-> new EntityNotFoundException("Entity not found"));
		return new MaterialDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}	
		
	}

	@Transactional
	public MaterialDTO insert(MaterialDTO dto) {
		Material entity = new Material();
		copyDtoToEntity(dto, entity);
		entity = materialRepository.save(entity);
		return new MaterialDTO(entity);
	}
	
	@Transactional
	public MaterialDTO update(Long id, MaterialDTO dto) {	
		try {
			Material entity = materialRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = materialRepository.save(entity);
			return new MaterialDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!materialRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			materialRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	public void copyDtoToEntity(MaterialDTO dto, Material entity) {
	    entity.setDescription(dto.getDescription());
	  
	    if (dto.getUom_id() != null) {
	    UnitOfMeasure unit = unitOfMeasureRepository.findById(dto.getUom_id())
	        .orElseThrow(() -> new ResourceNotFoundException("Unidade de medida não encontrada"));
	    entity.setUom(unit);
	    }
	
			  if (dto.getTaxclass_id() != null) {
				    TaxClassification taxClass = taxClassificationRepository.findById(dto.getTaxclass_id())
				        .orElseThrow(() -> new ResourceNotFoundException("Classificação Fiscal não encontrada"));
				    entity.setTaxclass(taxClass);
			  }
	  
					  if (dto.getProdGroup_id() != null) {
						    ProductGroup prodGroup = productGroupRepository.findById(dto.getProdGroup_id())
						        .orElseThrow(() -> new ResourceNotFoundException("Grupo de Mercadoria não encontrada"));
						    entity.setProdGroup(prodGroup);
					  }
					  
	  
	}
	
}
	

