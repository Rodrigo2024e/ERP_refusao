package com.smartprocessrefusao.erprefusao.projections;

public interface ReportEmployeeProjection {

		Long getIdPessoa();
		String getName();
		String getCpf();
		String getRg();
		String getEmail();
		String getCellPhone();
		String getTelephone();
		boolean isSysUser();
		Long getSectorId();
		String getNameSector();
		String getProcess();
		Long getIdAddress();
		String getStreet();
		Integer getNumberAddress();
		String getComplement();
		String getNeighborhood();
		String getZipCode();
		Long getCityId();
		String getNameCity();
		String getUfState(); 
		String getNameState(); 
		String getCountry();   
}
