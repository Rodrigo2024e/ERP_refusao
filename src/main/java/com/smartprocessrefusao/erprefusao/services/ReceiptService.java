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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.ReceiptDTO;
import com.smartprocessrefusao.erprefusao.dto.ReceiptItemDTO;
import com.smartprocessrefusao.erprefusao.dto.ReceiptItemReportDTO;
import com.smartprocessrefusao.erprefusao.dto.ReceiptReportDTO;
import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.Receipt;
import com.smartprocessrefusao.erprefusao.entities.ReceiptItem;
import com.smartprocessrefusao.erprefusao.enumerados.TypeCosts;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionReceipt;
import com.smartprocessrefusao.erprefusao.projections.ReceiptReportProjection;
import com.smartprocessrefusao.erprefusao.repositories.MaterialRepository;
import com.smartprocessrefusao.erprefusao.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.repositories.ReceiptItemRepository;
import com.smartprocessrefusao.erprefusao.repositories.ReceiptRepository;
import com.smartprocessrefusao.erprefusao.repositories.TicketRepository;
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
    // =====================================================
    // INSERT
    // =====================================================
    @Transactional
    public ReceiptDTO insert(ReceiptDTO dto) {

        Long numTicket = Optional.ofNullable(dto.getNumTicket())
                .orElseThrow(() -> new IllegalArgumentException("O número do ticket é obrigatório."));

        ticketRepository.findByNumTicket(numTicket).ifPresent(e -> {
            throw new IllegalArgumentException("O número de ticket '" + numTicket + "' já existe.");
        });

        BigDecimal totalItemsQuantity = calculateTotalItemQuantity(dto);
        if (dto.getNetWeight() != null &&
                totalItemsQuantity.compareTo(dto.getNetWeight()) > 0) {
            throw new IllegalArgumentException(
                    "A soma das quantidades dos itens (" + totalItemsQuantity +
                            ") não pode ultrapassar o Peso Líquido (" + dto.getNetWeight() + ").");
        }

        Receipt entity = new Receipt();
        copyDtoToEntity(dto, entity);
        entity = receiptRepository.save(entity);

        entity.getReceiptItems().clear();

        if (dto.getReceiptItems() != null) {

            int sequence = 1;
            Map<Long, Partner> partnerCache = new HashMap<>();
            Map<Long, Material> materialCache = new HashMap<>();

            for (ReceiptItemDTO itemDto : dto.getReceiptItems()) {

                Partner partner = partnerCache.computeIfAbsent(
                        itemDto.getPartnerId(),
                        id -> partnerRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Partner não encontrado: " + id))
                );

                Material material = materialCache.computeIfAbsent(
                        itemDto.getMaterialCode(),
                        id -> materialRepository.findByMaterialCode(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Material não encontrado: " + id))
                );

                ReceiptItem item = new ReceiptItem();
                item.setReceipt(entity);
                item.setPartner(partner);
                item.setMaterial(material);
                item.setItemSequence(sequence++);

                copyItemDtoToEntity(itemDto, item);
                entity.getReceiptItems().add(item);
            }
        }
        inventoryService.insertFromReceipt(entity);
        return new ReceiptDTO(entity);
    }
    // =====================================================
    // UPDATE
    // =====================================================
    @Transactional
    public ReceiptDTO updateByNumTicket(Long numTicket, ReceiptDTO dto) {

        Receipt entity = receiptRepository.findByNumTicket(numTicket)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Receipt não encontrado para atualização: " + numTicket));

        Long newNumTicket = Optional.ofNullable(dto.getNumTicket())
                .orElseThrow(() -> new IllegalArgumentException("O número do ticket é obrigatório."));

        final Long entityId = entity.getId();
        
        ticketRepository.findByNumTicket(newNumTicket).ifPresent(existing -> {
            if (!existing.getId().equals(entityId)) {
                throw new IllegalArgumentException(
                        "O número de ticket '" + newNumTicket + "' já está em uso.");
            }
        });

        BigDecimal totalItemsQuantity = calculateTotalItemQuantity(dto);
        if (dto.getNetWeight() != null &&
                totalItemsQuantity.compareTo(dto.getNetWeight()) > 0) {
            throw new IllegalArgumentException(
                    "A soma das quantidades dos itens (" + totalItemsQuantity +
                            ") não pode ultrapassar o Peso Líquido (" + dto.getNetWeight() + ").");
        }

        copyDtoToEntity(dto, entity);

        // 🔑 MAPA POR PK TÉCNICA
        Map<Long, ReceiptItem> currentItems = entity.getReceiptItems().stream()
                .collect(Collectors.toMap(ReceiptItem::getId, Function.identity()));

        Map<Long, Partner> partnerCache = new HashMap<>();
        Map<Long, Material> materialCache = new HashMap<>();

        entity.getReceiptItems().clear();

        if (dto.getReceiptItems() != null) {

            int sequence = 1;

            for (ReceiptItemDTO itemDto : dto.getReceiptItems()) {

                ReceiptItem item;

                if (itemDto.getReceiptId() != null && currentItems.containsKey(itemDto.getReceiptId())) {
                    // ✏️ UPDATE
                    item = currentItems.remove(itemDto.getReceiptId());
                } else {
                    // ➕ INSERT
                    item = new ReceiptItem();
                    item.setReceipt(entity);
                }

                Partner partner = partnerCache.computeIfAbsent(
                        itemDto.getPartnerId(),
                        id -> partnerRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Partner não encontrado: " + id))
                );

                Material material = materialCache.computeIfAbsent(
                        itemDto.getMaterialCode(),
                        id -> materialRepository.findByMaterialCode(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Material não encontrado: " + id))
                );

                item.setPartner(partner);
                item.setMaterial(material);
                item.setItemSequence(sequence++);

                copyItemDtoToEntity(itemDto, item);
                entity.getReceiptItems().add(item);
            }
        }
        // 🧹 orphanRemoval resolve automaticamente

        entity = receiptRepository.save(entity);
        inventoryService.updateFromReceipt(entity);
        return new ReceiptDTO(entity);
    }

    // =====================================================
    // DELETE
    // =====================================================
    @Transactional
    public void delete(Long numTicket) {

        Receipt receipt = receiptRepository.findByNumTicket(numTicket)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket não encontrado: " + numTicket));

        // 1️⃣ desfaz estoque
        inventoryService.deleteByReceipt(receipt);

        // 2️⃣ remove itens
        receipt.getReceiptItems().clear();

        // 3️⃣ remove receipt
        receiptRepository.delete(receipt);
    }

    // =====================================================
    // AUXILIARES
    // =====================================================
    private void copyDtoToEntity(ReceiptDTO dto, Receipt entity) {
        entity.setNumTicket(dto.getNumTicket());
        entity.setDateTicket(dto.getDateTicket());
        entity.setNumberPlate(dto.getNumberPlate().toUpperCase());
        entity.setNetWeight(dto.getNetWeight());
    }

    private void copyItemDtoToEntity(ReceiptItemDTO itemDto, ReceiptItem entity) {
        entity.setDocumentNumber(itemDto.getDocumentNumber());
        entity.setRecoveryYield(itemDto.getRecoveryYield());
        entity.setQuantity(itemDto.getQuantity());
        entity.setPrice(itemDto.getPrice());
        entity.setObservation(itemDto.getObservation());

        entity.setTypeReceipt(TypeTransactionReceipt.fromDescription(itemDto.getTypeReceipt()));
        entity.setTypeCosts(TypeCosts.fromDescription(itemDto.getTypeCosts()));

        if (itemDto.getQuantity() != null && itemDto.getPrice() != null) {
            entity.setTotalValue(itemDto.getQuantity().multiply(itemDto.getPrice()));
        } else {
            entity.setTotalValue(BigDecimal.ZERO);
        }

        if (itemDto.getQuantity() != null && itemDto.getRecoveryYield() != null) {
            entity.setQuantityMco(itemDto.getQuantity().multiply(itemDto.getRecoveryYield()));
        } else {
            entity.setQuantityMco(BigDecimal.ZERO);
        }
    }

    private BigDecimal calculateTotalItemQuantity(ReceiptDTO dto) {
        if (dto.getReceiptItems() == null) return BigDecimal.ZERO;

        return dto.getReceiptItems().stream()
                .map(i -> Optional.ofNullable(i.getQuantity()).orElse(BigDecimal.ZERO))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
	// REPORT
	@Transactional(readOnly = true)
	public Page<ReceiptReportDTO> findDetails(
			Long id, 
			Long numTicketId, 
			LocalDate startDate, 
			LocalDate endDate,
			Long partnerId, 
			String materialDescription, 
			Long materialCode, 
			Pageable pageable) {

		Page<ReceiptReportProjection> page = receiptRepository.reportReceipt(
				numTicketId, 
				startDate, 
				endDate, 
				pageable);
		List<Long> receiptIds = page.stream().map(ReceiptReportProjection::getReceiptId).toList();
		Map<Long, List<ReceiptItemReportDTO>> itemsMap = receiptItemRepository
				.findItemsByReceiptIds(receiptIds, 
						numTicketId, 
						startDate, 
						endDate, 
						partnerId, 
						materialDescription,
						materialCode)
				.stream().map(p -> new ReceiptItemReportDTO(p))
				.collect(Collectors.groupingBy(ReceiptItemReportDTO::getReceiptId));
		return page.map(p -> new ReceiptReportDTO(
				p.getReceiptId(), 
				p.getNumTicket(), 
				p.getDateTicket(),
				p.getNumberPlate(), 
				p.getNetWeight(), itemsMap.getOrDefault(p.getReceiptId(), List.of())));
	}
}
