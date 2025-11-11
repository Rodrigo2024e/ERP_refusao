package com.smartprocessrefusao.erprefusao.projections;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ReportReceiptProjection {

	Long getReceipt_id();
	
	Long getItem_sequence();
	
	Long getNumTicket();

	LocalDate getDate_ticket();

	Long getPartner_id();

	String getName();

	String getNumber_plate();

	BigDecimal getNet_weight();

	Long getMaterial_id();
	
	String getDescription();
	
	String getDocumentNumber();

	String getType_receipt();

	String getType_costs();

	BigDecimal getQuantity();

	BigDecimal getPrice();

	BigDecimal getTotal_value();

	String getObservation();

}
