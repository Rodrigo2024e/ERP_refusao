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

//	@Autowired
//	private InventoryService inventoryService;

	// INSERT
	@Transactional
	public DispatchDTO insert(DispatchDTO dto) {

		Long numTicket = Optional.ofNullable(dto.getNumTicket())
				.orElseThrow(() -> new IllegalArgumentException("O número do ticket é obrigatório."));

		ticketRepository.findByNumTicket(numTicket).ifPresent(e -> {
			throw new IllegalArgumentException("O número de ticket '" + numTicket + "' já existe.");
		});

		BigDecimal totalItemsQuantity = calculateTotalItemQuantity(dto);
		if (dto.getNetWeight() != null && totalItemsQuantity.compareTo(dto.getNetWeight()) > 0) {
			throw new IllegalArgumentException("A soma das quantidades dos itens (" + totalItemsQuantity
					+ ") não pode ultrapassar o Peso Líquido (" + dto.getNetWeight() + ").");
		}

		Dispatch entity = new Dispatch();
		copyDtoToEntity(dto, entity);
		entity = dispatchRepository.save(entity);

		entity.getDispatchItems().clear();

		if (dto.getDispatchItems() != null) {

			int sequence = 1;
			Map<Long, Partner> partnerCache = new HashMap<>();
			Map<Long, Product> productCache = new HashMap<>();

			for (DispatchItemDTO itemDto : dto.getDispatchItems()) {

				Partner partner = partnerCache.computeIfAbsent(itemDto.getPartnerId(),
						id -> partnerRepository.findById(id)
								.orElseThrow(() -> new ResourceNotFoundException("Partner não encontrado: " + id)));

				Product product = productCache.computeIfAbsent(itemDto.getProductCode(),
						id -> productRepository.findByProductCode(id)
								.orElseThrow(() -> new ResourceNotFoundException("Product não encontrado: " + id)));

				DispatchItem item = new DispatchItem();
				item.setDispatch(entity);
				item.setPartner(partner);
				item.setProduct(product);
				item.setItemSequence(sequence++);

				copyItemDtoToEntity(itemDto, item);
				entity.getDispatchItems().add(item);
			}
		}

//        inventoryService.insertFromDispatch(entity);

		return new DispatchDTO(entity);
	}

	// UPDATE
	@Transactional
	public DispatchDTO updateByNumTicket(Long numTicket, DispatchDTO dto) {

		Dispatch entity = dispatchRepository.findByNumTicket(numTicket).orElseThrow(
				() -> new ResourceNotFoundException("Dispatch não encontrado para atualização: " + numTicket));

		Long newNumTicket = Optional.ofNullable(dto.getNumTicket())
				.orElseThrow(() -> new IllegalArgumentException("O número do ticket é obrigatório."));

		final Long entityId = entity.getId();

		ticketRepository.findByNumTicket(newNumTicket).ifPresent(existing -> {
			if (!existing.getId().equals(entityId)) {
				throw new IllegalArgumentException("O número de ticket '" + newNumTicket + "' já está em uso.");
			}
		});

		BigDecimal totalItemsQuantity = calculateTotalItemQuantity(dto);
		if (dto.getNetWeight() != null && totalItemsQuantity.compareTo(dto.getNetWeight()) > 0) {
			throw new IllegalArgumentException("A soma das quantidades dos itens (" + totalItemsQuantity
					+ ") não pode ultrapassar o Peso Líquido (" + dto.getNetWeight() + ").");
		}

		copyDtoToEntity(dto, entity);

		// 🔑 MAPA POR PK TÉCNICA
		Map<Long, DispatchItem> currentItems = entity.getDispatchItems().stream()
				.collect(Collectors.toMap(DispatchItem::getId, Function.identity()));

		Map<Long, Partner> partnerCache = new HashMap<>();
		Map<Long, Product> productCache = new HashMap<>();

		entity.getDispatchItems().clear();

		if (dto.getDispatchItems() != null) {

			int sequence = 1;

			for (DispatchItemDTO itemDto : dto.getDispatchItems()) {

				DispatchItem item;

				if (itemDto.getDispatchId() != null && currentItems.containsKey(itemDto.getDispatchId())) {
					// ✏️ UPDATE
					item = currentItems.remove(itemDto.getDispatchId());
				} else {
					// ➕ INSERT
					item = new DispatchItem();
					item.setDispatch(entity);
				}

				Partner partner = partnerCache.computeIfAbsent(itemDto.getPartnerId(),
						id -> partnerRepository.findById(id)
								.orElseThrow(() -> new ResourceNotFoundException("Partner não encontrado: " + id)));

				Product product = productCache.computeIfAbsent(itemDto.getProductCode(),
						id -> productRepository.findByProductCode(id)
								.orElseThrow(() -> new ResourceNotFoundException("Product não encontrado: " + id)));

				item.setPartner(partner);
				item.setProduct(product);
				item.setItemSequence(sequence++);

				copyItemDtoToEntity(itemDto, item);
				entity.getDispatchItems().add(item);
			}
		}

		// 🧹 orphanRemoval resolve automaticamente

		entity = dispatchRepository.save(entity);
//        inventoryService.updateFromDispatch(entity);

		return new DispatchDTO(entity);
	}

	// DELETE
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long numTicket) {

		if (!dispatchRepository.existsByNumTicket(numTicket)) {
			throw new ResourceNotFoundException("Ticket não encontrado: " + numTicket);
		}

		try {
			dispatchRepository.deleteByNumTicket(numTicket);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de integridade.");
		}
	}

	// AUXILIARES
	private void copyDtoToEntity(DispatchDTO dto, Dispatch entity) {
		entity.setNumTicket(dto.getNumTicket());
		entity.setDateTicket(dto.getDateTicket());
		entity.setNumberPlate(dto.getNumberPlate().toUpperCase());
		entity.setNetWeight(dto.getNetWeight());
	}

	private void copyItemDtoToEntity(DispatchItemDTO itemDto, DispatchItem entity) {

		entity.setDocumentNumber(itemDto.getDocumentNumber());
		entity.setQuantity(itemDto.getQuantity());
		entity.setPrice(itemDto.getPrice());

		if (itemDto.getQuantity() != null && itemDto.getPrice() != null) {
			entity.setTotalValue(itemDto.getQuantity().multiply(itemDto.getPrice()));
		} else {
			entity.setTotalValue(BigDecimal.ZERO);
		}

		entity.setObservation(itemDto.getObservation());

		entity.setTypeDispatch(TypeTransactionOutGoing.fromDescription(itemDto.getTypeDispatch()));
		entity.setAlloy(AluminumAlloy.fromDescription(itemDto.getAlloy()));
		entity.setAlloyPol(AluminumAlloyPol.valueOf(itemDto.getAlloyPol()));
		entity.setAlloyFootage(AluminumAlloyFootage.valueOf(itemDto.getAlloyFootage()));

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
			Long id, 
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
		List<Long> dispatchIds = page.stream().map(DispatchReportProjection::getId).toList();
		Map<Long, List<DispatchItemReportDTO>> itemsMap = dispatchItemRepository
				.findItemsByDispatchIds(
						dispatchIds, 
						numTicketId, 
						startDate, 
						endDate, 
						partnerId, 
						productDescription,
						productCode)
				.stream().map(p -> new DispatchItemReportDTO(p))
				.collect(Collectors.groupingBy(DispatchItemReportDTO::getDispatchId));
		return page.map(p -> new DispatchReportDTO(
				p.getId(),
				p.getNumTicketId(), 
				p.getDateTicket(),
				p.getNumberPlate(), 
				p.getNetWeight(), itemsMap.getOrDefault(p.getId(), List.of())));
	}
}
