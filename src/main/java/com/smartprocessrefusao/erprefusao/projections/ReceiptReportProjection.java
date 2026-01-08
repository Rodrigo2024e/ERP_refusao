package com.smartprocessrefusao.erprefusao.projections;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ReceiptReportProjection {

	Long getReceiptId();

	Long getNumTicket();

	LocalDate getDateTicket();

	String getNumberPlate();

	BigDecimal getNetWeight();
}
