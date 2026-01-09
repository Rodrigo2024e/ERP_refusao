package com.smartprocessrefusao.erprefusao.projections;

import java.math.BigDecimal;

public interface ReceiptItemReportProjection {

	Long getReceiptId();
	Long getNumTicketId();
	Integer getItemSequence();
	Long getPartnerId();
	String getPartnerName();
	Long getMaterialCode();
	String getMaterialDescription();
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
