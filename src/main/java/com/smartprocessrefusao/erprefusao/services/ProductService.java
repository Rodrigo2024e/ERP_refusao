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
import com.smartprocessrefusao.erprefusao.entities.Unit;
import com.smartprocessrefusao.erprefusao.projections.ReportProductProjection;
import com.smartprocessrefusao.erprefusao.repositories.ProductGroupRepository;
import com.smartprocessrefusao.erprefusao.repositories.ProductRepository;
import com.smartprocessrefusao.erprefusao.repositories.TaxClassificationRepository;
import com.smartprocessrefusao.erprefusao.repositories.UnitRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository ProductRepository;
	
	@Autowired
	private UnitRepository unitRepository;
	
	@Autowired
	private TaxClassificationRepository taxRepository;
	
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
	   	    
	    Optional.ofNullable(dto.getUomId())
	    .ifPresent(id -> {
	        Unit unit = unitRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Unidade de medida não encontrada"));
	        entity.setUom(unit);
	    });
	    
	    Optional.ofNullable(dto.getTaxClassId())
	    .ifPresent(id -> {
	        TaxClassification taxClass = taxRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Classificação Fiscal não encontrada"));
	        entity.setTaxclass(taxClass);
	    });

	    Optional.ofNullable(dto.getProdGroupId())
	    .ifPresent(id -> {
	        ProductGroup prodGroup = productGroupRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Grupo de Mercadoria não encontrada"));
	        entity.setProdGroup(prodGroup);
	    });

	}

}
	

