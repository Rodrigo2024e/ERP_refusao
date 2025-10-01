package com.smartprocessrefusao.erprefusao.tests;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.smartprocessrefusao.erprefusao.dto.SupplierReceiptDTO;
import com.smartprocessrefusao.erprefusao.entities.Input;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.SupplierReceipt;
import com.smartprocessrefusao.erprefusao.enumerados.TypeCosts;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionReceipt;

public class SupplierReceiptFactory {

	private static final long EXISTING_ID = 1L;
	private static final long EXISTING_PARTNER_ID = 3L;
	private static final long NON_EXISTING_INPUT_ID = 999L;

	public static SupplierReceiptDTO createDTO() {
		SupplierReceipt entity = createEntity();
		return new SupplierReceiptDTO(entity);
	}

	public static SupplierReceipt createEntity() {
		SupplierReceipt entity = new SupplierReceipt();
		entity.setId(1L);
		entity.setDateReceipt(LocalDate.now());
		entity.setAmountSupplier(new BigDecimal("1000"));
		entity.setUnitValue(new BigDecimal("5.00"));
		entity.setTransaction(TypeTransactionReceipt.BUY);
		entity.setCosts(TypeCosts.DIRECT_COSTS);

		Partner partner = new Partner();
		partner.setId(1L);
		partner.setName("Fornecedor Teste");

		Input input = new Input();
		input.setId(2L);
		input.setDescription("Insumo Teste");

		entity.setPartner(partner);
		entity.setInput(input);

		return entity;
	}

	public static SupplierReceiptDTO createSupplierReceiptInvalidCostsDTO() {
		return new SupplierReceiptDTO(EXISTING_ID, LocalDate.now(), EXISTING_PARTNER_ID, "Partner Test",
				TypeTransactionReceipt.SENT_FOR_PROCESSING.toString(), "INVALID", 1L, "Input Teste",
				new BigDecimal("1000.00"), new BigDecimal("5.00"), new BigDecimal("5000.00"));
	}

	public static SupplierReceiptDTO createSupplierReceiptInvalidTransactionDTO() {
		return new SupplierReceiptDTO(EXISTING_ID, LocalDate.now(), EXISTING_PARTNER_ID, "Partner Test", "INVALID",
				TypeCosts.DIRECT_COSTS.toString(), 1L, "Input Teste", new BigDecimal("1000.00"), new BigDecimal("5.00"),
				new BigDecimal("5000.00"));
	}

	public static SupplierReceiptDTO createSupplierReceiptInvalidInputDTO() {
		return new SupplierReceiptDTO(EXISTING_ID, LocalDate.now(), EXISTING_PARTNER_ID, "Partner Test",
				TypeTransactionReceipt.SENT_FOR_PROCESSING.toString(), TypeCosts.DIRECT_COSTS.toString(),
				NON_EXISTING_INPUT_ID, "INVALID", new BigDecimal("1000.00"), new BigDecimal("5.00"),
				new BigDecimal("5000.00"));
	}

	
	public static SupplierReceiptDTO createSupplierReceiptValidPartnerDTO() {
		return new SupplierReceiptDTO(EXISTING_ID, LocalDate.now(), 4L, "ECOALUMI ALUMÍNIO S/A",
				TypeTransactionReceipt.SENT_FOR_PROCESSING.toString(), TypeCosts.DIRECT_COSTS.toString(), 1L,
				"Input Teste", new BigDecimal("1000.00"), new BigDecimal("5.00"), new BigDecimal("5000.00"));
	}
	
	public static SupplierReceiptDTO createSupplierReceiptInValidPartnerDTO() {
		return new SupplierReceiptDTO(EXISTING_ID, LocalDate.now(), 99L, "INVALID",
				TypeTransactionReceipt.SENT_FOR_PROCESSING.toString(), TypeCosts.DIRECT_COSTS.toString(), 1L,
				"Input Teste", new BigDecimal("1000.00"), new BigDecimal("5.00"), new BigDecimal("5000.00"));
	}

}