package com.smartprocessrefusao.erprefusao.projections;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface TicketReportProjection {

	
	Integer getNumTicket();

	LocalDate getDateTicket();

	String getNumberPlate();

	BigDecimal getNetWeight();

	Integer getNumTicketId();

	Long getPartnerId();

	String getNamePartner();

	Long getScrapId();

	String getScrapDescription();

	BigDecimal getAmountScrap();

}
