package com.smartprocessrefusao.erprefusao.projections;

import java.math.BigDecimal;

public interface ReportInventoryProjection {
	Long getCode();

	String getDescription();
	
	BigDecimal getTotalAdjustmentEntries();
	BigDecimal getRecoveryYieldAdjustmentEntries();
	BigDecimal getTotalAdjustmentEntriesMco();

	BigDecimal getTotalPurchase();
	BigDecimal getRecoveryYieldPurchase();
	BigDecimal getTotalPurchaseMco();

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
