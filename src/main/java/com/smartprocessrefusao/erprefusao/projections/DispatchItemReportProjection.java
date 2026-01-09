package com.smartprocessrefusao.erprefusao.projections;

import java.math.BigDecimal;

public interface DispatchItemReportProjection {

		Long getDispatchId();
		Long getNumTicketId();
		Long getItemSequence();
		Long getPartnerId();
		String getPartnerName();
		String getDocumentNumber();
		Long getProductCode();
		String getProductDescription();
		String getTypeDispatch();
		String getAlloy();
		String getAlloyPol();
		String getAlloyFootage();
		BigDecimal getQuantity();
		BigDecimal getPrice();
		BigDecimal getTotalValue();
		String getObservation();
}
