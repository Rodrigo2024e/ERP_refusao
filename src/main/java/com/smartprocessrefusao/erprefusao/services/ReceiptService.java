package com.smartprocessrefusao.erprefusao.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.ReceiptDTO;
import com.smartprocessrefusao.erprefusao.dto.ReceiptItemDTO;
import com.smartprocessrefusao.erprefusao.dto.ReceiptItemReportDTO;
import com.smartprocessrefusao.erprefusao.dto.ReceiptReportDTO;
import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.Receipt;
import com.smartprocessrefusao.erprefusao.entities.ReceiptItem;
import com.smartprocessrefusao.erprefusao.entities.PK.ReceiptItemPK;
import com.smartprocessrefusao.erprefusao.enumerados.TypeCosts;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionReceipt;
import com.smartprocessrefusao.erprefusao.projections.ReceiptReportProjection;
import com.smartprocessrefusao.erprefusao.repositories.MaterialRepository;
import com.smartprocessrefusao.erprefusao.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.repositories.ReceiptItemRepository;
import com.smartprocessrefusao.erprefusao.repositories.ReceiptRepository;
import com.smartprocessrefusao.erprefusao.repositories.TicketRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

@Service
public class ReceiptService {

	@Autowired
	private ReceiptRepository receiptRepository;

	@Autowired
	private ReceiptItemRepository receiptItemRepository;

	@Autowired
	private PartnerRepository partnerRepository;

	@Autowired
	private MaterialRepository materialRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private InventoryService inventoryService;

	@Transactional
	public ReceiptDTO insert(ReceiptDTO dto) {

		// 1. Validação de número de ticket
		Long numTicket = Optional.ofNullable(dto.getNumTicket())
				.orElseThrow(() -> new IllegalArgumentException("O número do ticket é obrigatório."));
		ticketRepository.findByNumTicket(numTicket).ifPresent(e -> {
			throw new IllegalArgumentException("O número de ticket '" + numTicket + "' já existe no sistema.");
		});

		// 2. Validação de soma das quantidades
		BigDecimal totalItemsQuantity = calculateTotalItemQuantity(dto);
		if (dto.getNetWeight() != null && totalItemsQuantity.compareTo(dto.getNetWeight()) > 0) {
			throw new IllegalArgumentException("A soma das quantidades dos itens (" + totalItemsQuantity
					+ ") não pode ultrapassar o Peso Líquido (Net Weight) do ticket (" + dto.getNetWeight() + ").");
		}

		// 3. Mapeamento DTO → Entidade
		Receipt entity = new Receipt();
		copyDtoToEntity(dto, entity);
		entity = receiptRepository.save(entity);

		// 4. Itens
		Map<Long, Partner> partnerCache = new HashMap<>();
		Map<Long, Material> materialCache = new HashMap<>();
		entity.getReceiptItems().clear();

		if (dto.getReceiptItems() != null) {
			Integer sequence = 1;
			for (ReceiptItemDTO itemDto : dto.getReceiptItems()) {

				ReceiptItem item = new ReceiptItem();

				Long partnerId = Optional.ofNullable(itemDto.getPartnerId())
						.orElseThrow(() -> new ResourceNotFoundException("Partner ID é obrigatório."));

				Partner partner = partnerCache.computeIfAbsent(partnerId, id -> partnerRepository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException("Partner não encontrado: id = " + id)));

				Long code = Optional.ofNullable(itemDto.getMaterialCode())
						.orElseThrow(() -> new ResourceNotFoundException("Código do material é obrigatório."));

				Material material = materialCache.computeIfAbsent(code,
						id -> materialRepository.findByMaterialCode(code).orElseThrow(
								() -> new ResourceNotFoundException("Material não encontrado para o code: " + code)));

				copyItemDtoToEntity(itemDto, item, entity, partner, material, sequence);
				entity.getReceiptItems().add(item);
				sequence++;
			}
		}

		// 5. Movimenta o estoque (entrada)
		inventoryService.insertFromReceipt(entity);

		return new ReceiptDTO(entity);
	}

	// --------------------------------------------------------------------------
	// UPDATE
	// --------------------------------------------------------------------------
	@Transactional
	public ReceiptDTO updateByNumTicket(Long numTicket, ReceiptDTO dto) {

		Receipt entity = receiptRepository.findByNumTicket(numTicket).orElseThrow(
				() -> new ResourceNotFoundException("Receipt não encontrado para atualização, ID: " + numTicket));

		/*
		 * ===================================================== 1️⃣ VALIDAÇÃO DE
		 * UNICIDADE DO NUMTICKET =====================================================
		 */
		Long newNumTicket = Optional.ofNullable(dto.getNumTicket())
				.orElseThrow(() -> new IllegalArgumentException("O número do ticket é obrigatório."));

		final Long entityId = entity.getId();

		ticketRepository.findByNumTicket(newNumTicket).ifPresent(existing -> {
			if (!existing.getId().equals(entityId)) {
				throw new IllegalArgumentException(
						String.format("O número de ticket '%d' já está em uso por outro registro.", newNumTicket));
			}
		});

		/*
		 * ===================================================== 2️⃣ REGRA DE NEGÓCIO —
		 * PESO LÍQUIDO =====================================================
		 */
		BigDecimal totalItemsQuantity = calculateTotalItemQuantity(dto);

		if (dto.getNetWeight() != null && totalItemsQuantity.compareTo(dto.getNetWeight()) > 0) {

			throw new IllegalArgumentException("A soma das quantidades dos itens (" + totalItemsQuantity
					+ ") não pode ultrapassar o Peso Líquido (" + dto.getNetWeight() + ").");
		}

		/*
		 * ===================================================== 3️⃣ ATUALIZA CAMPOS
		 * SIMPLES =====================================================
		 */
		copyDtoToEntity(dto, entity);

		/*
		 * ===================================================== 4️⃣ MAPA DOS ITENS
		 * ATUAIS (CHAVE = PK) =====================================================
		 */
		Map<ReceiptItemPK, ReceiptItem> currentItems = entity.getReceiptItems().stream()
				.collect(Collectors.toMap(ReceiptItem::getId, Function.identity()));

		/*
		 * ===================================================== 5️⃣ CACHE DE APOIO
		 * =====================================================
		 */
		Map<Long, Partner> partnerCache = new HashMap<>();
		Map<Long, Material> materialCache = new HashMap<>();

		/*
		 * ===================================================== 6️⃣ PROCESSA ITENS DO
		 * DTO (DIFF INTELIGENTE) =====================================================
		 */
		if (dto.getReceiptItems() != null) {

			Integer sequence = 1;

			for (ReceiptItemDTO itemDto : dto.getReceiptItems()) {

				Partner partner = partnerCache.computeIfAbsent(itemDto.getPartnerId(),
						id -> partnerRepository.findById(id)
								.orElseThrow(() -> new ResourceNotFoundException("Parceiro não encontrado: " + id)));

				Material material = materialCache.computeIfAbsent(itemDto.getMaterialCode(),
						id -> materialRepository.findByMaterialCode(id)
								.orElseThrow(() -> new ResourceNotFoundException("Material não encontrado: " + id)));

				// 🔑 PK COMPLETA
				ReceiptItemPK pk = new ReceiptItemPK(entity, partner, material);
				pk.setItemSequence(sequence);

				ReceiptItem item = currentItems.remove(pk);

				if (item == null) {
					// ➕ NOVO ITEM
					item = new ReceiptItem();
					item.setId(pk);
					copyItemDtoToEntity(itemDto, item, entity, partner, material, sequence);
					entity.getReceiptItems().add(item);
				} else {
					// ✏️ UPDATE DE ITEM EXISTENTE
					copyItemDtoToEntity(itemDto, item, entity, partner, material, sequence);
				}

				sequence++;
			}
		}

		/*
		 * ===================================================== 7️⃣ REMOVE ORPHANS
		 * (SEGURANÇA TOTAL) =====================================================
		 */
		for (ReceiptItem orphan : currentItems.values()) {
			entity.getReceiptItems().remove(orphan);
		}

		/*
		 * ===================================================== 8️⃣ SALVA (CASCADE +
		 * ORPHAN OK) =====================================================
		 */
		entity = receiptRepository.save(entity);

		/*
		 * ===================================================== 9️⃣ ATUALIZA ESTOQUE
		 * =====================================================
		 */
		inventoryService.updateFromReceipt(entity);

		return new ReceiptDTO(entity);
	}

	// --------------------------------------------------------------------------
	// DELETE
	// --------------------------------------------------------------------------
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Long numTicket) {
		Receipt receipt = receiptRepository.findByNumTicket(numTicket)
				.orElseThrow(() -> new ResourceNotFoundException("Ticket não encontrado: " + numTicket));

		try {
			// Remove movimento de estoque
			inventoryService.deleteByReceipt(receipt);

			// Remove o recebimento
			receiptRepository.delete(receipt);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de integridade ao excluir o recebimento.");
		}
	}

	private void copyDtoToEntity(ReceiptDTO dto, Receipt entity) {
		entity.setNumTicket(dto.getNumTicket());
		entity.setDateTicket(dto.getDateTicket());
		entity.setNumberPlate(dto.getNumberPlate() != null ? dto.getNumberPlate().toUpperCase() : null);
		entity.setNetWeight(dto.getNetWeight());

	}

	private void copyItemDtoToEntity(ReceiptItemDTO dto, ReceiptItem entity, Receipt receipt, Partner partner,
			Material material, Integer sequence) {
		entity.getId().setReceipt(receipt);
		entity.getId().setPartner(partner);
		entity.getId().setMaterial(material);
		entity.getId().setItemSequence(sequence);

		entity.setRecoveryYield(dto.getRecoveryYield());
		entity.setQuantity(dto.getQuantity());
		entity.setPrice(dto.getPrice());
		entity.setTotalValue(dto.getQuantity().multiply(dto.getPrice()));
		entity.setQuantityMco(dto.getQuantity().multiply(dto.getRecoveryYield()));
		entity.setObservation(dto.getObservation() != null ? dto.getObservation().toUpperCase() : null);
		entity.setDocumentNumber(dto.getDocumentNumber());

		try {
			TypeTransactionReceipt typeReceipt = TypeTransactionReceipt.fromDescription(dto.getTypeReceipt());
			entity.setTypeReceipt(typeReceipt);
		} catch (

		IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de recebimento inválido: " + dto.getTypeReceipt());
		}

		try {
			TypeCosts typeCosts = TypeCosts.fromDescription(dto.getTypeCosts());
			entity.setTypeCosts(typeCosts);
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de custo inválido: " + dto.getTypeCosts());
		}
	}

	private BigDecimal calculateTotalItemQuantity(ReceiptDTO dto) {
		if (dto.getReceiptItems() == null)
			return BigDecimal.ZERO;

		return dto.getReceiptItems().stream().map(i -> Optional.ofNullable(i.getQuantity()).orElse(BigDecimal.ZERO))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	// REPORT
	@Transactional(readOnly = true)
	public Page<ReceiptReportDTO> findDetails(
			Long id,
			Long numTicket,
			LocalDate startDate,
	        LocalDate endDate,
	        Long partnerId,
			String materialDescription,
			Long materialCode,
	        Pageable pageable) {

	    Page<ReceiptReportProjection> page =
	            receiptRepository.reportReceipt(
	            		numTicket,
	                    startDate,
	                    endDate,
	                    pageable
	            );

		List<Long> receiptIds = page.stream()
	            .map(ReceiptReportProjection::getReceiptId)
	            .toList();

	    Map<Long, List<ReceiptItemReportDTO>> itemsMap =
	            receiptItemRepository.findItemsByReceiptIds(
	            		receiptIds,
	            		numTicket,
	            		startDate,
	            		endDate,
	            		partnerId,
	            		materialDescription,
	            		materialCode)
	                    .stream()
	                    .map(p -> new ReceiptItemReportDTO(p))
	                    .collect(Collectors.groupingBy(ReceiptItemReportDTO::getReceiptId));

	    return page.map(p -> new ReceiptReportDTO(
	    		p.getReceiptId(),
	    		p.getNumTicket(),
	            p.getDateTicket(),
	            p.getNumberPlate(),
	            p.getNetWeight(),
	            itemsMap.getOrDefault(p.getReceiptId(), List.of())
	    ));
	}

}