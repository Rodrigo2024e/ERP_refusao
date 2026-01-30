package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.entities.MaterialStockBalance;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;

public class MaterialStockBalanceDTO {

	private Long id;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	
	private BigDecimal totalPurchase = BigDecimal.ZERO;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalPurchaseMco = BigDecimal.ZERO;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal recoveryYieldPurchase = BigDecimal.ZERO;
	
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalSentForProcessing = BigDecimal.ZERO;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalSentForProcessingMco = BigDecimal.ZERO;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal recoveryYieldSentForProcessing = BigDecimal.ZERO;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalScrapSalesReturn = BigDecimal.ZERO;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalScrapSalesReturnMco = BigDecimal.ZERO;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal recoveryYieldScrapSalesReturn = BigDecimal.ZERO;

	private BigDecimal totalAdjustmentEntries = BigDecimal.ZERO;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalAdjustmentEntriesMco = BigDecimal.ZERO;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal recoveryYieldAdjustmentEntries = BigDecimal.ZERO;

	private BigDecimal totalSalesScrap = BigDecimal.ZERO;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalSalesScrapMco = BigDecimal.ZERO;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal recoveryYieldSalesScrap = BigDecimal.ZERO;

	private BigDecimal totalAdjustmentExit = BigDecimal.ZERO;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalAdjustmentExitMco = BigDecimal.ZERO;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal recoveryYieldAdjustmentExit = BigDecimal.ZERO;

	private BigDecimal finalBalance = BigDecimal.ZERO;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal finalBalanceMco = BigDecimal.ZERO;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal recoveryYieldFinalBalance = BigDecimal.ZERO;

	private List<MaterialDTO> materials = new ArrayList<>();

	public MaterialStockBalanceDTO() {
	}

	public MaterialStockBalanceDTO(Long id, BigDecimal totalPurchase, BigDecimal totalPurchaseMco,
			BigDecimal recoveryYieldPurchase, BigDecimal totalSentForProcessing, BigDecimal totalSentForProcessingMco,
			BigDecimal recoveryYieldSentForProcessing, BigDecimal totalScrapSalesReturn,
			BigDecimal totalScrapSalesReturnMco, BigDecimal recoveryYieldScrapSalesReturn,
			BigDecimal totalAdjustmentEntries, BigDecimal totalAdjustmentEntriesMco,
			BigDecimal recoveryYieldAdjustmentEntries, BigDecimal totalSalesScrap, BigDecimal totalSalesScrapMco,
			BigDecimal recoveryYieldSalesScrap, BigDecimal totalAdjustmentExit, BigDecimal totalAdjustmentExitMco,
			BigDecimal recoveryYieldAdjustmentExit, BigDecimal finalBalance, BigDecimal finalBalanceMco,
			BigDecimal recoveryYieldFinalBalance) {
		this.id = id;
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

	public MaterialStockBalanceDTO(MaterialStockBalance entity) {
		id = entity.getId();

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
	}

	public Long getId() {
		return id;
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
