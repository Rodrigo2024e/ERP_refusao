package com.smartprocessrefusao.erprefusao.projections;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ReportDispatchProjection {

	Long getNumTicket();

	LocalDate getDate_ticket();

	Long getItem_sequence();

	Long getPartner_id();

	String getName();

	String getNumber_plate();

	BigDecimal getNet_weight();

	Long getMaterial_id();

	String getDescription();

	String getDocumentNumber();

	String getTransactionDescription();

	String getAlloy();

	String getAlloyPol();

	String getAlloyFootage();

	BigDecimal getQuantity();

	BigDecimal getPrice();

	BigDecimal getTotal_value();

	String getObservation();

}
