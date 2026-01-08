package com.smartprocessrefusao.erprefusao.services;

import java.math.BigDecimal;
import java.util.List;

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

	private Receipt validateReceipt(Long receiptId) {
		return receiptRepository.findById(receiptId)
				.orElseThrow(() -> new ResourceNotFoundException("Receipt não encontrado: id = " + receiptId));
	}

	private Partner validatePartner(Long partnerId) {
		return partnerRepository.findById(partnerId)
				.orElseThrow(() -> new ResourceNotFoundException("Partner não encontrado: id = " + partnerId));
	}

	private Material validateMaterial(Long code) {
		return materialRepository.findById(code)
				.orElseThrow(() -> new ResourceNotFoundException("Material não encontrado: code = " + code));
	}

	// --- MÉTODOS AUXILIARES DE PK ---

	/**
	 * Constrói a chave primária composta (ReceiptItemPK) usando referências.
	 * 
	 * @param receiptId O ID da Receipt (parte da PK).
	 * @param partnerId O ID do Partner (parte da PK).
	 * @param code      do Material (parte da PK).
	 * @return A chave ReceiptItemPK totalmente inicializada.
	 */
	private ReceiptItemPK buildReceiptItemPK(Long receiptId, Long partnerId, Long code) {

		Receipt receipt = validateReceipt(receiptId);
		Partner partner = validatePartner(partnerId);
		Material material = validateMaterial(code);

		return new ReceiptItemPK(receipt, partner, material);
	}

	@Transactional(readOnly = true)
	public List<ReceiptItemDTO> findAll() {
		return receiptItemRepository.findAll().stream().map(ReceiptItemDTO::new).toList();
	}

	@Transactional(readOnly = true)
	public ReceiptItemDTO findById(Long receiptId, Long partnerId, Long code) {
		ReceiptItemPK pk = buildReceiptItemPK(receiptId, partnerId, code);

		ReceiptItem entity = receiptItemRepository.findById(pk)
				.orElseThrow(() -> new ResourceNotFoundException("ReceiptItem não encontrado para os IDs informados"));

		return new ReceiptItemDTO(entity);
	}

	@Transactional
	public ReceiptItemDTO insert(Long parentReceiptId, ReceiptItemDTO dto) {

		// valida coerência do ID no corpo
		if (dto.getReceiptId() != null && !dto.getReceiptId().equals(parentReceiptId)) {
			throw new IllegalArgumentException("ReceiptId no corpo (" + dto.getReceiptId()
					+ ") difere do ID passado na URL (" + parentReceiptId + ")");
		}

		// valida e monta a chave primária
		ReceiptItemPK pk = buildReceiptItemPK(parentReceiptId, dto.getPartnerId(), dto.getMaterialCode());

		if (receiptItemRepository.existsById(pk)) {
			throw new IllegalArgumentException("ReceiptItem já existe com (Receipt=" + parentReceiptId + ", Partner="
					+ dto.getPartnerId() + ", Code=" + dto.getMaterialCode() + ")");
		}

		ReceiptItem entity = new ReceiptItem();
		entity.setId(pk);

		copyDtoToEntity(dto, entity);

		entity = receiptItemRepository.save(entity);
		return new ReceiptItemDTO(entity);

	}

	@Transactional
	public ReceiptItemDTO update(Long receiptId, Long partnerId, Long code, ReceiptItemDTO dto) {

		// 1. Constrói a PK para localizar o item existente
		ReceiptItemPK pk = buildReceiptItemPK(receiptId, partnerId, code);

		ReceiptItem entity = receiptItemRepository.findById(pk)
				.orElseThrow(() -> new ResourceNotFoundException("ReceiptItem não encontrado para update."));

		copyDtoToEntity(dto, entity);

		entity = receiptItemRepository.save(entity);
		return new ReceiptItemDTO(entity);
	}

	@Transactional
	public void delete(Long receiptId, Long partnerId, Long code) {
		ReceiptItemPK pk = buildReceiptItemPK(receiptId, partnerId, code);

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