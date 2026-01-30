package com.smartprocessrefusao.erprefusao.projections;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface InventoryReportProjection {

	LocalDate getDateInventory();

	Long getMaterialCode();

	String getDescription();

	BigDecimal getTotalAdjustmentEntries();

	BigDecimal getRecoveryYieldAdjustmentEntries();

	BigDecimal getTotalAdjustmentEntriesMco();

	BigDecimal getTotalPurchase();

	BigDecimal getRecoveryYieldPurchase();

	BigDecimal getTotalPurchaseMco();

	BigDecimal getTotalValue();

	BigDecimal getAveragePrice();

	BigDecimal getAveragePriceMco();

	BigDecimal getTotalSentForProcessing();

	BigDecimal getRecoveryYieldSentForProcessing();

	BigDecimal getTotalSentForProcessingMco();

	BigDecimal getTotalSalesScrap();

	BigDecimal getRecoveryYieldSalesScrap();

	BigDecimal getTotalSalesScrapMco();

	BigDecimal getTotalScrapSalesReturn();

	BigDecimal getRecoveryYieldScrapSalesReturn();

	BigDecimal getTotalScrapSalesReturnMco();

	BigDecimal getTotalAdjustmentExit();

	BigDecimal getRecoveryYieldAdjustmentExit();

	BigDecimal getTotalAdjustmentExitMco();

	BigDecimal getFinalBalance();

	BigDecimal getRecoveryYieldFinalBalance();

	BigDecimal getFinalBalanceMco();

}
