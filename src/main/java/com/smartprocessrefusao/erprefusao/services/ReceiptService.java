package com.smartprocessrefusao.erprefusao.services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.ReceiptDTO;
import com.smartprocessrefusao.erprefusao.dto.ReceiptItemDTO;
import com.smartprocessrefusao.erprefusao.dto.ReportReceiptDTO;
import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.Receipt;
import com.smartprocessrefusao.erprefusao.entities.ReceiptItem;
import com.smartprocessrefusao.erprefusao.enumerados.TypeCosts;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionReceipt;
import com.smartprocessrefusao.erprefusao.projections.ReportReceiptProjection;
import com.smartprocessrefusao.erprefusao.repositories.MaterialRepository;
import com.smartprocessrefusao.erprefusao.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.repositories.ReceiptRepository;
import com.smartprocessrefusao.erprefusao.repositories.TicketRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

@Service
public class ReceiptService {

	@Autowired
	private ReceiptRepository receiptRepository;

	@Autowired
	private PartnerRepository partnerRepository;

	@Autowired
	private MaterialRepository materialRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Transactional(readOnly = true)
	public Page<ReportReceiptDTO> reportReceipt(Long receiptId, String description, Long numTicket, Long people_id,
			Pageable pageable) {
		Page<ReportReceiptProjection> page = receiptRepository.searchDescriptionMaterialOrNumTicketPeople(receiptId,
				description, numTicket, people_id, pageable);
		return page.map(ReportReceiptDTO::new);
	}

	@Transactional
	public ReceiptDTO insert(ReceiptDTO dto) {

		Long numTicket = java.util.Optional.ofNullable(dto.getNumTicket())
				.orElseThrow(() -> new IllegalArgumentException("O número do ticket é obrigatório."));

		// 2. VALIDAÇÃO DE UNICIDADE (sem 'if' explícito)
		// Se o repositório encontrar um ticket com esse numTicket, lança uma exceção.
		ticketRepository.findByNumTicket(numTicket).ifPresent(e -> {
			throw new IllegalArgumentException(
					String.format("O número de ticket '%d' já existe no sistema.", numTicket));
		});

		// 1. VALIDAÇÃO DE NEGÓCIO: A soma das quantidades dos itens não pode exceder o
		// Net Weight.
		BigDecimal totalItemsQuantity = calculateAndValidateItemQuantities(dto);

		if (dto.getNetWeight() != null && totalItemsQuantity.compareTo(dto.getNetWeight()) > 0) {
			throw new IllegalArgumentException("A soma das quantidades dos itens (" + totalItemsQuantity
					+ ") não pode ultrapassar o Peso Líquido (Net Weight) do ticket (" + dto.getNetWeight() + ").");
		}

		// 2. Inicializa o Cache (para evitar NonUniqueObjectException com
		// Partner/Material)
		Map<Long, Partner> partnerCache = new HashMap<>();
		Map<Long, Material> materialCache = new HashMap<>();

		// 3. Mapear DTO para a entidade principal
		Receipt newReceipt = new Receipt();
		copyReceiptDtoToEntity(dto, newReceipt);

		// 4. Persistir o Receipt principal para obter o ID gerado
		newReceipt = receiptRepository.save(newReceipt);

		// 5. Iterar sobre os DTOs de itens e configurar as entidades.
		if (dto.getReceiptItems() != null) {

			newReceipt.getReceiptItems().clear();

			// Necessário para a unicidade do item, pois a PK é (ID, Partner, Material,
			// Sequência)
			Long sequenceCounter = 1L;

			for (ReceiptItemDTO itemDto : dto.getReceiptItems()) {

				ReceiptItem itemEntity = new ReceiptItem();

				// 5a. Obter instâncias gerenciadas do Cache (Partner/Material)
				Long partnerId = itemDto.getPartnerId();
				if (partnerId == null) {
					throw new ResourceNotFoundException("Partner ID é obrigatório para a PK do Item.");
				}
				Partner partner = partnerCache.computeIfAbsent(partnerId, id -> partnerRepository.getReferenceById(id));

				Long materialId = itemDto.getMaterialId();
				if (materialId == null) {
					throw new ResourceNotFoundException("Material ID é obrigatório para a PK do Item.");
				}
				Material material = materialCache.computeIfAbsent(materialId,
						id -> materialRepository.getReferenceById(id));

				// 5b. Configurar a chave composta (PK) e copiar os dados.
				copyItemDtoToItemEntity(itemDto, itemEntity, newReceipt, partner, material, sequenceCounter

				);

				// Adiciona o item à coleção.
				newReceipt.getReceiptItems().add(itemEntity);

				sequenceCounter++;
			}
		}

		return new ReceiptDTO(newReceipt);
	}

	@Transactional
	public ReceiptDTO updateByNumTicket(Long numTicket, ReceiptDTO dto) {
		Receipt entity = receiptRepository.findByNumTicket(numTicket)
				.orElseThrow(() -> new ResourceNotFoundException("Receipt não encontrado para atualização, ID: " + numTicket));

		// 2. VALIDAÇÃO DE UNICIDADE DO numTicket (Herdado de Ticket)
		// a) Garante que o numTicket do DTO não é nulo e obtém o valor
		Long newNumTicket = Optional.ofNullable(dto.getNumTicket())
				.orElseThrow(() -> new IllegalArgumentException("O número do ticket é obrigatório."));

		ticketRepository.findByNumTicket(newNumTicket).ifPresent(existing -> {
			if (!existing.getId().equals(entity.getId())) {
				throw new IllegalArgumentException(
						String.format("O número de ticket '%d' já está em uso por outro registro.", newNumTicket));
			}
		});

		// 1. VALIDAÇÃO DE NEGÓCIO: A soma das quantidades dos itens não pode exceder o
		// Net Weight.
		BigDecimal totalItemsQuantity = calculateAndValidateItemQuantities(dto);

		if (dto.getNetWeight() != null && totalItemsQuantity.compareTo(dto.getNetWeight()) > 0) {
			throw new IllegalArgumentException("A soma das quantidades dos itens (" + totalItemsQuantity
					+ ") não pode ultrapassar o Peso Líquido (Net Weight) do ticket (" + dto.getNetWeight() + ").");
		}

		copyReceiptDtoToEntity(dto, entity);

		Map<Long, Partner> partnerCache = new HashMap<>();
		Map<Long, Material> materialCache = new HashMap<>();

		entity.getReceiptItems().clear();

		if (dto.getReceiptItems() != null) {
			Long sequenceCounter = 1L;

			for (ReceiptItemDTO itemDto : dto.getReceiptItems()) {

				ReceiptItem itemEntity = new ReceiptItem();

				Long partnerId = itemDto.getPartnerId();
				Partner partner = partnerCache.computeIfAbsent(partnerId,
						pid -> partnerRepository.getReferenceById(pid));

				Long materialId = itemDto.getMaterialId();
				Material material = materialCache.computeIfAbsent(materialId,
						mid -> materialRepository.getReferenceById(mid));

				// Configura a chave composta (PK) e copia os dados.
				copyItemDtoToItemEntity(itemDto, itemEntity, entity, partner, material, sequenceCounter);

				entity.getReceiptItems().add(itemEntity);

				sequenceCounter++;
			}
		}

		return new ReceiptDTO(receiptRepository.save(entity));
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long numTicket) {
		if (!receiptRepository.existsByNumTicket(numTicket)) {
			throw new ResourceNotFoundException("Ticket not found " + numTicket);
		}
		try {
			receiptRepository.deleteByNumTicket(numTicket);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	// --- MÉTODOS AUXILIARES ---

	private void copyReceiptDtoToEntity(ReceiptDTO dto, Receipt entity) {
		entity.setNumTicket(Long.valueOf(dto.getNumTicket()));
		entity.setDateTicket(dto.getDateTicket());
		entity.setNumberPlate(dto.getNumberPlate().toUpperCase());
		entity.setNetWeight(dto.getNetWeight());
	}

	private void copyItemDtoToItemEntity(ReceiptItemDTO itemDto, ReceiptItem itemEntity, Receipt parentReceipt,
			Partner partner, Material material, Long itemSequence) {

		// --- 1. SETA OS COMPONENTES DA CHAVE COMPOSTA (PK) ---
		itemEntity.getId().setReceipt(parentReceipt);
		itemEntity.getId().setPartner(partner);
		itemEntity.getId().setMaterial(material);
		itemEntity.getId().setItemSequence(itemSequence); // Novo campo sequencial

		// --- 2. SETA OUTROS ATRIBUTOS (Dados Simples) ---
		itemEntity.setDocumentNumber(itemDto.getDocumentNumber());
		itemEntity.setObservation(itemDto.getObservation().toUpperCase());
		itemEntity.setPrice(itemDto.getPrice());
		itemEntity.setQuantity(itemDto.getQuantity());

		BigDecimal totalValue = itemEntity.getQuantity().multiply(itemEntity.getPrice());
		itemEntity.setTotalValue(totalValue);

		// Conversão de Enum
		try {
			TypeTransactionReceipt typeReceipt = TypeTransactionReceipt.fromDescription(itemDto.getTypeReceipt());
			itemEntity.setTypeReceipt(typeReceipt);
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de recebimento inválido: " + itemDto.getTypeReceipt());
		}

		try {
			TypeCosts typeCosts = TypeCosts.fromDescription(itemDto.getTypeCosts());
			itemEntity.setTypeCosts(typeCosts);
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de custo inválido: " + itemDto.getTypeCosts());
		}
	}

	/**
	 * Calcula a soma total das quantidades dos itens e valida o formato. * @param
	 * dto O ReceiptDTO contendo a lista de ReceiptItemDTOs.
	 * 
	 * @return A soma total das quantidades dos itens como BigDecimal.
	 */
	private BigDecimal calculateAndValidateItemQuantities(ReceiptDTO dto) {
		if (dto.getReceiptItems() == null || dto.getReceiptItems().isEmpty()) {
			return BigDecimal.ZERO;
		}

		BigDecimal totalQuantity = BigDecimal.ZERO;

		for (ReceiptItemDTO itemDto : dto.getReceiptItems()) {

			BigDecimal itemQuantity = itemDto.getQuantity();

			if (itemQuantity == null) {

				itemQuantity = BigDecimal.ZERO;
			}

			totalQuantity = totalQuantity.add(itemQuantity);
		}

		return totalQuantity;
	}
}
