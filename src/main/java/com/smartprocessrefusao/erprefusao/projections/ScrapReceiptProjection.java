package com.smartprocessrefusao.erprefusao.projections;

import java.math.BigDecimal;

public interface ScrapReceiptProjection extends IdProjection<Long> {

	Long getId();
	
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
