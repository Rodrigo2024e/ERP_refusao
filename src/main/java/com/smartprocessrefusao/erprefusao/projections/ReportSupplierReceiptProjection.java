package com.smartprocessrefusao.erprefusao.projections;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public interface ReportSupplierReceiptProjection extends IdProjection<Long> {

	Long getId();
	
	Instant getMoment();

	LocalDate getDateReceipt();
	
	String getTypeMaterial();
	
	Long getPartnerId();

	String getPartnerName();

	Long getSupplierId();

	String getSupplierDescription();
	
	String getTransactionDescription();

	String getCosts();

	BigDecimal getAmountSupplier();

	BigDecimal getUnitValue();

	BigDecimal getTotalValue();

}
