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
import com.smartprocessrefusao.erprefusao.entities.MaterialGroup;
import com.smartprocessrefusao.erprefusao.entities.Product;
import com.smartprocessrefusao.erprefusao.entities.TaxClassification;
import com.smartprocessrefusao.erprefusao.entities.Unit;
import com.smartprocessrefusao.erprefusao.enumerados.TypeMaterial;
import com.smartprocessrefusao.erprefusao.projections.ReportProductProjection;
import com.smartprocessrefusao.erprefusao.repositories.MaterialGroupRepository;
import com.smartprocessrefusao.erprefusao.repositories.ProductRepository;
import com.smartprocessrefusao.erprefusao.repositories.TaxClassificationRepository;
import com.smartprocessrefusao.erprefusao.repositories.UnitRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UnitRepository unitRepository;

	@Autowired
	private TaxClassificationRepository taxRepository;

	@Autowired
	private MaterialGroupRepository materialGroupRepository;

	@Transactional(readOnly = true)
	public Page<ReportProductDTO> reportProduct(Integer alloy, Long ProductId, Pageable pageable) {

		Page<ReportProductProjection> page = productRepository.searchProductByNameOrId(alloy, ProductId,
				pageable);

		return page.map(ReportProductDTO::new);
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		try {
			Optional<Product> obj = productRepository.findById(id);
			Product entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
			return new ProductDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}

	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = productRepository.save(entity);
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = productRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = productRepository.save(entity);
			return new ProductDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!productRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			productRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	public void copyDtoToEntity(ProductDTO dto, Product entity) {

		if (dto.getTypeMaterial() != null) {
		    try {
		        TypeMaterial typeMaterial = TypeMaterial.fromDescription(dto.getTypeMaterial());
		        entity.setTypeMaterial(typeMaterial);
		    } catch (IllegalArgumentException e) {
		        throw new ResourceNotFoundException("Tipo de material inválido: " + dto.getTypeMaterial());
		    }
		}

		entity.setDescription(dto.getDescription().toUpperCase());
		entity.setAlloy(dto.getAlloy());
		entity.setBilletDiameter(dto.getBilletDiameter());
		entity.setBilletLength(dto.getBilletLength());

		Optional.ofNullable(dto.getUnitId()).ifPresent(id -> {
			Unit unit = unitRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Unidade de medida não encontrada"));
			entity.setUomMaterial(unit);
		});

		Optional.ofNullable(dto.getTaxClassId()).ifPresent(id -> {
			TaxClassification taxClass = taxRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Classificação Fiscal não encontrada"));
			entity.setTaxClassMaterial(taxClass);
		});

		Optional.ofNullable(dto.getMatGroupId()).ifPresent(id -> {
			MaterialGroup matGroup = materialGroupRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Grupo de Mercadoria não encontrada"));
			entity.setMaterialGroup(matGroup);
		});

	}

}
