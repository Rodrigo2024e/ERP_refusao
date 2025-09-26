package com.smartprocessrefusao.erprefusao.projections;

public interface ProductReportProjection {

	Long getId();

	String getTypeMaterial();

	String getDescription();

	Integer getAlloy();

	Integer getBilletDiameter();

	Double getBilletLength();

	Long getUnitId();

	String getAcronym();

	Long getTaxClassId();

	String getDescription_taxclass();

	Integer getNumber();

	Long getMatGroupId();

	String getDescription_matGroup();
}
