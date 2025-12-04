package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;

import com.smartprocessrefusao.erprefusao.projections.ReportInventoryProjection;

public class ReportInventoryDTO {

	private Long code;
	private String description;

	private BigDecimal totalAdjustmentEntries;
	private BigDecimal recoveryYieldAdjustmentEntries;
	private BigDecimal totalAdjustmentEntriesMco;
	
	private BigDecimal totalPurchase;
	private BigDecimal recoveryYieldPurchase;
	private BigDecimal totalPurchaseMco;
	
	private BigDecimal totalSentForProcessing;
	private BigDecimal recoveryYieldSentForProcessing;
	private BigDecimal totalSentForProcessingMco;
	
	private BigDecimal totalSalesScrap;
	private BigDecimal recoveryYieldSalesScrap;
	private BigDecimal totalSalesScrapMco;
	
	private BigDecimal totalScrapSalesReturn;
	private BigDecimal recoveryYieldScrapSalesReturn;
	private BigDecimal totalScrapSalesReturnMco;

	private BigDecimal totalAdjustmentExit;
	private BigDecimal recoveryYieldAdjustmentExit;
	private BigDecimal totalAdjustmentExitMco;
	
	private BigDecimal finalBalance;
	private BigDecimal recoveryYieldFinalBalance;
	private BigDecimal finalBalanceMco;
	

	public ReportInventoryDTO(ReportInventoryProjection p) {
		this.code = p.getCode();
		this.description = p.getDescription();

		this.totalAdjustmentEntries = p.getTotalAdjustmentEntries();
		this.totalAdjustmentEntriesMco = p.getTotalAdjustmentEntriesMco();
		this.recoveryYieldAdjustmentEntries = p.getRecoveryYieldAdjustmentEntries();

		this.totalPurchase = p.getTotalPurchase();
		this.totalPurchaseMco = p.getTotalPurchaseMco();
		this.recoveryYieldPurchase = p.getRecoveryYieldPurchase();

		this.totalSentForProcessing = p.getTotalSentForProcessing();
		this.totalSentForProcessingMco = p.getTotalSentForProcessingMco();
		this.recoveryYieldSentForProcessing = p.getRecoveryYieldSentForProcessing();

		this.totalSalesScrap = p.getTotalSalesScrap();
		this.totalSalesScrapMco = p.getTotalSalesScrapMco();
		this.recoveryYieldSalesScrap = p.getRecoveryYieldSalesScrap();

		this.totalScrapSalesReturn = p.getTotalScrapSalesReturn();
		this.totalScrapSalesReturnMco = p.getTotalScrapSalesReturnMco();
		this.recoveryYieldScrapSalesReturn = p.getRecoveryYieldScrapSalesReturn();

		this.totalAdjustmentExit = p.getTotalAdjustmentExit();
		this.totalAdjustmentExitMco = p.getTotalAdjustmentExitMco();
		this.recoveryYieldAdjustmentExit = p.getRecoveryYieldAdjustmentExit();

		this.finalBalance = p.getFinalBalance();
		this.finalBalanceMco = p.getFinalBalanceMco();
		this.recoveryYieldFinalBalance = p.getRecoveryYieldFinalBalance();

	}

	public Long getCode() {
		return code;
	}

	public String getDescription() {
		return description;
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

	public BigDecimal getTotalSalesScrap() {
		return totalSalesScrap;
	}

	public BigDecimal getTotalSalesScrapMco() {
		return totalSalesScrapMco;
	}

	public BigDecimal getRecoveryYieldSalesScrap() {
		return recoveryYieldSalesScrap;
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

}
