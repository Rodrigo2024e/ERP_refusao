package com.smartprocessrefusao.erprefusao.projections;

public interface ReportEmployeeSectorProjection {

	Long getIdPessoa();
	String getName();
	Long getSectorId();
	String getNameSector();
	String getProcess();
	boolean getSysUser();

}
