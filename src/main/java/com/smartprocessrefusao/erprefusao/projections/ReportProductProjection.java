package com.smartprocessrefusao.erprefusao.projections;

public interface ReportProductProjection {

	Long getCode();
	String getDescription();
	String getType();
	String getAlloy();
	Integer getBilletDiameter();
	Double getBilletLength();
	String getAcronym();
	Long getTaxClassId();
	String getDescription_taxclass();
	Integer getNcmCode();
	Long getMatGroupId();
	String getDescription_matGroup();
}
