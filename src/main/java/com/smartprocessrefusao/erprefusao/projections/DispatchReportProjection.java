package com.smartprocessrefusao.erprefusao.projections;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface DispatchReportProjection {

	Long getId();

	Long getNumTicketId();
	
	LocalDate getDateTicket();

	String getNumberPlate();

	BigDecimal getNetWeight();
}
