package com.smartprocessrefusao.erprefusao.projections;

public interface MeltingEmployeeProjection {

	Long getMeltingId();

	Long getEmployeeId();

	String getEmployeeName();

	Long getDepartamentId();

	String getDepartamentName();

	String getDepartamentProcess();

	String getEmployeePosition();

	/*
	 * Long getEmployeeId();
	 * 
	 * String getEmployeeName();
	 * 
	 * Long getDepartamentId();
	 * 
	 * String getDepartamentName();
	 * 
	 * String getDepartamentProcess();
	 * 
	 * String getEmployeePosition();
	 */
}
