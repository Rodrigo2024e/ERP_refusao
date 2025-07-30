package com.smartprocessrefusao.erprefusao.projections;

public interface EmployeeSectorProjection {

	Long getIdPessoa();
	String getName();
	String getEmail();
	String getCellPhone();
	String getTelephone();
	String getCpf();
	String getRg();
	boolean getSysUser();
	Long getSectorId();
	String getNameSector();
	String getProcess();

}
