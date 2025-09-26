package com.smartprocessrefusao.erprefusao.projections;

import java.math.BigDecimal;

public interface ProductDispatchReportProjection {

	Long getId();

	Integer getNumTicketId();

	Long getPartnerId();

	String getPartnerName();

	String getTransactionDescription();

	Long getProductId();

	String getProductDescription();

	Integer getAlloy();

	Integer getBilletDiameter();

	Double getBilletLength();

	BigDecimal getAmountProduct();

	BigDecimal getUnitValue();

	BigDecimal getTotalValue();

}
