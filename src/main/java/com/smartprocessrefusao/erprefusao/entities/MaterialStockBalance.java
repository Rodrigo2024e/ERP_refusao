package com.smartprocessrefusao.erprefusao.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_material_stock_balance")
public class MaterialStockBalance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "inventory_id", nullable = false)
	private Inventory inventory;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "material_id", nullable = false)
	private Material material;

	// --- SALDO INICIAL (Vindo do registro anterior) ---
	private BigDecimal previousBalance = BigDecimal.ZERO;
	private BigDecimal previousBalanceMco = BigDecimal.ZERO;

	// --- MOVIMENTAÇÕES DO ITEM ATUAL ---
	private BigDecimal totalPurchase = BigDecimal.ZERO;
	private BigDecimal totalPurchaseMco = BigDecimal.ZERO;
	private BigDecimal totalSentForProcessing = BigDecimal.ZERO;
	private BigDecimal totalSentForProcessingMco = BigDecimal.ZERO;
	private BigDecimal totalScrapSalesReturn = BigDecimal.ZERO;
	private BigDecimal totalScrapSalesReturnMco = BigDecimal.ZERO;
	private BigDecimal totalAdjustmentEntries = BigDecimal.ZERO;
	private BigDecimal totalAdjustmentEntriesMco = BigDecimal.ZERO;
	private BigDecimal totalSalesScrap = BigDecimal.ZERO;
	private BigDecimal totalSalesScrapMco = BigDecimal.ZERO;
	private BigDecimal totalAdjustmentExit = BigDecimal.ZERO;
	private BigDecimal totalAdjustmentExitMco = BigDecimal.ZERO;

	// --- VALORES E RENDIMENTOS ---
	private BigDecimal totalValue = BigDecimal.ZERO;
	private BigDecimal averagePrice = BigDecimal.ZERO;
	private BigDecimal averagePriceMco = BigDecimal.ZERO;
	private BigDecimal recoveryYieldPurchase = BigDecimal.ZERO;
	private BigDecimal recoveryYieldSentForProcessing = BigDecimal.ZERO;
	private BigDecimal recoveryYieldScrapSalesReturn = BigDecimal.ZERO;
	private BigDecimal recoveryYieldAdjustmentEntries = BigDecimal.ZERO;
	private BigDecimal recoveryYieldSalesScrap = BigDecimal.ZERO;
	private BigDecimal recoveryYieldAdjustmentExit = BigDecimal.ZERO;

	// --- SALDO FINAL ACUMULADO ---
	private BigDecimal finalBalance = BigDecimal.ZERO;
	private BigDecimal finalBalanceMco = BigDecimal.ZERO;
	private BigDecimal recoveryYieldFinalBalance = BigDecimal.ZERO;

	public MaterialStockBalance() {
	}

	public MaterialStockBalance(Long id, Inventory inventory, Material material, BigDecimal previousBalance,
			BigDecimal previousBalanceMco, BigDecimal totalPurchase, BigDecimal totalPurchaseMco,
			BigDecimal totalSentForProcessing, BigDecimal totalSentForProcessingMco, BigDecimal totalScrapSalesReturn,
			BigDecimal totalScrapSalesReturnMco, BigDecimal totalAdjustmentEntries,
			BigDecimal totalAdjustmentEntriesMco, BigDecimal totalSalesScrap, BigDecimal totalSalesScrapMco,
			BigDecimal totalAdjustmentExit, BigDecimal totalAdjustmentExitMco, BigDecimal totalValue,
			BigDecimal averagePrice, BigDecimal averagePriceMco, BigDecimal recoveryYieldPurchase,
			BigDecimal recoveryYieldSentForProcessing, BigDecimal recoveryYieldScrapSalesReturn,
			BigDecimal recoveryYieldAdjustmentEntries, BigDecimal recoveryYieldSalesScrap,
			BigDecimal recoveryYieldAdjustmentExit, BigDecimal finalBalance, BigDecimal finalBalanceMco,
			BigDecimal recoveryYieldFinalBalance) {
		this.id = id;
		this.inventory = inventory;
		this.material = material;
		this.previousBalance = previousBalance;
		this.previousBalanceMco = previousBalanceMco;
		this.totalPurchase = totalPurchase;
		this.totalPurchaseMco = totalPurchaseMco;
		this.totalSentForProcessing = totalSentForProcessing;
		this.totalSentForProcessingMco = totalSentForProcessingMco;
		this.totalScrapSalesReturn = totalScrapSalesReturn;
		this.totalScrapSalesReturnMco = totalScrapSalesReturnMco;
		this.totalAdjustmentEntries = totalAdjustmentEntries;
		this.totalAdjustmentEntriesMco = totalAdjustmentEntriesMco;
		this.totalSalesScrap = totalSalesScrap;
		this.totalSalesScrapMco = totalSalesScrapMco;
		this.totalAdjustmentExit = totalAdjustmentExit;
		this.totalAdjustmentExitMco = totalAdjustmentExitMco;
		this.totalValue = totalValue;
		this.averagePrice = averagePrice;
		this.averagePriceMco = averagePriceMco;
		this.recoveryYieldPurchase = recoveryYieldPurchase;
		this.recoveryYieldSentForProcessing = recoveryYieldSentForProcessing;
		this.recoveryYieldScrapSalesReturn = recoveryYieldScrapSalesReturn;
		this.recoveryYieldAdjustmentEntries = recoveryYieldAdjustmentEntries;
		this.recoveryYieldSalesScrap = recoveryYieldSalesScrap;
		this.recoveryYieldAdjustmentExit = recoveryYieldAdjustmentExit;
		this.finalBalance = finalBalance;
		this.finalBalanceMco = finalBalanceMco;
		this.recoveryYieldFinalBalance = recoveryYieldFinalBalance;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public BigDecimal getPreviousBalance() {
		return previousBalance;
	}

	public void setPreviousBalance(BigDecimal previousBalance) {
		this.previousBalance = previousBalance;
	}

	public BigDecimal getPreviousBalanceMco() {
		return previousBalanceMco;
	}

	public void setPreviousBalanceMco(BigDecimal previousBalanceMco) {
		this.previousBalanceMco = previousBalanceMco;
	}

	public BigDecimal getTotalPurchase() {
		return totalPurchase;
	}

	public void setTotalPurchase(BigDecimal totalPurchase) {
		this.totalPurchase = totalPurchase;
	}

	public BigDecimal getTotalPurchaseMco() {
		return totalPurchaseMco;
	}

	public void setTotalPurchaseMco(BigDecimal totalPurchaseMco) {
		this.totalPurchaseMco = totalPurchaseMco;
	}

	public BigDecimal getTotalSentForProcessing() {
		return totalSentForProcessing;
	}

	public void setTotalSentForProcessing(BigDecimal totalSentForProcessing) {
		this.totalSentForProcessing = totalSentForProcessing;
	}

	public BigDecimal getTotalSentForProcessingMco() {
		return totalSentForProcessingMco;
	}

	public void setTotalSentForProcessingMco(BigDecimal totalSentForProcessingMco) {
		this.totalSentForProcessingMco = totalSentForProcessingMco;
	}

	public BigDecimal getTotalScrapSalesReturn() {
		return totalScrapSalesReturn;
	}

	public void setTotalScrapSalesReturn(BigDecimal totalScrapSalesReturn) {
		this.totalScrapSalesReturn = totalScrapSalesReturn;
	}

	public BigDecimal getTotalScrapSalesReturnMco() {
		return totalScrapSalesReturnMco;
	}

	public void setTotalScrapSalesReturnMco(BigDecimal totalScrapSalesReturnMco) {
		this.totalScrapSalesReturnMco = totalScrapSalesReturnMco;
	}

	public BigDecimal getTotalAdjustmentEntries() {
		return totalAdjustmentEntries;
	}

	public void setTotalAdjustmentEntries(BigDecimal totalAdjustmentEntries) {
		this.totalAdjustmentEntries = totalAdjustmentEntries;
	}

	public BigDecimal getTotalAdjustmentEntriesMco() {
		return totalAdjustmentEntriesMco;
	}

	public void setTotalAdjustmentEntriesMco(BigDecimal totalAdjustmentEntriesMco) {
		this.totalAdjustmentEntriesMco = totalAdjustmentEntriesMco;
	}

	public BigDecimal getTotalSalesScrap() {
		return totalSalesScrap;
	}

	public void setTotalSalesScrap(BigDecimal totalSalesScrap) {
		this.totalSalesScrap = totalSalesScrap;
	}

	public BigDecimal getTotalSalesScrapMco() {
		return totalSalesScrapMco;
	}

	public void setTotalSalesScrapMco(BigDecimal totalSalesScrapMco) {
		this.totalSalesScrapMco = totalSalesScrapMco;
	}

	public BigDecimal getTotalAdjustmentExit() {
		return totalAdjustmentExit;
	}

	public void setTotalAdjustmentExit(BigDecimal totalAdjustmentExit) {
		this.totalAdjustmentExit = totalAdjustmentExit;
	}

	public BigDecimal getTotalAdjustmentExitMco() {
		return totalAdjustmentExitMco;
	}

	public void setTotalAdjustmentExitMco(BigDecimal totalAdjustmentExitMco) {
		this.totalAdjustmentExitMco = totalAdjustmentExitMco;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}

	public BigDecimal getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(BigDecimal averagePrice) {
		this.averagePrice = averagePrice;
	}

	public BigDecimal getAveragePriceMco() {
		return averagePriceMco;
	}

	public void setAveragePriceMco(BigDecimal averagePriceMco) {
		this.averagePriceMco = averagePriceMco;
	}

	public BigDecimal getRecoveryYieldPurchase() {
		return recoveryYieldPurchase;
	}

	public void setRecoveryYieldPurchase(BigDecimal recoveryYieldPurchase) {
		this.recoveryYieldPurchase = recoveryYieldPurchase;
	}

	public BigDecimal getRecoveryYieldSentForProcessing() {
		return recoveryYieldSentForProcessing;
	}

	public void setRecoveryYieldSentForProcessing(BigDecimal recoveryYieldSentForProcessing) {
		this.recoveryYieldSentForProcessing = recoveryYieldSentForProcessing;
	}

	public BigDecimal getRecoveryYieldScrapSalesReturn() {
		return recoveryYieldScrapSalesReturn;
	}

	public void setRecoveryYieldScrapSalesReturn(BigDecimal recoveryYieldScrapSalesReturn) {
		this.recoveryYieldScrapSalesReturn = recoveryYieldScrapSalesReturn;
	}

	public BigDecimal getRecoveryYieldAdjustmentEntries() {
		return recoveryYieldAdjustmentEntries;
	}

	public void setRecoveryYieldAdjustmentEntries(BigDecimal recoveryYieldAdjustmentEntries) {
		this.recoveryYieldAdjustmentEntries = recoveryYieldAdjustmentEntries;
	}

	public BigDecimal getRecoveryYieldSalesScrap() {
		return recoveryYieldSalesScrap;
	}

	public void setRecoveryYieldSalesScrap(BigDecimal recoveryYieldSalesScrap) {
		this.recoveryYieldSalesScrap = recoveryYieldSalesScrap;
	}

	public BigDecimal getRecoveryYieldAdjustmentExit() {
		return recoveryYieldAdjustmentExit;
	}

	public void setRecoveryYieldAdjustmentExit(BigDecimal recoveryYieldAdjustmentExit) {
		this.recoveryYieldAdjustmentExit = recoveryYieldAdjustmentExit;
	}

	public BigDecimal getFinalBalance() {
		return finalBalance;
	}

	public void setFinalBalance(BigDecimal finalBalance) {
		this.finalBalance = finalBalance;
	}

	public BigDecimal getFinalBalanceMco() {
		return finalBalanceMco;
	}

	public void setFinalBalanceMco(BigDecimal finalBalanceMco) {
		this.finalBalanceMco = finalBalanceMco;
	}

	public BigDecimal getRecoveryYieldFinalBalance() {
		return recoveryYieldFinalBalance;
	}

	public void setRecoveryYieldFinalBalance(BigDecimal recoveryYieldFinalBalance) {
		this.recoveryYieldFinalBalance = recoveryYieldFinalBalance;
	}
	// ---------------- REGRAS DE CÁLCULO ----------------

	/**
	 * Calcula o saldo final somando o saldo anterior + entradas - saídas.
	 */
	public void updateFinalBalance() {
	    // Cálculo do Saldo Quantidade (Normal)
	    BigDecimal entries = totalPurchase
	            .add(totalSentForProcessing) 
	            .add(totalScrapSalesReturn)
	            .add(totalAdjustmentEntries);

	    BigDecimal exits = totalSalesScrap
	            .add(totalAdjustmentExit);

	    this.finalBalance = previousBalance.add(entries).subtract(exits);

	    // Cálculo do Saldo MCO (O que faltava)
	    BigDecimal entriesMco = totalPurchaseMco
	            .add(totalSentForProcessingMco)
	            .add(totalScrapSalesReturnMco)
	            .add(totalAdjustmentEntriesMco);
	    
	    BigDecimal exitsMco = totalSalesScrapMco
	            .add(totalAdjustmentExitMco);

	    // Agora o MCO acumula corretamente e subtrai as saídas
	    this.finalBalanceMco = previousBalanceMco.add(entriesMco).subtract(exitsMco);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof MaterialStockBalance))
			return false;
		MaterialStockBalance that = (MaterialStockBalance) o;
		return Objects.equals(inventory, that.inventory) && Objects.equals(material, that.material);
	}

	@Override
	public int hashCode() {
		return Objects.hash(inventory, material);
	}
}
