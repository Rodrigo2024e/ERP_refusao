package com.smartprocessrefusao.erprefusao.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.ReceiptItemDTO;
import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.Receipt;
import com.smartprocessrefusao.erprefusao.entities.ReceiptItem;
import com.smartprocessrefusao.erprefusao.enumerados.TypeCosts;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionReceipt;
import com.smartprocessrefusao.erprefusao.repositories.MaterialRepository;
import com.smartprocessrefusao.erprefusao.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.repositories.ReceiptItemRepository;
import com.smartprocessrefusao.erprefusao.repositories.ReceiptRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;



@Service
public class ReceiptItemService {

	@Autowired
	private ReceiptItemRepository receiptItemRepository;

	@Autowired
	private ReceiptRepository receiptRepository;

	@Autowired
	private PartnerRepository partnerRepository;

	@Autowired
	private MaterialRepository materialRepository;
	
	//INSERT
	@Transactional
	public ReceiptItemDTO insert(Long receiptId, ReceiptItemDTO dto) {

		Receipt receipt = receiptRepository.findById( dto.getReceiptId())
				.orElseThrow(() -> new ResourceNotFoundException("Receipt não encontrado: " + dto.getReceiptId()));

		Partner partner = partnerRepository.findById(dto.getPartnerId())
				.orElseThrow(() -> new ResourceNotFoundException("Partner não encontrado: " + dto.getPartnerId()));

		Material material = materialRepository.findByMaterialCode(dto.getMaterialCode())
				.orElseThrow(() -> new ResourceNotFoundException("Material não encontrado: " + dto.getMaterialCode()));

		ReceiptItem entity = new ReceiptItem();
		entity.setReceipt(receipt);
		entity.setPartner(partner);
		entity.setMaterial(material);

		// 🔢 sequência (exemplo simples)
		Integer nextSequence = receiptItemRepository
				.findMaxSequenceByReceiptId(receiptId)
				.orElse(0) + 1;

		entity.setItemSequence(nextSequence);

		copyDtoToEntity(dto, entity);

		entity = receiptItemRepository.save(entity);
		return new ReceiptItemDTO(entity);
	}

	// UPDATE
	@Transactional
	public ReceiptItemDTO update(Long itemId, ReceiptItemDTO dto) {

		ReceiptItem entity = receiptItemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("ReceiptItem não encontrado: " + itemId));

		if (dto.getPartnerId() != null &&
				!dto.getPartnerId().equals(entity.getPartner().getId())) {

			Partner partner = partnerRepository.findById(dto.getPartnerId())
					.orElseThrow(() -> new ResourceNotFoundException("Partner não encontrado: " + dto.getPartnerId()));
			entity.setPartner(partner);
		}

		if (dto.getMaterialCode() != null &&
				!dto.getMaterialCode().equals(entity.getMaterial().getMaterialCode())) {

			Material material = materialRepository.findByMaterialCode(dto.getMaterialCode())
					.orElseThrow(() -> new ResourceNotFoundException("Material não encontrado: " + dto.getMaterialCode()));
			entity.setMaterial(material);
		}

		copyDtoToEntity(dto, entity);

		entity = receiptItemRepository.save(entity);
		return new ReceiptItemDTO(entity);
	}
	
	// DELETE
	@Transactional
	public void delete(Long itemId) {

		if (!receiptItemRepository.existsById(itemId)) {
			throw new ResourceNotFoundException("ReceiptItem não encontrado para exclusão: " + itemId);
		}

		receiptItemRepository.deleteById(itemId);
	}

	// =========================
	// MAPPING AUXILIAR
	// =========================
	private void copyDtoToEntity(ReceiptItemDTO itemDto, ReceiptItem entity) {

		entity.setDocumentNumber(itemDto.getDocumentNumber());
		entity.setRecoveryYield(itemDto.getRecoveryYield());
		entity.setQuantity(itemDto.getQuantity());
		entity.setPrice(itemDto.getPrice());
		entity.setObservation(itemDto.getObservation());

		try {
			entity.setTypeReceipt(TypeTransactionReceipt.fromDescription(itemDto.getTypeReceipt()));
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de recebimento inválido: " + itemDto.getTypeReceipt());
		}

		try {
			entity.setTypeCosts(TypeCosts.fromDescription(itemDto.getTypeCosts()));
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de custo inválido: " + itemDto.getTypeCosts());
		}

		// totalValue
		if (itemDto.getQuantity() != null && itemDto.getPrice() != null) {
			entity.setTotalValue(itemDto.getQuantity().multiply(itemDto.getPrice()));
		} else {
			entity.setTotalValue(BigDecimal.ZERO);
		}

		// quantityMco
		if (itemDto.getQuantity() != null && itemDto.getRecoveryYield() != null) {
			entity.setQuantityMco(itemDto.getQuantity().multiply(itemDto.getRecoveryYield()));
		} else {
			entity.setQuantityMco(BigDecimal.ZERO);
		}
	}
}



