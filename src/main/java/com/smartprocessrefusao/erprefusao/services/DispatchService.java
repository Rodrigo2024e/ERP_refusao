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

import com.smartprocessrefusao.erprefusao.dto.DispatchDTO;
import com.smartprocessrefusao.erprefusao.dto.DispatchItemDTO;
import com.smartprocessrefusao.erprefusao.dto.DispatchItemReportDTO;
import com.smartprocessrefusao.erprefusao.dto.DispatchReportDTO;
import com.smartprocessrefusao.erprefusao.entities.Dispatch;
import com.smartprocessrefusao.erprefusao.entities.DispatchItem;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.Product;
import com.smartprocessrefusao.erprefusao.entities.PK.DispatchItemPK;
import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloy;
import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloyFootage;
import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloyPol;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionOutGoing;
import com.smartprocessrefusao.erprefusao.projections.DispatchReportProjection;
import com.smartprocessrefusao.erprefusao.repositories.DispatchItemRepository;
import com.smartprocessrefusao.erprefusao.repositories.DispatchRepository;
import com.smartprocessrefusao.erprefusao.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.repositories.ProductRepository;
import com.smartprocessrefusao.erprefusao.repositories.TicketRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

@Service
public class DispatchService {

	@Autowired
	private DispatchRepository dispatchRepository;

	@Autowired
	private DispatchItemRepository dispatchItemRepository;

	@Autowired
	private PartnerRepository partnerRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private TicketRepository ticketRepository;

	
	//INSERT
	@Transactional
	public DispatchDTO insert(DispatchDTO dto) {

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
		Dispatch entity = new Dispatch();
		copyDtoToEntity(dto, entity);
		entity = dispatchRepository.save(entity);

		// 4. Itens
		Map<Long, Partner> partnerCache = new HashMap<>();
		Map<Long, Product> productCache = new HashMap<>();
		entity.getDispatchItems().clear();

		if (dto.getDispatchItems() != null) {
			Integer sequence = 1;
			for (DispatchItemDTO itemDto : dto.getDispatchItems()) {

				DispatchItem item = new DispatchItem();

				Long partnerId = Optional.ofNullable(itemDto.getPartnerId())
						.orElseThrow(() -> new ResourceNotFoundException("Partner ID é obrigatório."));

				Partner partner = partnerCache.computeIfAbsent(partnerId, id -> partnerRepository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException("Partner não encontrado: id = " + id)));

				Long productCode = Optional.ofNullable(itemDto.getProductCode())
						.orElseThrow(() -> new ResourceNotFoundException("Código do product é obrigatório."));

				Product product = productCache.computeIfAbsent(productCode,
						id -> productRepository.findByProductCode(productCode)
								.orElseThrow(() -> new ResourceNotFoundException(
										"Product não encontrado para o code: " + productCode)));

				copyItemDtoToEntity(itemDto, item, entity, partner, product, sequence);
				entity.getDispatchItems().add(item);
				sequence++;
			}
		}

		return new DispatchDTO(entity);
	}

	//UPDATE
	@Transactional
	public DispatchDTO updateByNumTicket(Long numTicket, DispatchDTO dto) {

		Dispatch entity = dispatchRepository.findByNumTicket(numTicket).orElseThrow(
				() -> new ResourceNotFoundException("Dispatch não encontrado para atualização, ID: " + numTicket));

		/*
		 * == 1️⃣ VALIDAÇÃO DE
		 * UNICIDADE DO NUMTICKET ===
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
		 * 2️ REGRA DE NEGÓCIO —
		 * PESO LÍQUIDO =====================================================
		 */
		BigDecimal totalItemsQuantity = calculateTotalItemQuantity(dto);
		
		if (dto.getNetWeight() != null && totalItemsQuantity.compareTo(dto.getNetWeight()) > 0) {
			throw new IllegalArgumentException("A soma das quantidades dos itens (" + totalItemsQuantity
					+ ") não pode ultrapassar o Peso Líquido (" + dto.getNetWeight() + ").");
		}

		/*
		 * 3️ ATUALIZA CAMPOS
		 * SIMPLES =====================================================
		 */
		copyDtoToEntity(dto, entity);

		/*
		 *  4️ MAPA DOS ITENS
		 * ATUAIS (CHAVE = PK) =====================================================
		 */
		Map<DispatchItemPK, DispatchItem> currentItems = entity.getDispatchItems().stream()
				.collect(Collectors.toMap(DispatchItem::getId, Function.identity()));

		/*
		 * 5️ CACHE DE APOIO
		 * =====================================================
		 */
		Map<Long, Partner> partnerCache = new HashMap<>();
		Map<Long, Product> productCache = new HashMap<>();

		/*
		 *  6️ PROCESSA ITENS DO
		 * DTO (DIFF INTELIGENTE) ===============================
		 */
		if (dto.getDispatchItems() != null) {

			Integer sequence = 1;

			for (DispatchItemDTO itemDto : dto.getDispatchItems()) {

				Partner partner = partnerCache.computeIfAbsent(itemDto.getPartnerId(),
						id -> partnerRepository.findById(id)
								.orElseThrow(() -> new ResourceNotFoundException("Parceiro não encontrado: " + id)));

				Product product = productCache.computeIfAbsent(itemDto.getProductCode(),
						id -> productRepository.findByProductCode(id)
								.orElseThrow(() -> new ResourceNotFoundException("Product não encontrado: " + id)));

				// 🔑 PK COMPLETA
				DispatchItemPK pk = new DispatchItemPK(entity, partner, product);
				pk.setItemSequence(sequence);

				DispatchItem item = currentItems.remove(pk);

				if (item == null) {
					// ➕ NOVO ITEM
					item = new DispatchItem();
					item.setId(pk);
					copyItemDtoToEntity(itemDto, item, entity, partner, product, sequence);
					entity.getDispatchItems().add(item);
				} else {
					// ✏️ UPDATE DE ITEM EXISTENTE
					copyItemDtoToEntity(itemDto, item, entity, partner, product, sequence);
				}

				sequence++;
			}
		}

		/*
		 * 7️ REMOVE ORPHANS
		 * (SEGURANÇA TOTAL) ================================
		 */
		for (DispatchItem orphan : currentItems.values()) {
			entity.getDispatchItems().remove(orphan);
		}

		/*
		 *  8️ SALVA (CASCADE +
		 * ORPHAN OK) =====================================================
		 */
		entity = dispatchRepository.save(entity);

		/*
		 * 9️ ATUALIZA ESTOQUE
		 * =====================================================
		 */

		return new DispatchDTO(entity);
	}
	//DELETE
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long numTicket) {
		if (!dispatchRepository.existsByNumTicket(numTicket)) {
			throw new ResourceNotFoundException("Ticket not found " + numTicket);
		}
		try {
			dispatchRepository.deleteByNumTicket(numTicket);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	// --- MÉTODOS AUXILIARES ---

	private void copyDtoToEntity(DispatchDTO dto, Dispatch entity) {
		entity.setNumTicket(Long.valueOf(dto.getNumTicket()));
		entity.setDateTicket(dto.getDateTicket());
		entity.setNumberPlate(dto.getNumberPlate().toUpperCase());
		entity.setNetWeight(dto.getNetWeight());
	}

	private void copyItemDtoToEntity(DispatchItemDTO itemDto, DispatchItem itemEntity, Dispatch dispatch,
			Partner partner, Product product, Integer itemSequence) {

		// --- 1. SETA OS COMPONENTES DA CHAVE COMPOSTA (PK) ---
		itemEntity.getId().setDispatch(dispatch);
		itemEntity.getId().setPartner(partner);
		itemEntity.getId().setProduct(product);
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
			TypeTransactionOutGoing typeDispatch = TypeTransactionOutGoing.fromDescription(itemDto.getTypeDispatch());
			itemEntity.setTypeDispatch(typeDispatch);
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de expedição inválida: " + itemDto.getTypeDispatch());
		}

		try {
			AluminumAlloy alloy = AluminumAlloy.fromDescription(itemDto.getAlloy());
			itemEntity.setAlloy(alloy);
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de liga inválida: " + itemDto.getAlloy());
		}

		try {
			AluminumAlloyPol alloyPol = AluminumAlloyPol.valueOf(itemDto.getAlloyPol());
			itemEntity.setAlloyPol(alloyPol);
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de polegada inválida: " + itemDto.getAlloyPol());
		}

		try {
			AluminumAlloyFootage alloyFootage = AluminumAlloyFootage.valueOf(itemDto.getAlloyFootage());
			itemEntity.setAlloyFootage(alloyFootage);
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de metragem inválida: " + itemDto.getAlloyFootage());
		}

	}

	private BigDecimal calculateTotalItemQuantity(DispatchDTO dto) {
		if (dto.getDispatchItems() == null)
			return BigDecimal.ZERO;

		return dto.getDispatchItems().stream().map(i -> Optional.ofNullable(i.getQuantity()).orElse(BigDecimal.ZERO))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	// REPORT
	@Transactional(readOnly = true)
	public Page<DispatchReportDTO> findDetails(
			Long dispatchId, 
			Long numTicketId, 
			LocalDate startDate,
			LocalDate endDate, 
			Long partnerId, 
			String productDescription, 
			Long productCode, 
			Pageable pageable) {

		Page<DispatchReportProjection> page = dispatchRepository.reportDispatch(
				numTicketId, 
				startDate, 
				endDate,
				pageable);

		List<Long> dispatchIds = page.stream().map(DispatchReportProjection::getDispatchId).toList();

		Map<Long, List<DispatchItemReportDTO>> itemsMap = dispatchItemRepository
				.findItemsByDispatchIds(dispatchIds, 
						numTicketId, 
						startDate, 
						endDate, 
						partnerId, 
						productDescription,
						productCode)
				.stream().map(p -> new DispatchItemReportDTO(p))
				.collect(Collectors.groupingBy(DispatchItemReportDTO::getDispatchId));

		return page.map(p -> new DispatchReportDTO(p.getDispatchId(), p.getNumTicketId(), p.getDateTicket(),
				p.getNumberPlate(), p.getNetWeight(), itemsMap.getOrDefault(p.getDispatchId(), List.of())));
	}

}
