package com.smartprocessrefusao.erprefusao.projections;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface MaterialMovementReportProjection extends IdProjection<Long> {

	Long getId();
	
	LocalDate getDate();
	
	Integer getTicketId();

	Long getPartnerId();

	String getPartnerName();

	String getTransactionDescription();

	String getCosts();

	Long getMaterialId();

	String getInputDescription();

	BigDecimal getAmount();

	BigDecimal getUnitValue();

	BigDecimal getTotalValue();


}
