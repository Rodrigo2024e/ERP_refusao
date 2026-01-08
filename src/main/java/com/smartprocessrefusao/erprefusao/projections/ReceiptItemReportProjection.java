package com.smartprocessrefusao.erprefusao.projections;

import java.math.BigDecimal;

public interface ReceiptItemReportProjection {

	Long getReceiptId();
	Integer getItemSequence();
	Long getPartnerId();
	String getPartnerName();
	Long getMaterialCode();
	BigDecimal getRecoveryYield();
	String getDocumentNumber();
	String getTypeReceipt();
	String getTypeCosts();
	BigDecimal getQuantity();
	BigDecimal getPrice();
	BigDecimal getTotalValue();
	BigDecimal getQuantityMco();
	String getObservation();
}
