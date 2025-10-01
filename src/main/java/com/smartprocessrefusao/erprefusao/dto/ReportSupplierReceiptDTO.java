package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;
import com.smartprocessrefusao.erprefusao.projections.SupplierReceiptProjection;

public class ReportSupplierReceiptDTO {

	private Long id;

	private String typeMaterial;

	private LocalDate dateReceipt;

	private Long partnerId;

	private String partnerName;

	private Long supplierId;

	private String supplierDescription;

	private String transaction;

	private String costs;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal amountSupplier;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal unitValue;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalValue;

	public ReportSupplierReceiptDTO() {

	}

	public ReportSupplierReceiptDTO(SupplierReceiptProjection projection) {

		id = projection.getId();
		dateReceipt = projection.getDateReceipt();
		typeMaterial = projection.getTypeMaterial();
		partnerId = projection.getPartnerId();
		partnerName = projection.getPartnerName();
		supplierId = projection.getSupplierId();
		supplierDescription = projection.getSupplierDescription();
		transaction = projection.getTransactionDescription();
		costs = projection.getCosts();
		amountSupplier = projection.getAmountSupplier();
		unitValue = projection.getUnitValue();
		totalValue = projection.getTotalValue();

	}

	public Long getId() {
		return id;
	}

	public String getTypeMaterial() {
		return typeMaterial;
	}

	public LocalDate getDateReceipt() {
		return dateReceipt;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public String getSupplierDescription() {
		return supplierDescription;
	}

	public String getTransaction() {
		return transaction;
	}

	public String getCosts() {
		return costs;
	}

	public BigDecimal getAmountSupplier() {
		return amountSupplier;
	}

	public BigDecimal getUnitValue() {
		return unitValue;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

}
