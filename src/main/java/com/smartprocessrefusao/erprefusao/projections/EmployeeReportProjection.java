package com.smartprocessrefusao.erprefusao.projections;

public interface EmployeeReportProjection {

		Long getId();
		String getName();
		String getCpf();
		String getRg();
		String getEmail();
		String getCellPhone();
		String getTelephone();
		boolean isSysUser();
		Long getDepartamentId();
		String getDepartament();
		String getProcess();
		Long getIdAddress();
		String getStreet();
		Integer getNumberAddress();
		String getComplement();
		String getNeighborhood();
		String getZipCode();
		Long getCityId();
		String getCity();
		String getState(); 
		String getNameState(); 
		String getCountry();   
}
