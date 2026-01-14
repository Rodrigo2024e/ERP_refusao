package com.smartprocessrefusao.erprefusao.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.InventoryDTO;
import com.smartprocessrefusao.erprefusao.entities.Inventory;
import com.smartprocessrefusao.erprefusao.entities.InventoryItem;
import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.Receipt;
import com.smartprocessrefusao.erprefusao.entities.ReceiptItem;
import com.smartprocessrefusao.erprefusao.entities.MaterialStockBalance;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionReceipt;
import com.smartprocessrefusao.erprefusao.projections.InventoryReportProjection;
import com.smartprocessrefusao.erprefusao.repositories.InventoryRepository;
import com.smartprocessrefusao.erprefusao.repositories.MaterialRepository;
import com.smartprocessrefusao.erprefusao.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.repositories.MaterialStockBalanceRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private MaterialRepository materialRepository;

	@Autowired
	private PartnerRepository partnerRepository;

	@Autowired
	private MaterialStockBalanceRepository stockBalanceRepository;

	private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

	@Transactional(readOnly = true)
	public List<InventoryDTO> findAll() {
		return inventoryRepository.findAll().stream().map(InventoryDTO::new).toList();
	}

	@Transactional(readOnly = true)
	public InventoryDTO findById(Long id) {
		Inventory entity = inventoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Inventory not found: " + id));
		return new InventoryDTO(entity);
	}

	@Transactional
	public InventoryDTO insert(InventoryDTO dto) {
		Inventory entity = new Inventory();
		copyDtoToEntity(dto, entity);
		entity = inventoryRepository.save(entity);
		updateStockBalance(entity);
		return new InventoryDTO(entity);
	}

	@Transactional
	public InventoryDTO update(Long id, InventoryDTO dto) {
		try {
			Inventory entity = inventoryRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = inventoryRepository.save(entity);
			updateStockBalance(entity);
			return new InventoryDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Inventory not found: " + id);
		}
	}

	@Transactional
	public void delete(Long id) {
		if (!inventoryRepository.existsById(id)) {
			throw new ResourceNotFoundException("Inventory not found: " + id);
		}
		try {
			inventoryRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	// ======================================================
	// INTEGRAÇÃO COM RECEBIMENTO (RECEIPT)
	// ======================================================

	/**
	 * Cria automaticamente um registro no inventário a partir de um recebimento.
	 */
	@Transactional
	public void insertFromReceipt(Receipt receipt) {
		logger.info("Gerando movimentação de inventário a partir do Receipt ID: {}", receipt.getId());

		Inventory inventory = new Inventory();
		inventory.setDateInventory(receipt.getDateTicket());
		inventory.setReceipt(receipt);
		inventory.setDescription("Entrada gerada automaticamente do Ticket nº " + receipt.getNumTicket());
		inventory.setObservation("Recebimento automático");

		BigDecimal totalValue = BigDecimal.ZERO;

		// Mapeia itens do recebimento
		for (ReceiptItem item : receipt.getReceiptItems()) {
			InventoryItem invItem = new InventoryItem();
			invItem.setInventory(inventory);
			invItem.setTypeReceipt(item.getTypeReceipt());
			invItem.setItemSequence(item.getId().getItemSequence());
			invItem.setPartner(item.getId().getPartner());
			invItem.setMaterial(item.getId().getMaterial());
			invItem.setRecoveryYield(calcRecoveryYield(item.getQuantityMco(), item.getQuantity()));
			invItem.setQuantity(item.getQuantity());
			invItem.setPrice(item.getPrice());
			invItem.setTotalValue(item.getTotalValue());
			invItem.setQuantityMco(item.getQuantityMco());
			inventory.getItems().add(invItem);

			totalValue = totalValue.add(item.getTotalValue());
		}

		inventoryRepository.save(inventory);
		updateStockBalance(inventory);
	}

	/**
	 * Atualiza a movimentação de inventário associada a um recebimento.
	 */
	@Transactional
	public void updateFromReceipt(Receipt receipt) {
		logger.info("Atualizando movimentação de inventário do Receipt ID: {}", receipt.getId());

		Inventory inventory = inventoryRepository.findByReceipt(receipt).orElseThrow(
				() -> new ResourceNotFoundException("Inventory entry not found for Receipt ID: " + receipt.getId()));

		// 1️⃣ Reverte o saldo existente ANTES de recalcular
		rollbackStockBalance(inventory);

		// 2️⃣ Limpa itens anteriores
		inventory.getItems().clear();

		BigDecimal totalValue = BigDecimal.ZERO;

		for (ReceiptItem item : receipt.getReceiptItems()) {
			InventoryItem invItem = new InventoryItem();
			invItem.setInventory(inventory);
			invItem.setTypeReceipt(item.getTypeReceipt());
			invItem.setItemSequence(item.getId().getItemSequence());
			invItem.setPartner(item.getId().getPartner());
			invItem.setMaterial(item.getId().getMaterial());
			invItem.setRecoveryYield(calcRecoveryYield(item.getQuantityMco(), item.getQuantity()));
			invItem.setQuantity(item.getQuantity());
			invItem.setPrice(item.getPrice());
			invItem.setTotalValue(item.getTotalValue());
			invItem.setQuantityMco(item.getQuantityMco());
			inventory.getItems().add(invItem);

			totalValue = totalValue.add(item.getTotalValue());
		}

		inventoryRepository.save(inventory);

		updateStockBalance(inventory);

	}

	/**
	 * Exclui a movimentação de inventário vinculada a um recebimento.
	 */
	@Transactional
	public void deleteByReceipt(Receipt receipt) {
		logger.info("Removendo movimentação de inventário do Receipt ID: {}", receipt.getId());

		Optional<Inventory> optInventory = inventoryRepository.findByReceipt(receipt);

		if (optInventory.isPresent()) {
			Inventory inventory = optInventory.get();
			rollbackStockBalance(inventory);
			inventoryRepository.delete(inventory);
		} else {
			throw new ResourceNotFoundException("Inventory entry not found for Receipt ID: " + receipt.getId());
		}
	}

	// ======================================================
	// MÉTODOS AUXILIARES
	// ======================================================

	private void copyDtoToEntity(InventoryDTO dto, Inventory entity) {
		entity.setDateInventory(dto.getDateInventory());
		entity.setDescription(dto.getDescription());
		entity.setObservation(dto.getObservation());

		entity.getItems().clear();
		dto.getItems().forEach(itemDto -> {
			InventoryItem item = new InventoryItem();
			item.setInventory(entity);
			item.setTypeReceipt(TypeTransactionReceipt.valueOf(item.toString()));
			item.setItemSequence(itemDto.getItemSequence());
			item.setRecoveryYield(itemDto.getRecoveryYield());
			item.setQuantity(itemDto.getQuantity());
			item.setPrice(itemDto.getPrice());
			item.setTotalValue(itemDto.getTotalValue());
			item.setQuantityMco(itemDto.getQuantityMco());

			Material material = materialRepository.findById(itemDto.getMaterial().getMaterialCode()).orElseThrow(
					() -> new ResourceNotFoundException("Material not found: " + itemDto.getMaterial().getMaterialCode()));

			item.setMaterial(material);

			Partner partner = partnerRepository.findById(itemDto.getPartner().getId()).orElseThrow(
					() -> new ResourceNotFoundException("Material not found: " + itemDto.getMaterial().getMaterialCode()));

			item.setPartner(partner);

			entity.getItems().add(item);
		});
	}

	/**
	 * Atualiza o saldo de estoque conforme o tipo de recebimento.
	 */
	private void updateStockBalance(Inventory entity) {

		for (InventoryItem item : entity.getItems()) {

			Material material = item.getMaterial();
			MaterialStockBalance stock = material.getStockBalance();

			if (stock == null) {
				stock = new MaterialStockBalance();
				stock.setMaterials(new ArrayList<>());
				stock.getMaterials().add(material);
				material.setStockBalance(stock);
			}

			BigDecimal qty = item.getQuantity() != null ? item.getQuantity() : BigDecimal.ZERO;

			BigDecimal val = item.getTotalValue() != null ? item.getTotalValue() : BigDecimal.ZERO;

			// ================
			// BLOQUEIA ESTOQUE NEGATIVO
			// ================
			boolean isExit = item.getTypeReceipt() == TypeTransactionReceipt.ADJUSTMENT_EXIT
					|| item.getTypeReceipt() == TypeTransactionReceipt.SALES_SCRAP;

			if (isExit) {
				BigDecimal finalBalance = stock.getFinalBalance() != null ? stock.getFinalBalance() : BigDecimal.ZERO;

				if (finalBalance.subtract(qty).compareTo(BigDecimal.ZERO) < 0) {
					throw new IllegalArgumentException("Saldo insuficiente para o material " + material.getMaterialCode()
							+ ". Saldo atual: " + finalBalance + " | Saída: " + qty);
				}
			}

			// ==================================
			// APLICA A MOVIMENTAÇÃO TYPE_RECEIPT
			// ==================================
			switch (item.getTypeReceipt()) {

			case PURCHASE:
				// Total PURCHASE QTY
				stock.setTotalPurchase(stock.getTotalPurchase().add(qty));

				// Total PURCHASE MCO
				BigDecimal mco = item.getQuantityMco() != null ? item.getQuantityMco() : BigDecimal.ZERO;
				stock.setTotalPurchaseMco(stock.getTotalPurchaseMco().add(mco));

				// Recovery Yield PURCHASE
				BigDecimal ry = calcRecoveryYield(stock.getTotalPurchaseMco(), stock.getTotalPurchase());
				stock.setRecoveryYieldPurchase(ry);

				// TotalValue
				stock.setTotalValue(stock.getTotalValue().add(val));

				// Average Cost
				BigDecimal aPrice = calcAveragePrice(stock.getTotalValue(), stock.getTotalPurchase());
				stock.setAveragePrice(aPrice);

				// Average Cost Mco
				BigDecimal aPriceMco = calcAveragePrice(stock.getTotalValue(), stock.getTotalPurchaseMco());
				stock.setAveragePriceMco(aPriceMco);
				break;

			case SENT_FOR_PROCESSING:
				// Total SENT_FOR_PROCESSING QTY
				stock.setTotalSentForProcessing(stock.getTotalSentForProcessing().add(qty));

				// Total SENT_FOR_PROCESSING MCO
				BigDecimal processingMco = item.getQuantityMco() != null ? item.getQuantityMco() : BigDecimal.ZERO;
				stock.setTotalSentForProcessingMco(stock.getTotalSentForProcessingMco().add(processingMco));

				// Recovery Yield SENT_FOR_PROCESSING
				BigDecimal processingRy = calcRecoveryYield(stock.getTotalSentForProcessingMco(),
						stock.getTotalSentForProcessing());
				stock.setRecoveryYieldSentForProcessing(processingRy);
				break;

			case SCRAP_SALES_RETURN:
				// Total SCRAP_SALES_RETURN QTY
				stock.setTotalScrapSalesReturn(stock.getTotalScrapSalesReturn().add(qty));

				// Total SCRAP_SALES_RETURN MCO
				BigDecimal scrapSalesReturnMco = item.getQuantityMco() != null ? item.getQuantityMco()
						: BigDecimal.ZERO;
				stock.setTotalScrapSalesReturnMco(stock.getTotalScrapSalesReturnMco().add(scrapSalesReturnMco));

				// Recovery Yield SCRAP_SALES_RETURN
				BigDecimal scrapSalesReturnRy = calcRecoveryYield(stock.getTotalScrapSalesReturnMco(),
						stock.getTotalScrapSalesReturn());
				stock.setRecoveryYieldScrapSalesReturn(scrapSalesReturnRy);
				break;

			case ADJUSTMENT_ENTRY:
				// Total ADJUSTMENT_ENTRY QTY
				stock.setTotalAdjustmentEntries(stock.getTotalAdjustmentEntries().add(qty));

				// Total ADJUSTMENT_ENTRY MCO
				BigDecimal ajustmentEntryMco = item.getQuantityMco() != null ? item.getQuantityMco() : BigDecimal.ZERO;
				stock.setTotalAdjustmentEntriesMco(stock.getTotalAdjustmentEntriesMco().add(ajustmentEntryMco));

				// Recovey Yield ADJUSTMENT_ENTRY
				BigDecimal ajustmentEntryRy = calcRecoveryYield(stock.getTotalAdjustmentEntriesMco(),
						stock.getTotalAdjustmentEntries());
				stock.setRecoveryYieldAdjustmentEntries(ajustmentEntryRy);
				break;

			case SALES_SCRAP:
				// Total SALES_SCRAP QTY
				stock.setTotalSalesScrap(stock.getTotalSalesScrap().add(qty));

				// Total SALES_SCRAP MCO
				BigDecimal salesScrapMco = item.getQuantityMco() != null ? item.getQuantityMco() : BigDecimal.ZERO;
				stock.setTotalSalesScrapMco(stock.getTotalSalesScrapMco().add(salesScrapMco));

				// Recovey Yield SALES_SCRAP
				BigDecimal salesScrapRy = calcRecoveryYield(stock.getTotalSalesScrapMco(), stock.getTotalSalesScrap());
				stock.setRecoveryYieldSalesScrap(salesScrapRy);
				break;

			case ADJUSTMENT_EXIT:
				// Total ADJUSTMENT_EXIT QTY
				stock.setTotalAdjustmentExit(stock.getTotalAdjustmentExit().add(qty));

				// Total ADJUSTMENT_EXIT MCO
				BigDecimal ajustmentExitMco = item.getQuantityMco() != null ? item.getQuantityMco() : BigDecimal.ZERO;
				stock.setTotalAdjustmentExitMco(stock.getTotalAdjustmentExitMco().add(ajustmentExitMco));

				// Recovey Yield ADJUSTMENT_EXIT
				BigDecimal ajustmentExitRy = calcRecoveryYield(stock.getTotalAdjustmentExitMco(),
						stock.getTotalAdjustmentExit());
				stock.setRecoveryYieldAdjustmentExit(ajustmentExitRy);
				break;

			default:
				break;

			}

			// Recalcula saldo final depois da movimentação
			stock.updateFinalBalance();

			stockBalanceRepository.save(stock);
		}
	}

	/**
	 * Reverte os saldos de estoque ao excluir movimentações.
	 */
	private void rollbackStockBalance(Inventory entity) {

		for (InventoryItem item : entity.getItems()) {

			Material material = item.getMaterial();
			MaterialStockBalance stock = material.getStockBalance();

			if (stock == null)
				continue;

			BigDecimal qty = item.getQuantity() != null ? item.getQuantity() : BigDecimal.ZERO;

			BigDecimal val = item.getTotalValue() != null ? item.getTotalValue() : BigDecimal.ZERO;

			switch (item.getTypeReceipt()) {

			case PURCHASE:
				// Total PURCHASE QTY
				stock.setTotalPurchase(stock.getTotalPurchase().add(qty));

				// Total PURCHASE MCO
				BigDecimal mco = item.getQuantityMco() != null ? item.getQuantityMco() : BigDecimal.ZERO;
				stock.setTotalPurchaseMco(stock.getTotalPurchaseMco().add(mco));

				// Recovery Yield PURCHASE
				BigDecimal ry = calcRecoveryYield(stock.getTotalPurchaseMco(), stock.getTotalPurchase());
				stock.setRecoveryYieldPurchase(ry);

				// TotalValue
				stock.setTotalValue(stock.getTotalValue().add(val));

				// Average Cost
				BigDecimal aPrice = calcAveragePrice(stock.getTotalValue(), stock.getTotalPurchase());
				stock.setAveragePrice(aPrice);

				// Average Cost Mco
				BigDecimal aPriceMco = calcAveragePrice(stock.getTotalValue(), stock.getTotalPurchaseMco());
				stock.setAveragePriceMco(aPriceMco);
				break;

			case SENT_FOR_PROCESSING:
				// Total SENT_FOR_PROCESSING QTY
				stock.setTotalSentForProcessing(stock.getTotalSentForProcessing().add(qty));

				// Total SENT_FOR_PROCESSING MCO
				BigDecimal processingMco = item.getQuantityMco() != null ? item.getQuantityMco() : BigDecimal.ZERO;
				stock.setTotalSentForProcessingMco(stock.getTotalSentForProcessingMco().add(processingMco));

				// Recovery Yield SENT_FOR_PROCESSING
				BigDecimal processingRy = calcRecoveryYield(stock.getTotalSentForProcessingMco(),
						stock.getTotalSentForProcessing());
				stock.setRecoveryYieldSentForProcessing(processingRy);
				break;

			case SCRAP_SALES_RETURN:
				// Total SCRAP_SALES_RETURN QTY
				stock.setTotalScrapSalesReturn(stock.getTotalScrapSalesReturn().add(qty));

				// Total SCRAP_SALES_RETURN MCO
				BigDecimal scrapSalesReturnMco = item.getQuantityMco() != null ? item.getQuantityMco()
						: BigDecimal.ZERO;
				stock.setTotalScrapSalesReturnMco(stock.getTotalScrapSalesReturnMco().add(scrapSalesReturnMco));

				// Recovery Yield SCRAP_SALES_RETURN
				BigDecimal scrapSalesReturnRy = calcRecoveryYield(stock.getTotalScrapSalesReturnMco(),
						stock.getTotalScrapSalesReturn());
				stock.setRecoveryYieldScrapSalesReturn(scrapSalesReturnRy);
				break;

			case ADJUSTMENT_ENTRY:
				// Total ADJUSTMENT_ENTRY QTY
				stock.setTotalAdjustmentEntries(stock.getTotalAdjustmentEntries().add(qty));

				// Total ADJUSTMENT_ENTRY MCO
				BigDecimal ajustmentEntryMco = item.getQuantityMco() != null ? item.getQuantityMco() : BigDecimal.ZERO;
				stock.setTotalAdjustmentEntriesMco(stock.getTotalAdjustmentEntriesMco().add(ajustmentEntryMco));

				// Recovey Yield ADJUSTMENT_ENTRY
				BigDecimal ajustmentEntryRy = calcRecoveryYield(stock.getTotalAdjustmentEntriesMco(),
						stock.getTotalAdjustmentEntries());
				stock.setRecoveryYieldAdjustmentEntries(ajustmentEntryRy);
				break;

			case SALES_SCRAP:
				// Total SALES_SCRAP QTY
				stock.setTotalSalesScrap(stock.getTotalSalesScrap().add(qty));

				// Total SALES_SCRAP MCO
				BigDecimal salesScrapMco = item.getQuantityMco() != null ? item.getQuantityMco() : BigDecimal.ZERO;
				stock.setTotalSalesScrapMco(stock.getTotalSalesScrapMco().add(salesScrapMco));

				// Recovey Yield SALES_SCRAP
				BigDecimal salesScrapRy = calcRecoveryYield(stock.getTotalSalesScrapMco(), stock.getTotalSalesScrap());
				stock.setRecoveryYieldSalesScrap(salesScrapRy);
				break;

			case ADJUSTMENT_EXIT:
				// Total ADJUSTMENT_EXIT QTY
				stock.setTotalAdjustmentExit(stock.getTotalAdjustmentExit().add(qty));

				// Total ADJUSTMENT_EXIT MCO
				BigDecimal ajustmentExitMco = item.getQuantityMco() != null ? item.getQuantityMco() : BigDecimal.ZERO;
				stock.setTotalAdjustmentExitMco(stock.getTotalAdjustmentExitMco().add(ajustmentExitMco));

				// Recovey Yield ADJUSTMENT_EXIT
				BigDecimal ajustmentExitRy = calcRecoveryYield(stock.getTotalAdjustmentExitMco(),
						stock.getTotalAdjustmentExit());
				stock.setRecoveryYieldAdjustmentExit(ajustmentExitRy);
				break;

			default:
				break;
			}

			// Recalcula saldo final
			stock.updateFinalBalance();
			stockBalanceRepository.save(stock);

		}
	}

	public Page<InventoryReportProjection> getReportRange(LocalDate startDate, LocalDate endDate, Long materialCode,
			Pageable pageable) {

		// --- Validação: startDate > endDate ---
		if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
			throw new IllegalArgumentException("A data inicial não pode ser maior que a final.");
		}

		// --- Validação: código inexistente ---
		if (materialCode != null) {
			boolean exists = materialRepository.existsByMaterialCode(materialCode);

			if (!exists) {
				throw new IllegalArgumentException("Nenhum material encontrado com o código: " + materialCode);
			}
		}

		// --- Executa a consulta ---
		return inventoryRepository.reportInventory(startDate, endDate, materialCode, pageable);
	}

	// Calculate Recovery Yield
	private BigDecimal calcRecoveryYield(BigDecimal mco, BigDecimal total) {
		if (mco == null)
			mco = BigDecimal.ZERO;
		if (total == null || total.compareTo(BigDecimal.ZERO) == 0) {
			return BigDecimal.ZERO; // Evita divisão por zero
		}

		return mco.divide(total, 2, RoundingMode.HALF_UP);
	}

	// Calculate Average Cosy
	private BigDecimal calcAveragePrice(BigDecimal valTotal, BigDecimal qty) {
		if (qty == null)
			qty = BigDecimal.ZERO;
		if (valTotal == null || valTotal.compareTo(BigDecimal.ZERO) == 0) {
			return BigDecimal.ZERO; // Evita divisão por zero
		}

		return valTotal.divide(qty, 2, RoundingMode.HALF_UP);
	}

}
