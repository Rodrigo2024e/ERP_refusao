package com.smartprocessrefusao.erprefusao.services;

import java.math.RoundingMode;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.ReportSupplierReceiptDTO;
import com.smartprocessrefusao.erprefusao.dto.SupplierReceiptDTO;
import com.smartprocessrefusao.erprefusao.entities.Input;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.SupplierReceipt;
import com.smartprocessrefusao.erprefusao.enumerados.TypeCosts;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionReceipt;
import com.smartprocessrefusao.erprefusao.projections.ReportSupplierReceiptProjection;
import com.smartprocessrefusao.erprefusao.repositories.InputRepository;
import com.smartprocessrefusao.erprefusao.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.repositories.SupplierReceiptRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SupplierReceiptService {

	@Autowired
	private SupplierReceiptRepository supplierReceiptRepository;

	@Autowired
	private InputRepository inputRepository;

	@Autowired
	private PartnerRepository partnerRepository;

	@Transactional(readOnly = true)
	public Page<ReportSupplierReceiptDTO> reportSupplierReceipt(Long inputId, Pageable pageable) {

		Page<ReportSupplierReceiptProjection> page = supplierReceiptRepository.searchSupplierReceiptByinputId(inputId,
				pageable);

		return page.map(ReportSupplierReceiptDTO::new);
	}

	@Transactional(readOnly = true)
	public SupplierReceiptDTO findById(Long id) {
		try {
			Optional<SupplierReceipt> obj = supplierReceiptRepository.findById(id);
			SupplierReceipt entity = obj.orElseThrow(() -> new EntityNotFoundException("Receipt not found"));
			return new SupplierReceiptDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}

	}

	@Transactional
	public SupplierReceiptDTO insert(SupplierReceiptDTO dto) {
		SupplierReceipt entity = new SupplierReceipt();
		copyDtoToEntity(dto, entity);
		entity = supplierReceiptRepository.save(entity);
		return new SupplierReceiptDTO(entity);
	}

	@Transactional
	public SupplierReceiptDTO update(Long id, SupplierReceiptDTO dto) {
		try {
			SupplierReceipt entity = supplierReceiptRepository.getReferenceById(id);

			copyDtoToEntity(dto, entity);
			entity = supplierReceiptRepository.save(entity);
			return new SupplierReceiptDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!supplierReceiptRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			supplierReceiptRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	public void copyDtoToEntity(SupplierReceiptDTO dto, SupplierReceipt entity) {
		entity.setMoment(dto.getMoment());
		entity.setDateReceipt(dto.getDateReceipt());
		entity.setAmountSupplier(dto.getAmountSupplier());
		entity.setUnitValue(dto.getUnitValue());

		// 🧮 Cálculo de totalValue
		if (dto.getAmountSupplier() != null && dto.getUnitValue() != null) {
			entity.setTotalValue(
					dto.getAmountSupplier().multiply(dto.getUnitValue()).setScale(2, RoundingMode.HALF_UP));
		}

		try {
			if (dto.getTransactionDescription() != null) {
				TypeTransactionReceipt transaction = TypeTransactionReceipt
						.valueOf(dto.getTransactionDescription().toUpperCase());
				entity.setTransaction(transaction);
			}
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de operação inválida: " + dto.getTransactionDescription());
		}

		try {
			if (dto.getCosts() != null) {
				TypeCosts expenses = TypeCosts.valueOf(dto.getCosts().toUpperCase());
				entity.setCosts(expenses);
			}
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de despesa inválida: " + dto.getCosts());
		}

		Optional.ofNullable(dto.getPartnerId()).ifPresent(id -> {
			Partner partner = partnerRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Parceiro não encontrado"));
			entity.setPartner(partner);
		});

		Optional.ofNullable(dto.getInputId()).ifPresent(id -> {
			Input input = inputRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Insumo não encontrado"));
			entity.setInput(input);
		});

	}

}
