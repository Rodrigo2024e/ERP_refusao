package com.smartprocessrefusao.erprefusao.projections;

import java.math.BigDecimal;

public interface ReceiptItemReportProjection {

	Long getReceiptId();
	Long getNumTicketId();
	Integer getItemSequence();
	Long getPartnerId();
	String getPartnerName();
	String getDocumentNumber();
	String getTypeReceipt();
	String getTypeCosts();
	Long getMaterialCode();
	String getMaterialDescription();
	BigDecimal getRecoveryYield();
	BigDecimal getQuantity();
	BigDecimal getPrice();
	BigDecimal getTotalValue();
	BigDecimal getQuantityMco();
	String getObservation();
}
