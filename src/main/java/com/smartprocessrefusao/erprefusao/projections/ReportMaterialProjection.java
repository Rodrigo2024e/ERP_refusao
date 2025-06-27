package com.smartprocessrefusao.erprefusao.projections;

public interface ReportMaterialProjection {

	Long getId();
	String getDescription();
	String getUnit();
	String getTax_Classification();
	String getNumber();
	String getProduct_Group();
}
