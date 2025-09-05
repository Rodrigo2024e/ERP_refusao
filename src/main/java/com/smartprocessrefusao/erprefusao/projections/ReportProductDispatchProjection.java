package com.smartprocessrefusao.erprefusao.projections;

import java.math.BigDecimal;
import java.time.Instant;

public interface ReportProductDispatchProjection {

	Long getId();

	Instant getMoment();

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
