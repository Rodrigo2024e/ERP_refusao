package com.smartprocessrefusao.erprefusao.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_stock_balance")
public class StockBalance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate dateStock = LocalDate.now();

	@OneToMany(mappedBy = "stockBalance")
	private List<Material> materials = new ArrayList<>();

	// ENTRADAS
	private BigDecimal totalPurchase = BigDecimal.ZERO;
	private BigDecimal totalPurchaseMco = BigDecimal.ZERO;
	private BigDecimal recoveryYieldPurchase = BigDecimal.ZERO;
	private BigDecimal totalValue = BigDecimal.ZERO;
	private BigDecimal averagePrice = BigDecimal.ZERO;
	private BigDecimal averagePriceMco = BigDecimal.ZERO;

	private BigDecimal totalSentForProcessing = BigDecimal.ZERO;
	private BigDecimal totalSentForProcessingMco = BigDecimal.ZERO;
	private BigDecimal recoveryYieldSentForProcessing = BigDecimal.ZERO;

	private BigDecimal totalScrapSalesReturn = BigDecimal.ZERO;
	private BigDecimal totalScrapSalesReturnMco = BigDecimal.ZERO;
	private BigDecimal recoveryYieldScrapSalesReturn = BigDecimal.ZERO;

	// AJUSTES / SAÍDAS
	private BigDecimal totalAdjustmentEntries = BigDecimal.ZERO;
	private BigDecimal totalAdjustmentEntriesMco = BigDecimal.ZERO;
	private BigDecimal recoveryYieldAdjustmentEntries = BigDecimal.ZERO;

	private BigDecimal totalSalesScrap = BigDecimal.ZERO;
	private BigDecimal totalSalesScrapMco = BigDecimal.ZERO;
	private BigDecimal recoveryYieldSalesScrap = BigDecimal.ZERO;

	private BigDecimal totalAdjustmentExit = BigDecimal.ZERO;
	private BigDecimal totalAdjustmentExitMco = BigDecimal.ZERO;
	private BigDecimal recoveryYieldAdjustmentExit = BigDecimal.ZERO;

	// SALDO FINAL
	private BigDecimal finalBalance = BigDecimal.ZERO;
	private BigDecimal finalBalanceMco = BigDecimal.ZERO;
	private BigDecimal recoveryYieldFinalBalance = BigDecimal.ZERO;

	public StockBalance() {
	}

	public StockBalance(Long id, LocalDate dateStock, List<Material> materials, BigDecimal totalPurchase,
			BigDecimal totalPurchaseMco, BigDecimal recoveryYieldPurchase, BigDecimal totalValue,
			BigDecimal averagePrice, BigDecimal averagePriceMco, BigDecimal totalSentForProcessing,
			BigDecimal totalSentForProcessingMco, BigDecimal recoveryYieldSentForProcessing,
			BigDecimal totalScrapSalesReturn, BigDecimal totalScrapSalesReturnMco,
			BigDecimal recoveryYieldScrapSalesReturn, BigDecimal totalAdjustmentEntries,
			BigDecimal totalAdjustmentEntriesMco, BigDecimal recoveryYieldAdjustmentEntries, BigDecimal totalSalesScrap,
			BigDecimal totalSalesScrapMco, BigDecimal recoveryYieldSalesScrap, BigDecimal totalAdjustmentExit,
			BigDecimal totalAdjustmentExitMco, BigDecimal recoveryYieldAdjustmentExit, BigDecimal finalBalance,
			BigDecimal finalBalanceMco, BigDecimal recoveryYieldFinalBalance) {
		this.id = id;
		this.dateStock = dateStock;
		this.materials = materials;
		this.totalPurchase = totalPurchase;
		this.totalPurchaseMco = totalPurchaseMco;
		this.recoveryYieldPurchase = recoveryYieldPurchase;
		this.totalValue = totalValue;
		this.averagePrice = averagePrice;
		this.averagePriceMco = averagePriceMco;
		this.totalSentForProcessing = totalSentForProcessing;
		this.totalSentForProcessingMco = totalSentForProcessingMco;
		this.recoveryYieldSentForProcessing = recoveryYieldSentForProcessing;
		this.totalScrapSalesReturn = totalScrapSalesReturn;
		this.totalScrapSalesReturnMco = totalScrapSalesReturnMco;
		this.recoveryYieldScrapSalesReturn = recoveryYieldScrapSalesReturn;
		this.totalAdjustmentEntries = totalAdjustmentEntries;
		this.totalAdjustmentEntriesMco = totalAdjustmentEntriesMco;
		this.recoveryYieldAdjustmentEntries = recoveryYieldAdjustmentEntries;
		this.totalSalesScrap = totalSalesScrap;
		this.totalSalesScrapMco = totalSalesScrapMco;
		this.recoveryYieldSalesScrap = recoveryYieldSalesScrap;
		this.totalAdjustmentExit = totalAdjustmentExit;
		this.totalAdjustmentExitMco = totalAdjustmentExitMco;
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

	public LocalDate getDateStock() {
		return dateStock;
	}

	public void setDateStock(LocalDate dateStock) {
		this.dateStock = dateStock;
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
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

	public BigDecimal getRecoveryYieldPurchase() {
		return recoveryYieldPurchase;
	}

	public void setRecoveryYieldPurchase(BigDecimal recoveryYieldPurchase) {
		this.recoveryYieldPurchase = recoveryYieldPurchase;
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

	public BigDecimal getAveragePriceMco() {
		return averagePriceMco;
	}

	public void setAveragePriceMco(BigDecimal averagePriceMco) {
		this.averagePriceMco = averagePriceMco;
	}

	public void setAveragePrice(BigDecimal averagePrice) {
		this.averagePrice = averagePrice;
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

	public BigDecimal getRecoveryYieldSentForProcessing() {
		return recoveryYieldSentForProcessing;
	}

	public void setRecoveryYieldSentForProcessing(BigDecimal recoveryYieldSentForProcessing) {
		this.recoveryYieldSentForProcessing = recoveryYieldSentForProcessing;
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

	public BigDecimal getRecoveryYieldScrapSalesReturn() {
		return recoveryYieldScrapSalesReturn;
	}

	public void setRecoveryYieldScrapSalesReturn(BigDecimal recoveryYieldScrapSalesReturn) {
		this.recoveryYieldScrapSalesReturn = recoveryYieldScrapSalesReturn;
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

	public BigDecimal getRecoveryYieldAdjustmentEntries() {
		return recoveryYieldAdjustmentEntries;
	}

	public void setRecoveryYieldAdjustmentEntries(BigDecimal recoveryYieldAdjustmentEntries) {
		this.recoveryYieldAdjustmentEntries = recoveryYieldAdjustmentEntries;
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

	public BigDecimal getRecoveryYieldSalesScrap() {
		return recoveryYieldSalesScrap;
	}

	public void setRecoveryYieldSalesScrap(BigDecimal recoveryYieldSalesScrap) {
		this.recoveryYieldSalesScrap = recoveryYieldSalesScrap;
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

	// ------------------------------------------------------------
	// REGRAS DE CÁLCULO
	// ------------------------------------------------------------
	// Scrap
	public void updateFinalBalance() {
		BigDecimal totalEntries = totalPurchase.add(totalScrapSalesReturn).add(totalSentForProcessing);

		this.finalBalance = totalEntries.add(totalAdjustmentEntries).subtract(totalSalesScrap)
				.subtract(totalAdjustmentExit);

		// Mco
		BigDecimal totalEntiesMco = totalPurchaseMco.add(totalScrapSalesReturnMco).add(totalSentForProcessingMco);

		this.finalBalanceMco = totalEntiesMco.add(totalAdjustmentEntriesMco).subtract(totalSalesScrapMco)
				.subtract(totalAdjustmentExitMco);

		// --- RECOVERY YIELD ---
		if (finalBalance != null && finalBalance.compareTo(BigDecimal.ZERO) > 0 && finalBalanceMco != null) {

			BigDecimal recoveryReturnRy = finalBalanceMco.divide(finalBalance, 8, // escala (pode ajustar)
					RoundingMode.HALF_UP // modo de arredondamento seguro
			);

			this.setRecoveryYieldFinalBalance(recoveryReturnRy);

		} else {
			// Caso não seja possível calcular
			this.setRecoveryYieldFinalBalance(BigDecimal.ZERO);
		}

	}

	// ------------------------------------------------------------
	// EQUALS & HASHCODE
	// ------------------------------------------------------------
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockBalance other = (StockBalance) obj;
		return Objects.equals(id, other.id);
	}
}
