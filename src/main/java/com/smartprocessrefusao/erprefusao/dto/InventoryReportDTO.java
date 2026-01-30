package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;
import com.smartprocessrefusao.erprefusao.projections.InventoryReportProjection;

public class InventoryReportDTO {

	private LocalDate dateInventory;
	private Long materialCode;
	private String description;

	//INPUTS
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalAdjustmentEntries;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal recoveryYieldAdjustmentEntries;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalAdjustmentEntriesMco;

	//PURCHASE
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalPurchase;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal recoveryYieldPurchase;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalPurchaseMco;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalValue = BigDecimal.ZERO;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal averagePrice = BigDecimal.ZERO;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal averagePriceMco = BigDecimal.ZERO;

	//SENT FOR PROCESSING
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalSentForProcessing;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal recoveryYieldSentForProcessing;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalSentForProcessingMco;

	//SALES
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalSalesScrap;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal recoveryYieldSalesScrap;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalSalesScrapMco;

	//RETURN SALES
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalScrapSalesReturn;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal recoveryYieldScrapSalesReturn;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalScrapSalesReturnMco;

	//EXIT
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalAdjustmentExit;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal recoveryYieldAdjustmentExit;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalAdjustmentExitMco;

	//FINAL BALANCE
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal finalBalance;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal recoveryYieldFinalBalance;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal finalBalanceMco;

	public InventoryReportDTO(InventoryReportProjection p) {
		this.dateInventory = p.getDateInventory();
		this.materialCode = p.getMaterialCode();
		this.description = p.getDescription();

		this.totalAdjustmentEntries = p.getTotalAdjustmentEntries();
		this.totalAdjustmentEntriesMco = p.getTotalAdjustmentEntriesMco();
		this.recoveryYieldAdjustmentEntries = p.getRecoveryYieldAdjustmentEntries();

		this.totalPurchase = p.getTotalPurchase();
		this.totalPurchaseMco = p.getTotalPurchaseMco();
		this.recoveryYieldPurchase = p.getRecoveryYieldPurchase();
		this.totalValue = p.getTotalValue();
		this.averagePrice = p.getAveragePrice();
		this.averagePriceMco = p.getAveragePriceMco();

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
		this.recoveryYieldFinalBalance = p.getRecoveryYieldFinalBalance();
		this.finalBalanceMco = p.getFinalBalanceMco();
	}

	public LocalDate getDateInventory() {
		return dateInventory;
	}

	public Long getMaterialCode() {
		return materialCode;
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

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public BigDecimal getAveragePrice() {
		return averagePrice;
	}

	public BigDecimal getAveragePriceMco() {
		return averagePriceMco;
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

	public BigDecimal getRecoveryYieldFinalBalance() {
		return recoveryYieldFinalBalance;
	}

	public BigDecimal getFinalBalanceMco() {
		return finalBalanceMco;
	}

}
