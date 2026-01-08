package com.smartprocessrefusao.erprefusao.projections;

import java.math.BigDecimal;

public interface MeltingItemProjection {

	Long getMeltingId();
	
	Integer getItemSequence();

	Long getMaterialCode();

	String getDescription();

	BigDecimal getQuantity();

	BigDecimal getAveragePrice();

	BigDecimal getTotalValue();

	BigDecimal getAverageRecoveryYield();

	BigDecimal getQuantityMco();

	BigDecimal getSlagWeight();
}
