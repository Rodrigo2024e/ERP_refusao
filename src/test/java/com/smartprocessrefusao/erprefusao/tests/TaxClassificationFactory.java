package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.dto.TaxClassificationDTO;
import com.smartprocessrefusao.erprefusao.entities.TaxClassification;

public class TaxClassificationFactory {

	public static TaxClassification createTaxClass() {
		TaxClassification tax = new TaxClassification();
		tax.setId(1L);
		tax.setDescription("Tarugo de alumínio");
		tax.setNumber(7604000);
		return tax;
	}
	
	public static TaxClassificationDTO createTaxDTO() {
		TaxClassification taxClassification = createTaxClass();
		return new TaxClassificationDTO(taxClassification);
	}
	
}
