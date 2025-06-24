package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.dto.PartnerDTO;
import com.smartprocessrefusao.erprefusao.entities.Partner;

public class PartnerFactory {

	public static Partner createPartner() {
		Partner partner = new Partner();
		partner.setId(1L);
		partner.setname("João Carlos");
		partner.setEmail("joao@gmail.com");
		partner.setCellPhone("44-12244-1222");
		partner.setTelephone("44-1442-2222");
		partner.setCnpj("07.911.773/0001-79");
		partner.setIe("114.115.225");
		partner.setSupplier(true);
		partner.setClient(true);
		partner.setActive(true);
		return partner;
		
	}
	
	public static PartnerDTO createPartnerDTO() {
		PartnerDTO dto = new PartnerDTO();
		dto.setName("João Carlos");
		dto.setEmail("joao@gmail.com");
		dto.setCellPhone("44-12244-1222");
		dto.setTelephone("44-1442-2222");
		dto.setCnpj("07.911.773/0001-79");
		dto.setIe("114.115.225");
		dto.setSupplier(true);
		dto.setClient(true);
		dto.setActive(true);
		return dto;
	}
	
	
}
