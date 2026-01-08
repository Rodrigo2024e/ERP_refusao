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
import com.smartprocessrefusao.erprefusao.dto.MateriaReportlDTO;
import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.entities.MaterialGroup;
import com.smartprocessrefusao.erprefusao.entities.TaxClassification;
import com.smartprocessrefusao.erprefusao.entities.Unit;
import com.smartprocessrefusao.erprefusao.enumerados.TypeMaterial;
import com.smartprocessrefusao.erprefusao.projections.MaterialReportProjection;
import com.smartprocessrefusao.erprefusao.repositories.MaterialGroupRepository;
import com.smartprocessrefusao.erprefusao.repositories.MaterialRepository;
import com.smartprocessrefusao.erprefusao.repositories.TaxClassificationRepository;
import com.smartprocessrefusao.erprefusao.repositories.UnitRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MaterialService {

	@Autowired
	private MaterialRepository materialRepository;

	@Autowired
	private UnitRepository unitRepository;

	@Autowired
	private TaxClassificationRepository taxRepository;

	@Autowired
	private MaterialGroupRepository materialGroupRepository;

	@Transactional(readOnly = true)
	public Page<MateriaReportlDTO> reportMaterial(String type, Long code, String description, Long groupId, Pageable pageable) {
		Page<MaterialReportProjection> page = materialRepository.searchMaterialByNameOrGroup(type, code, description, groupId,
				pageable);
		return page.map(MateriaReportlDTO::new);
	}

	@Transactional(readOnly = true)
	public MaterialDTO findByCode(Long code) {
		try {
			Optional<Material> obj = materialRepository.findByMaterialCode(code);
			Material entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
			return new MaterialDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + code);
		}

	}

	@Transactional
	public MaterialDTO insert(MaterialDTO dto) {

		// 1. Validação de Duplicidade de Código
		materialRepository.findByMaterialCode(dto.getMaterialCode()).ifPresent(m -> {
			throw new IllegalArgumentException("Código de material já cadastrado!");
		});

		String newMaterialCode = dto.getMaterialCode().toString();
		String groupIdStr = String.valueOf(dto.getMatGroupId());
		String taxClassIdStr = String.valueOf(dto.getTaxClassId());

		// Validação de segurança: garante que o código não está vazio.
		if (newMaterialCode.isEmpty()) {
			throw new IllegalArgumentException("O código do material não pode ser vazio.");
		}

		// Pega apenas o primeiro dígito do código.
		String firstDigit = newMaterialCode.substring(0, 1);

		// A validação falha se o primeiro dígito NÃO for igual ao ID do grupo
		// OU se o primeiro dígito NÃO for igual ao ID da classe fiscal.
		if (!firstDigit.equals(groupIdStr) || !firstDigit.equals(taxClassIdStr)) {
			throw new IllegalArgumentException("O primeiro dígito do código do material ('" + firstDigit + "') "
					+ "deve ser idêntico ao ID do grupo de material e classificação fiscal"

			);
		}

		// 3. Persistência (se as validações passarem)
		Material entity = new Material();
		copyDtoToEntity(dto, entity);
		entity = materialRepository.save(entity);

		return new MaterialDTO(entity);
	}

	@Transactional
	public MaterialDTO update(Long materialCode, MaterialDTO dto) {

		// 1. Busca a entidade existente pelo código atual.
		Material entity = materialRepository.findByMaterialCode(materialCode)
				.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado com o código: " + materialCode));

		// --- INÍCIO DA VALIDAÇÃO CORRETA ---

		String newMaterialCode = dto.getMaterialCode().toString();
		String groupIdStr = String.valueOf(dto.getMatGroupId());
		String taxClassIdStr = String.valueOf(dto.getTaxClassId());

		// Validação de segurança: garante que o código não está vazio.
		if (newMaterialCode.isEmpty()) {
			throw new IllegalArgumentException("O código do material não pode ser vazio.");
		}

		// Pega apenas o primeiro dígito do código.
		String firstDigit = newMaterialCode.substring(0, 1);

		// A validação falha se o primeiro dígito NÃO for igual ao ID do grupo
		// OU se o primeiro dígito NÃO for igual ao ID da classe fiscal.
		if (!firstDigit.equals(groupIdStr) || !firstDigit.equals(taxClassIdStr)) {
			throw new IllegalArgumentException("O primeiro dígito do código do material ('" + firstDigit + "') "
					+ "deve ser idêntico ao ID do grupo de material e classificação fiscal");
		}

		// 4. Atualização e Persistência
		copyDtoToEntity(dto, entity);

		entity = materialRepository.save(entity);

		return new MaterialDTO(entity);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long materialCode) {
		if (!materialRepository.existsByMaterialCode(materialCode)) {
			throw new ResourceNotFoundException("Code not found " + materialCode);
		}
		try {
			materialRepository.deleteByMaterialCode(materialCode);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	public void copyDtoToEntity(MaterialDTO dto, Material entity) {

		entity.setMaterialCode(dto.getMaterialCode());
		entity.setDescription(dto.getDescription().toUpperCase());

		try {

			TypeMaterial type = TypeMaterial.fromDescription(dto.getType());
			entity.setType(type);

		} catch (IllegalArgumentException e) {

			throw new ResourceNotFoundException("Tipo de material inválido: " + dto.getType());
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

}
