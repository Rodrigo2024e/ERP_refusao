package com.smartprocessrefusao.erprefusao.services;

import java.math.BigDecimal;
import java.util.List;
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

	// --- MÉTODOS AUXILIARES DE PK ---

	/**
	 * Constrói a chave primária composta (ReceiptItemPK) usando referências.
	 * 
	 * @param receiptId  O ID da Receipt (parte da PK).
	 * @param partnerId  O ID do Partner (parte da PK).
	 * @param materialId O ID do Material (parte da PK).
	 * @return A chave ReceiptItemPK totalmente inicializada.
	 */
	private ReceiptItemPK buildReceiptItemPK(Long receiptId, Long partnerId, Long materialId) {
		Receipt receiptRef = receiptRepository.getReferenceById(receiptId);
		Partner partnerRef = partnerRepository.getReferenceById(partnerId);
		Material materialRef = materialRepository.getReferenceById(materialId);

		return new ReceiptItemPK(receiptRef, partnerRef, materialRef);
	}

	@Transactional(readOnly = true)
	public List<ReceiptItemDTO> findAll() {
		return receiptItemRepository.findAll().stream().map(ReceiptItemDTO::new).toList();
	}

	@Transactional(readOnly = true)
	public ReceiptItemDTO findById(Long receiptId, Long partnerId, Long materialId) {
		ReceiptItemPK pk = buildReceiptItemPK(receiptId, partnerId, materialId);

		Optional<ReceiptItem> obj = receiptItemRepository.findById(pk);
		ReceiptItem entity = obj
				.orElseThrow(() -> new ResourceNotFoundException("ReceiptItem not found for the given IDs"));

		return new ReceiptItemDTO(entity);
	}

	@Transactional
	public ReceiptItemDTO insert(Long parentReceiptId, ReceiptItemDTO dto) {

		if (dto.getReceiptId() != null && !dto.getReceiptId().equals(parentReceiptId)) {
			throw new IllegalArgumentException("O Receipt ID no corpo (" + dto.getReceiptId()
					+ ") não corresponde ao ID do Path (" + parentReceiptId + ")");
		}

		// 2. Constrói a PK usando o ID da URL (priorizando o parentReceiptId)
		// O buildReceiptItemPK carrega as referências de Receipt, Partner e Material.
		ReceiptItemPK pk = buildReceiptItemPK(parentReceiptId, dto.getPartnerId(), dto.getMaterialId());

		// 3. Verifica se o item já existe (para evitar duplicidade em uma inserção de
		// chave composta)
		if (receiptItemRepository.existsById(pk)) {
			throw new IllegalArgumentException("Um ReceiptItem com esta chave (ReceiptID: " + parentReceiptId
					+ ", PartnerID: " + dto.getPartnerId() + ", MaterialID: " + dto.getMaterialId() + ") já existe.");
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

	@Transactional
	public ReceiptItemDTO update(Long receiptId, Long partnerId, Long materialId, ReceiptItemDTO dto) {

		// 1. Constrói a PK para localizar o item existente
		ReceiptItemPK pk = buildReceiptItemPK(receiptId, partnerId, materialId);

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

	@Transactional
	public void delete(Long receiptId, Long partnerId, Long materialId) {
		ReceiptItemPK pk = buildReceiptItemPK(receiptId, partnerId, materialId);

		// Se a validação for necessária
		if (!receiptItemRepository.existsById(pk)) {
			throw new ResourceNotFoundException("ReceiptItem not found for deletion.");
		}

		receiptItemRepository.deleteById(pk);
	}

	// --- FUNÇÃO AUXILIAR DE MAPPING ---

	private void copyDtoToEntity(ReceiptItemDTO dto, ReceiptItem entity) {

		// Mapeamento dos campos que não fazem parte da PK:

		try {
			TypeTransactionReceipt typeReceipt = TypeTransactionReceipt.fromDescription(dto.getTypeReceipt());
			entity.setTypeReceipt(typeReceipt);
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de recebimento inválido: " + dto.getTypeReceipt());
		}

		try {
			TypeCosts typeCosts = TypeCosts.fromDescription(dto.getTypeCosts());
			entity.setTypeCosts(typeCosts);
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de custo inválido: " + dto.getTypeCosts());
		}

		entity.setRecoveryYield(dto.getRecoveryYield());
		entity.setDocumentNumber(dto.getDocumentNumber());
		entity.setQuantity(dto.getQuantity());
		entity.setPrice(dto.getPrice());
		entity.setObservation(dto.getObservation());

		// 🧮 Recalcula o valor total (também está no @PrePersist/@PreUpdate da
		// entidade)
		if (dto.getQuantity() != null && dto.getPrice() != null) {
			entity.setTotalValue(dto.getQuantity().multiply(dto.getPrice()));
		} else {
			entity.setTotalValue(BigDecimal.ZERO);
		}

		// 🧮 Recalcula a quantidade de metal líquido com base no rendimento da sucata
		// (também está no @PrePersist/@PreUpdate da entidade)
		if (dto.getQuantity() != null && dto.getRecoveryYield() != null) {
			entity.setQuantityMco(dto.getQuantity().multiply(dto.getRecoveryYield()));
		} else {
			entity.setQuantityMco(BigDecimal.ZERO);
		}

		entity.setObservation(dto.getObservation());
	}
}