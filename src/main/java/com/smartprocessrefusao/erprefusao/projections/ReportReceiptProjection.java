package com.smartprocessrefusao.erprefusao.projections;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ReportReceiptProjection {

	Long getNumTicket();

	LocalDate getDate_ticket();

	String getNumber_plate();

	String getDocumentNumber();

	BigDecimal getNet_weight();

	String getType_receipt();

	String getType_costs();

	Long getPartner_id();

	String getName();

	Long getItem_sequence();
	
	Long getMaterialId();

	Long getCode();

	String getDescription();

	BigDecimal getQuantity();

	BigDecimal getRecovery_yield();

	BigDecimal getPrice();

	BigDecimal getTotal_value();

	BigDecimal getQuantity_mco();

	String getObservation();

}
