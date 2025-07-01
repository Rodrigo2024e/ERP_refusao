package com.smartprocessrefusao.erprefusao.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.ProductDTO;
import com.smartprocessrefusao.erprefusao.dto.ReportProductDTO;
import com.smartprocessrefusao.erprefusao.entities.Product;
import com.smartprocessrefusao.erprefusao.entities.ProductGroup;
import com.smartprocessrefusao.erprefusao.entities.TaxClassification;
import com.smartprocessrefusao.erprefusao.entities.UnitOfMeasure;
import com.smartprocessrefusao.erprefusao.projections.ReportProductProjection;
import com.smartprocessrefusao.erprefusao.repositories.ProductGroupRepository;
import com.smartprocessrefusao.erprefusao.repositories.ProductRepository;
import com.smartprocessrefusao.erprefusao.repositories.TaxClassificationRepository;
import com.smartprocessrefusao.erprefusao.repositories.UnitOfMeasureRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository ProductRepository;
	
	@Autowired
	private UnitOfMeasureRepository unitOfMeasureRepository;
	
	@Autowired
	private TaxClassificationRepository taxClassificationRepository;
	
	@Autowired
	private ProductGroupRepository productGroupRepository;

	   @Transactional(readOnly = true) 
	    public Page<ReportProductDTO> reportProduct(String description, Long ProductId, Pageable pageable) {

	        Page<ReportProductProjection> page = ProductRepository.searchProductByNameOrId(description, ProductId, pageable);

	        return page.map(ReportProductDTO::new);
	    }
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		try {
		Optional<Product> obj = ProductRepository.findById(id);
		Product entity = obj.orElseThrow(()-> new EntityNotFoundException("Entity not found"));
		return new ProductDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}	
		
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = ProductRepository.save(entity);
		return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {	
		try {
			Product entity = ProductRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = ProductRepository.save(entity);
			return new ProductDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!ProductRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			ProductRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	public void copyDtoToEntity(ProductDTO dto, Product entity) {
	    entity.setDescription(dto.getDescription());
	    entity.setAlloy(dto.getAlloy());
	    entity.setInch(dto.getInch());
	    entity.setLength(dto.getLength());
	    
	  
	    if (dto.getUom_id() != null) {
	    UnitOfMeasure unit = unitOfMeasureRepository.findById(dto.getUom_id())
	        .orElseThrow(() -> new ResourceNotFoundException("Unidade de medida não encontrada"));
	    entity.setUom(unit);
	    }
	
			  if (dto.getTaxClass_id() != null) {
				    TaxClassification taxClass = taxClassificationRepository.findById(dto.getTaxClass_id())
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
	

