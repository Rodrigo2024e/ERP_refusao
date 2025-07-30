package com.smartprocessrefusao.erprefusao.projections;

public interface ReportMaterialProjection {

	Long getId();
	String getDescription();
	String getUnit();
	String getTax_Classification();
	Integer getNumber();
	String getProduct_Group();
}
