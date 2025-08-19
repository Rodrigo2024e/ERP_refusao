package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.dto.PartnerDTO;
import com.smartprocessrefusao.erprefusao.entities.Partner;

public class PartnerFactory {

	public static Partner createPartner() {
		Partner partner = new Partner();
		partner.setId(1L);
		partner.setName("ECOALUMI ALUMINIO S/A");
		partner.setEmail("JOAO@GMAIL.COM");
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
		return new PartnerDTO(createPartner());
	}

}
