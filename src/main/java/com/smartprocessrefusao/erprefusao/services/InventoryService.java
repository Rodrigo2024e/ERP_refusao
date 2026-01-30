package com.smartprocessrefusao.erprefusao.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.InventoryDTO;
import com.smartprocessrefusao.erprefusao.dto.InventoryItemDTO;
import com.smartprocessrefusao.erprefusao.entities.Inventory;
import com.smartprocessrefusao.erprefusao.entities.InventoryItem;
import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.entities.MaterialStockBalance;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.Receipt;
import com.smartprocessrefusao.erprefusao.entities.ReceiptItem;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionReceipt;
import com.smartprocessrefusao.erprefusao.projections.InventoryReportProjection;
import com.smartprocessrefusao.erprefusao.repositories.InventoryRepository;
import com.smartprocessrefusao.erprefusao.repositories.MaterialRepository;
import com.smartprocessrefusao.erprefusao.repositories.MaterialStockBalanceRepository;
import com.smartprocessrefusao.erprefusao.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.BusinessException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

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

	// ======================================================
	// CRUD
	// ======================================================

	@Transactional(readOnly = true)
	public List<InventoryDTO> findAll() {
		return inventoryRepository.findAll().stream()
				.map(InventoryDTO::new)
				.toList();
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

		inventoryRepository.save(entity);
		recalcStockByInventory(entity);

		return new InventoryDTO(entity);
	}

	@Transactional
	public InventoryDTO update(Long id, InventoryDTO dto) {
		Inventory entity = inventoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Inventory not found: " + id));

		deleteStockBalances(entity);

		copyDtoToEntity(dto, entity);
		inventoryRepository.save(entity);

		recalcStockByInventory(entity);
		return new InventoryDTO(entity);
	}

	@Transactional
	public void delete(Long id) {
		Inventory entity = inventoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Inventory not found: " + id));

		deleteStockBalances(entity);
		inventoryRepository.delete(entity);
	}

	// ======================================================
	// RECEIPT INTEGRATION
	// ======================================================

	@Transactional
	public void insertFromReceipt(Receipt receipt) {

		Inventory inventory = new Inventory();
		inventory.setDateInventory(receipt.getDateTicket());
		inventory.setReceipt(receipt);
		inventory.setDescription("Entrada automática do Ticket nº " + receipt.getNumTicket());
		inventory.setObservation("Recebimento automático");

		for (ReceiptItem item : receipt.getReceiptItems()) {
			inventory.getItems().add(buildInventoryItem(inventory, item));
		}

		inventoryRepository.save(inventory);
		recalcStockByInventory(inventory);
	}

	@Transactional
	public void updateFromReceipt(Receipt receipt) {

		Inventory inventory = inventoryRepository.findByReceipt(receipt)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Inventory not found for receipt: " + receipt.getId()));

		deleteStockBalances(inventory);
		inventory.getItems().clear();

		for (ReceiptItem item : receipt.getReceiptItems()) {
			inventory.getItems().add(buildInventoryItem(inventory, item));
		}

		inventoryRepository.save(inventory);
		recalcStockByInventory(inventory);
	}

	@Transactional
	public void deleteByReceipt(Receipt receipt) {

		Inventory inventory = inventoryRepository.findByReceipt(receipt)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Inventory not found for receipt: " + receipt.getId()));

		deleteStockBalances(inventory);
		inventoryRepository.delete(inventory);
	}

	// ======================================================
	// CORE — RECÁLCULO DE ESTOQUE
	// ======================================================

	private void recalcStockByInventory(Inventory inventory) {
	    // Importante: Limpar os registros deste inventário antes de recalcular 
	    // para evitar que o "lastStock" pegue uma linha antiga do próprio inventário
	    stockBalanceRepository.deleteByInventory(inventory);
	    stockBalanceRepository.flush(); 

	    for (InventoryItem item : inventory.getItems()) {
	        Material material = item.getMaterial();

	        // 1. Busca o último saldo acumulado real (ignorando o inventário atual)
	        MaterialStockBalance lastStock = stockBalanceRepository
	            .findFirstByMaterialAndInventoryNotOrderByInventoryDateInventoryDescIdDesc(material, inventory)
	            .orElse(new MaterialStockBalance());

	        MaterialStockBalance currentStock = new MaterialStockBalance();
	        currentStock.setInventory(inventory);
	        currentStock.setMaterial(material);
	        
	        // 2. Transporta o saldo anterior
	        currentStock.setPreviousBalance(lastStock.getFinalBalance());
	        currentStock.setPreviousBalanceMco(lastStock.getFinalBalanceMco());

	        // 3. Aplica a movimentação atual e calcula o final
	        applyMovement(currentStock, item);
	        currentStock.updateFinalBalance();

	        // 4. VALIDAÇÃO DE ESTOQUE NEGATIVO
	        if (currentStock.getFinalBalance().compareTo(BigDecimal.ZERO) < 0) {
	            throw new BusinessException("Saldo insuficiente para o material: " 
	                + material.getDescription() + ". Saldo atual: " + lastStock.getFinalBalance() 
	                + ", Tentativa de saída: " + item.getQuantity());
	        }

	        stockBalanceRepository.save(currentStock);
	    }
	}

	private void deleteStockBalances(Inventory inventory) {
		stockBalanceRepository.deleteByInventory(inventory);
	}

	private void applyMovement(MaterialStockBalance stock, InventoryItem item) {

		BigDecimal qty = defaultZero(item.getQuantity());
		BigDecimal mco = defaultZero(item.getQuantityMco());
		BigDecimal val = defaultZero(item.getTotalValue());

		switch (item.getTypeReceipt()) {

		case PURCHASE -> {
			stock.setTotalPurchase(qty);
			stock.setTotalPurchaseMco(mco);
			stock.setTotalValue(val);
			stock.setRecoveryYieldPurchase(calcRecoveryYield(mco, qty));
			stock.setAveragePrice(calcAveragePrice(val, qty));
			stock.setAveragePriceMco(calcAveragePrice(val, mco));
		}

		case SENT_FOR_PROCESSING -> {
			stock.setTotalSentForProcessing(qty);
			stock.setTotalSentForProcessingMco(mco);
			stock.setRecoveryYieldSentForProcessing(calcRecoveryYield(mco, qty));
		}

		case SALES_SCRAP -> {
			stock.setTotalSalesScrap(qty);
			stock.setTotalSalesScrapMco(mco);
			stock.setRecoveryYieldSalesScrap(calcRecoveryYield(mco, qty));
		}

		case SCRAP_SALES_RETURN -> {
			stock.setTotalScrapSalesReturn(qty);
			stock.setTotalScrapSalesReturnMco(mco);
			stock.setRecoveryYieldScrapSalesReturn(calcRecoveryYield(mco, qty));
		}

		case ADJUSTMENT_ENTRY -> {
			stock.setTotalAdjustmentEntries(qty);
			stock.setTotalAdjustmentEntriesMco(mco);
			stock.setRecoveryYieldAdjustmentEntries(calcRecoveryYield(mco, qty));
		}

		case ADJUSTMENT_EXIT -> {
			stock.setTotalAdjustmentExit(qty);
			stock.setTotalAdjustmentExitMco(mco);
			stock.setRecoveryYieldAdjustmentExit(calcRecoveryYield(mco, qty));
		}
		}
	}

	// ======================================================
	// AUXILIARES
	// ======================================================

	private InventoryItem buildInventoryItem(Inventory inventory, ReceiptItem item) {

		InventoryItem invItem = new InventoryItem();
		invItem.setInventory(inventory);
		invItem.setTypeReceipt(item.getTypeReceipt());
		invItem.setItemSequence(item.getItemSequence());
		invItem.setPartner(item.getPartner());
		invItem.setMaterial(item.getMaterial());
		invItem.setQuantity(item.getQuantity());
		invItem.setQuantityMco(item.getQuantityMco());
		invItem.setPrice(item.getPrice());
		invItem.setTotalValue(item.getTotalValue());
		invItem.setRecoveryYield(calcRecoveryYield(item.getQuantityMco(), item.getQuantity()));

		return invItem;
	}

	private void copyDtoToEntity(InventoryDTO dto, Inventory entity) {

		entity.setDateInventory(dto.getDateInventory());
		entity.setDescription(dto.getDescription());
		entity.setObservation(dto.getObservation());

		entity.getItems().clear();

		for (InventoryItemDTO itemDto : dto.getItems()) {

			InventoryItem item = new InventoryItem();
			item.setInventory(entity);
			item.setTypeReceipt(TypeTransactionReceipt.fromDescription(itemDto.getTypeReceipt()));
			item.setItemSequence(itemDto.getItemSequence());
			item.setQuantity(itemDto.getQuantity());
			item.setQuantityMco(itemDto.getQuantityMco());
			item.setPrice(itemDto.getPrice());
			item.setTotalValue(itemDto.getTotalValue());
			item.setRecoveryYield(itemDto.getRecoveryYield());

			Material material = materialRepository
					.findByMaterialCode(itemDto.getMaterial().getMaterialCode())
					.orElseThrow(() -> new ResourceNotFoundException("Material not found"));

			Partner partner = partnerRepository
					.findById(itemDto.getPartner().getId())
					.orElseThrow(() -> new ResourceNotFoundException("Partner not found"));

			item.setMaterial(material);
			item.setPartner(partner);

			entity.getItems().add(item);
		}
	}

	private BigDecimal calcRecoveryYield(BigDecimal mco, BigDecimal total) {
		if (total == null || total.compareTo(BigDecimal.ZERO) == 0)
			return BigDecimal.ZERO;
		return defaultZero(mco).divide(total, 4, RoundingMode.HALF_UP);
	}

	private BigDecimal calcAveragePrice(BigDecimal value, BigDecimal qty) {
		if (qty == null || qty.compareTo(BigDecimal.ZERO) == 0)
			return BigDecimal.ZERO;
		return defaultZero(value).divide(qty, 4, RoundingMode.HALF_UP);
	}

	private BigDecimal defaultZero(BigDecimal value) {
		return value != null ? value : BigDecimal.ZERO;
	}

	public Page<InventoryReportProjection> getReportRange(
			LocalDate startDate, 
			LocalDate endDate, 
			Long materialCode,
			Pageable pageable) 
	{
		if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
			throw new IllegalArgumentException("Data inicial maior que data final");
			
		}
		if (materialCode != null && !materialRepository.existsByMaterialCode(materialCode)) {
			throw new IllegalArgumentException("Material inexistente: " + materialCode);
		}
		return inventoryRepository.reportInventory(startDate, endDate, materialCode, pageable);
	}
}
