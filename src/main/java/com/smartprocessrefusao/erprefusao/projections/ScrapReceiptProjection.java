package com.smartprocessrefusao.erprefusao.projections;

import java.math.BigDecimal;
import java.time.Instant;

public interface ScrapReceiptProjection extends IdProjection<Long> {

	Long getId();

	Instant getMoment();
	
	Integer getNumTicketId();

	Long getPartnerId();

	String getPartnerName();

	String getTransactionDescription();

	String getCosts();

	Long getInputId();

	String getInputDescription();

	BigDecimal getAmountScrap();

	BigDecimal getUnitValue();

	BigDecimal getTotalValue();

	BigDecimal getMetalYield();

	BigDecimal getMetalWeight();

	BigDecimal getSlag();

}
