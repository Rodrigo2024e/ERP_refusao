package com.smartprocessrefusao.erprefusao.projections;

public interface ProductReportProjection {
	
	Long getProductCode();
	String getDescription();
	String getAlloy();
	String getAlloyPol();
	String getAlloyFootage();
	String getAcronym();
	Long getTaxClassId();
	String getDescriptionTaxclass();
	Integer getNcmCode();
	Long getMatGroupId();
	String getDescriptionMatGroup();
}
