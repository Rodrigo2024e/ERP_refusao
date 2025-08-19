package com.smartprocessrefusao.erprefusao.projections;

public interface ReportInputProjection {

	Long getId();
	String getType_Material();
	String getDescription();
	String getUnit();
	Long getTaxClassId();
	String getTax_Classification();
	Integer getNumber();
	Long getMatGroupId();
	String getMaterial_Group();
}
