package com.smartprocessrefusao.erprefusao.projections;

public interface ReportProductProjection {

	Long getId();
	String getDescription();
	Integer getAlloy();
	Integer getInch();
	Integer getLength();
	String getUnit();
	String getTax_Classification();
	String getNumber();
	String getProduct_Group();
}
