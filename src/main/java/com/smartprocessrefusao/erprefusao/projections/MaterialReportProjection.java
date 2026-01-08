package com.smartprocessrefusao.erprefusao.projections;

public interface MaterialReportProjection {

	Long getId();
	Long getMaterialCode();
	String getDescription();
	String getType();
	Long getUnitId();
	String getAcronym();
	Long getTaxClassId();
	String getTaxClassification();
	Integer getNcmCode();
	Long getMatGroupId();
	String getMaterialGroup();
}
