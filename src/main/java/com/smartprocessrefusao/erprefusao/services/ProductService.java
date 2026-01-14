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
import com.smartprocessrefusao.erprefusao.dto.ProductReportDTO;
import com.smartprocessrefusao.erprefusao.entities.MaterialGroup;
import com.smartprocessrefusao.erprefusao.entities.Product;
import com.smartprocessrefusao.erprefusao.entities.TaxClassification;
import com.smartprocessrefusao.erprefusao.entities.Unit;
import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloy;
import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloyFootage;
import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloyPol;
import com.smartprocessrefusao.erprefusao.projections.ProductReportProjection;
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
	public ProductDTO findByCode(Long productCode) {
		try {
			Optional<Product> obj = productRepository.findByProductCode(productCode);
			Product entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
			return new ProductDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + productCode);
		}

	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {

		// 1. Validação de Duplicidade de Código
		productRepository.findByProductCode(dto.getProductCode()).ifPresent(m -> {
			throw new IllegalArgumentException("Código de product já cadastrado!");
		});

		String newProductCode = dto.getProductCode().toString();
		String groupIdStr = String.valueOf(dto.getMatGroupId());
		String taxClassIdStr = String.valueOf(dto.getTaxClassId());

		// Validação de segurança: garante que o código não está vazio.
		if (newProductCode.isEmpty()) {
			throw new IllegalArgumentException("O código do product não pode ser vazio.");
		}

		// Pega apenas o primeiro dígito do código.
		String firstDigit = newProductCode.substring(0, 1);

		// A validação falha se o primeiro dígito NÃO for igual ao ID do grupo
		// OU se o primeiro dígito NÃO for igual ao ID da classe fiscal.
		if (!firstDigit.equals(groupIdStr) || !firstDigit.equals(taxClassIdStr)) {
			throw new IllegalArgumentException("O primeiro dígito do código do product ('" + firstDigit + "') "
					+ "deve ser idêntico ao ID do grupo de product e classificação fiscal"

			);
		}

		// 3. Persistência (se as validações passarem)
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = productRepository.save(entity);

		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long productCode, ProductDTO dto) {

		// 1. Busca a entidade existente pelo código atual.
		Product entity = productRepository.findByProductCode(productCode)
				.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado com o código: " + productCode));

		// --- INÍCIO DA VALIDAÇÃO CORRETA ---

		String newProductCode = dto.getProductCode().toString();
		String groupIdStr = String.valueOf(dto.getMatGroupId());
		String taxClassIdStr = String.valueOf(dto.getTaxClassId());

		// Validação de segurança: garante que o código não está vazio.
		if (newProductCode.isEmpty()) {
			throw new IllegalArgumentException("O código do product não pode ser vazio.");
		}

		// Pega apenas o primeiro dígito do código.
		String firstDigit = newProductCode.substring(0, 1);

		// A validação falha se o primeiro dígito NÃO for igual ao ID do grupo
		// OU se o primeiro dígito NÃO for igual ao ID da classe fiscal.
		if (!firstDigit.equals(groupIdStr) || !firstDigit.equals(taxClassIdStr)) {
			throw new IllegalArgumentException("O primeiro dígito do código do product ('" + firstDigit + "') "
					+ "deve ser idêntico ao ID do grupo de product e classificação fiscal");
		}

		// 4. Atualização e Persistência
		copyDtoToEntity(dto, entity);

		entity = productRepository.save(entity);

		return new ProductDTO(entity);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long productCode) {
		if (!productRepository.existsByProductCode(productCode)) {
			throw new ResourceNotFoundException("Code not found " + productCode);
		}
		try {
			productRepository.deleteByProductCode(productCode);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	public void copyDtoToEntity(ProductDTO dto, Product entity) {

		entity.setProductCode(dto.getProductCode());
		entity.setDescription(dto.getDescription().toUpperCase());

		try {
			AluminumAlloy alloy = AluminumAlloy.fromDescription(dto.getAlloy());
			entity.setAlloy(alloy);
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de liga inválida: " + dto.getAlloy());
		}

		try {
			AluminumAlloyPol alloyPol = AluminumAlloyPol.valueOf(dto.getAlloyPol());
			entity.setAlloyPol(alloyPol);
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de polegada inválida: " + dto.getAlloyPol());
		}
		
		try {
			AluminumAlloyFootage alloyFootage = AluminumAlloyFootage.valueOf(dto.getAlloyFootage());
			entity.setAlloyFootage(alloyFootage);
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de metragem inválida: " + dto.getAlloyFootage());
		}

		Optional.ofNullable(dto.getUnitId()).ifPresent(id -> {
			Unit unit = unitRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Unidade de medida não encontrada"));
			entity.setUnit(unit);
		});

		Optional.ofNullable(dto.getTaxClassId()).ifPresent(id -> {
			TaxClassification taxClass = taxRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Classificação Fiscal não encontrada"));
			entity.setTaxClass(taxClass);
		});

		Optional.ofNullable(dto.getMatGroupId()).ifPresent(id -> {
			MaterialGroup matGroup = materialGroupRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Grupo de Mercadoria não encontrada"));
			entity.setMaterialGroup(matGroup);
		});

	}
	
	@Transactional(readOnly = true)
	public Page<ProductReportDTO> reportProduct(
			String alloy, 
			Long productCode, 
			String description, 
			Long groupId, 
			Pageable pageable) {
		Page<ProductReportProjection> page = productRepository.searchProductByNameOrGroup(
				alloy, 
				productCode, 
				description, 
				groupId,
				pageable);
		return page.map(ProductReportDTO::new);
	}

}
