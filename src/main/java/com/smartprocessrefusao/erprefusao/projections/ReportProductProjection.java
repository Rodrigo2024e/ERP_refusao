package com.smartprocessrefusao.erprefusao.projections;

public interface ReportProductProjection {

	Long getId();
	String getTypeMaterial();
	String getDescription();
	Integer getAlloy();
	Integer getBilletDiameter();
	Integer getBilletLength();
	String getUnit();
	String getTax_Classification();
	Integer getNumber();
	String getMaterial_Group();
}
