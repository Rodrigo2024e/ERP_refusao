package com.smartprocessrefusao.erprefusao.services;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.ReceiptItemDTO;
import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.Receipt;
import com.smartprocessrefusao.erprefusao.entities.ReceiptItem;
import com.smartprocessrefusao.erprefusao.entities.PK.ReceiptItemPK;
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

	/**
	 * Constrói a chave primária composta (DispatchItemPK) usando referências.
	 * 
	 * @param dispatchId O ID da Dispatch (parte da PK).
	 * @param partnerId  O ID do Partner (parte da PK).
	 * @param productId  O ID do Product (parte da PK).
	 * @return A chave DispatchItemPK totalmente inicializada.
	 */
	private ReceiptItemPK buildReceiptItemPK(Long receiptId, Long partnerId, Long materialCode) {
		Receipt receiptRef = receiptRepository.getReferenceById(receiptId);
		Partner partnerRef = partnerRepository.getReferenceById(partnerId);
		Material materialRef = materialRepository.getReferenceById(materialCode);

		return new ReceiptItemPK(receiptRef, partnerRef, materialRef);
	}

	// FINDBYID
	@Transactional(readOnly = true)
	public ReceiptItemDTO findById(Long receiptId, Long partnerId, Long materialCode) {
		ReceiptItemPK pk = buildReceiptItemPK(receiptId, partnerId, materialCode);

		Optional<ReceiptItem> obj = receiptItemRepository.findById(pk);
		ReceiptItem entity = obj
				.orElseThrow(() -> new ResourceNotFoundException("ReceiptItem not found for the given IDs"));

		return new ReceiptItemDTO(entity);
	}

	// INSERT
	@Transactional
	public ReceiptItemDTO insert(Long parentReceiptId, ReceiptItemDTO dto) {

		if (dto.getReceiptId() != null && !dto.getReceiptId().equals(parentReceiptId)) {
			throw new IllegalArgumentException("O Receipt ID no corpo (" + dto.getReceiptId()
					+ ") não corresponde ao ID do Path (" + parentReceiptId + ")");
		}

		// 2. Constrói a PK usando o ID da URL (priorizando o parentReceiptId)
		// O buildReceiptItemPK carrega as referências de Receipt, Partner e Material.
		ReceiptItemPK pk = buildReceiptItemPK(parentReceiptId, dto.getPartnerId(), dto.getMaterialCode());

		// 3. Verifica se o item já existe (para evitar duplicidade em uma inserção de
		// chave composta)
		if (receiptItemRepository.existsById(pk)) {
			throw new IllegalArgumentException("Um ReceiptItem com esta chave (ReceiptID: " + parentReceiptId
					+ ", PartnerID: " + dto.getPartnerId() + ", MaterialID: " + dto.getMaterialCode() + ") já existe.");
		}

		// 4. Cria a nova entidade e seta a PK (que está totalmente inicializada)
		ReceiptItem entity = new ReceiptItem();
		entity.setId(pk);

		// 5. Copia os campos de dados não-PK do DTO para a Entidade
		// Usamos o DTO original, pois o copyDtoToEntity lida apenas com os campos de
		// dados (quantity, price, observation, etc.).
		copyDtoToEntity(dto, entity);

		// 6. Salva e retorna o DTO
		entity = receiptItemRepository.save(entity);
		return new ReceiptItemDTO(entity);
	}

	// UPDATE
	@Transactional
	public ReceiptItemDTO update(Long receiptId, Long partnerId, Long materialCode, ReceiptItemDTO dto) {

		// 1. Constrói a PK para localizar o item existente
		ReceiptItemPK pk = buildReceiptItemPK(receiptId, partnerId, materialCode);

		try {
			// 2. Obtém a referência da entidade
			ReceiptItem entity = receiptItemRepository.getReferenceById(pk);

			// 3. Copia os novos dados (a PK não precisa ser alterada)
			copyDtoToEntity(dto, entity);

			// 4. Salva e converte para DTO
			entity = receiptItemRepository.save(entity);
			return new ReceiptItemDTO(entity);

		} catch (jakarta.persistence.EntityNotFoundException e) {
			throw new ResourceNotFoundException("ReceiptItem not found for update.");
		}
	}

	// DELETE
	@Transactional
	public void delete(Long receiptId, Long partnerId, Long materialCode) {
		ReceiptItemPK pk = buildReceiptItemPK(receiptId, partnerId, materialCode);

		// Se a validação for necessária
		if (!receiptItemRepository.existsById(pk)) {
			throw new ResourceNotFoundException("ReceiptItem not found for deletion.");
		}

		receiptItemRepository.deleteById(pk);
	}
	// =======================
	// MAPPER
	// =======================

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
