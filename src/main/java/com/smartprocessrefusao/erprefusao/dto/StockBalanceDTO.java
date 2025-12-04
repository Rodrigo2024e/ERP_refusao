package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.smartprocessrefusao.erprefusao.entities.StockBalance;

public class StockBalanceDTO {

	private Long id;
	private LocalDate dateStock;
	private BigDecimal totalPurchase = BigDecimal.ZERO;
	private BigDecimal totalPurchaseMco = BigDecimal.ZERO;
	private BigDecimal recoveryYieldPurchase = BigDecimal.ZERO;

	private BigDecimal totalSentForProcessing = BigDecimal.ZERO;
	private BigDecimal totalSentForProcessingMco = BigDecimal.ZERO;
	private BigDecimal recoveryYieldSentForProcessing = BigDecimal.ZERO;

	private BigDecimal totalScrapSalesReturn = BigDecimal.ZERO;
	private BigDecimal totalScrapSalesReturnMco = BigDecimal.ZERO;
	private BigDecimal recoveryYieldScrapSalesReturn = BigDecimal.ZERO;

	private BigDecimal totalAdjustmentEntries = BigDecimal.ZERO;
	private BigDecimal totalAdjustmentEntriesMco = BigDecimal.ZERO;
	private BigDecimal recoveryYieldAdjustmentEntries = BigDecimal.ZERO;

	private BigDecimal totalSalesScrap = BigDecimal.ZERO;
	private BigDecimal totalSalesScrapMco = BigDecimal.ZERO;
	private BigDecimal recoveryYieldSalesScrap = BigDecimal.ZERO;

	private BigDecimal totalAdjustmentExit = BigDecimal.ZERO;
	private BigDecimal totalAdjustmentExitMco = BigDecimal.ZERO;
	private BigDecimal recoveryYieldAdjustmentExit = BigDecimal.ZERO;

	private BigDecimal finalBalance = BigDecimal.ZERO;
	private BigDecimal finalBalanceMco = BigDecimal.ZERO;
	private BigDecimal recoveryYieldFinalBalance = BigDecimal.ZERO;

	private List<MaterialDTO> materials = new ArrayList<>();

	public StockBalanceDTO() {
	}

	public StockBalanceDTO(Long id, LocalDate dateStock, BigDecimal totalPurchase, BigDecimal totalPurchaseMco,
			BigDecimal recoveryYieldPurchase, BigDecimal totalSentForProcessing, BigDecimal totalSentForProcessingMco,
			BigDecimal recoveryYieldSentForProcessing, BigDecimal totalScrapSalesReturn,
			BigDecimal totalScrapSalesReturnMco, BigDecimal recoveryYieldScrapSalesReturn,
			BigDecimal totalAdjustmentEntries, BigDecimal totalAdjustmentEntriesMco,
			BigDecimal recoveryYieldAdjustmentEntries, BigDecimal totalSalesScrap, BigDecimal totalSalesScrapMco,
			BigDecimal recoveryYieldSalesScrap, BigDecimal totalAdjustmentExit, BigDecimal totalAdjustmentExitMco,
			BigDecimal recoveryYieldAdjustmentExit, BigDecimal finalBalance, BigDecimal finalBalanceMco,
			BigDecimal recoveryYieldFinalBalance) {
		this.id = id;
		this.dateStock = dateStock;
		this.totalPurchase = totalPurchase;
		this.totalPurchaseMco = totalPurchaseMco;
		this.recoveryYieldPurchase = recoveryYieldPurchase;
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

	public StockBalanceDTO(StockBalance entity) {
		id = entity.getId();
		dateStock = entity.getDateStock();

		totalPurchase = entity.getTotalPurchase();
		totalPurchaseMco = entity.getTotalPurchaseMco();
		recoveryYieldPurchase = entity.getRecoveryYieldPurchase();

		totalSentForProcessing = entity.getTotalSentForProcessing();
		totalSentForProcessingMco = entity.getTotalSentForProcessingMco();
		recoveryYieldSentForProcessing = entity.getRecoveryYieldSentForProcessing();

		totalScrapSalesReturn = entity.getTotalScrapSalesReturn();
		totalScrapSalesReturnMco = entity.getTotalScrapSalesReturnMco();
		recoveryYieldScrapSalesReturn = entity.getRecoveryYieldScrapSalesReturn();

		totalAdjustmentEntries = entity.getTotalAdjustmentEntries();
		totalAdjustmentEntriesMco = entity.getTotalAdjustmentEntriesMco();
		recoveryYieldAdjustmentEntries = entity.getRecoveryYieldAdjustmentEntries();

		totalSalesScrap = entity.getTotalSalesScrap();
		totalSalesScrapMco = entity.getTotalSalesScrapMco();
		recoveryYieldSalesScrap = entity.getRecoveryYieldSalesScrap();

		totalAdjustmentExit = entity.getTotalAdjustmentExit();
		totalAdjustmentExitMco = entity.getTotalAdjustmentExitMco();
		recoveryYieldAdjustmentExit = entity.getRecoveryYieldAdjustmentEntries();

		finalBalance = entity.getFinalBalance();
		finalBalanceMco = entity.getFinalBalanceMco();
		recoveryYieldFinalBalance = entity.getRecoveryYieldFinalBalance();

		materials = entity.getMaterials().stream().map(material -> new MaterialDTO(material))
				.collect(Collectors.toList());

	}

	public Long getId() {
		return id;
	}

	public LocalDate getDateStock() {
		return dateStock;
	}

	public BigDecimal getTotalPurchase() {
		return totalPurchase;
	}

	public BigDecimal getTotalPurchaseMco() {
		return totalPurchaseMco;
	}

	public BigDecimal getRecoveryYieldPurchase() {
		return recoveryYieldPurchase;
	}

	public BigDecimal getTotalSentForProcessing() {
		return totalSentForProcessing;
	}

	public BigDecimal getTotalSentForProcessingMco() {
		return totalSentForProcessingMco;
	}

	public BigDecimal getRecoveryYieldSentForProcessing() {
		return recoveryYieldSentForProcessing;
	}

	public BigDecimal getTotalScrapSalesReturn() {
		return totalScrapSalesReturn;
	}

	public BigDecimal getTotalScrapSalesReturnMco() {
		return totalScrapSalesReturnMco;
	}

	public BigDecimal getRecoveryYieldScrapSalesReturn() {
		return recoveryYieldScrapSalesReturn;
	}

	public BigDecimal getTotalAdjustmentEntries() {
		return totalAdjustmentEntries;
	}

	public BigDecimal getTotalAdjustmentEntriesMco() {
		return totalAdjustmentEntriesMco;
	}

	public BigDecimal getRecoveryYieldAdjustmentEntries() {
		return recoveryYieldAdjustmentEntries;
	}

	public BigDecimal getTotalSalesScrap() {
		return totalSalesScrap;
	}

	public BigDecimal getTotalSalesScrapMco() {
		return totalSalesScrapMco;
	}

	public BigDecimal getRecoveryYieldSalesScrap() {
		return recoveryYieldSalesScrap;
	}

	public BigDecimal getTotalAdjustmentExit() {
		return totalAdjustmentExit;
	}

	public BigDecimal getTotalAdjustmentExitMco() {
		return totalAdjustmentExitMco;
	}

	public BigDecimal getRecoveryYieldAdjustmentExit() {
		return recoveryYieldAdjustmentExit;
	}

	public BigDecimal getFinalBalance() {
		return finalBalance;
	}

	public BigDecimal getFinalBalanceMco() {
		return finalBalanceMco;
	}

	public BigDecimal getRecoveryYieldFinalBalance() {
		return recoveryYieldFinalBalance;
	}

	public List<MaterialDTO> getMaterials() {
		return materials;
	}

}
