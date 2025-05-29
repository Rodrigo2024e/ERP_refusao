package com.smartprocessrefusao.erprefusao.projections;

public interface ReportPartnerProjection {

		Long getId();
		String getName();
		String getCnpj();
		String getIe();
		String getEmail();
		String getCellPhone();
		String getTelephone();
		Boolean getSupplier();
		Boolean getClient();
		Boolean getActive();
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
