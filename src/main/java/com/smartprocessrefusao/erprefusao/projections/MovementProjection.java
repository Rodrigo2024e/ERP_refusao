package com.smartprocessrefusao.erprefusao.projections;

import java.math.BigDecimal;

public interface MovementProjection extends IdProjection<Long> {

	Long getId();

	Integer getNumTicketId();

	Long getPartnerId();

	String getPartnerName();

	Long getTransactionId();
	
	String getTransactionDescription();

	String getExpenses();

	Long getInputId();

	String getInputDescription();

	BigDecimal getAmountMaterial();

	BigDecimal getUnitValue();

	BigDecimal getTotalValue();

	BigDecimal getMetalYield();

	BigDecimal getMetalWeight();

	BigDecimal getSlag();

}
