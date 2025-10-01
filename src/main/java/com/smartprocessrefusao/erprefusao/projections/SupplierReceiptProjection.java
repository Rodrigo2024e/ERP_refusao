package com.smartprocessrefusao.erprefusao.projections;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface SupplierReceiptProjection extends IdProjection<Long> {

	Long getId();

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
