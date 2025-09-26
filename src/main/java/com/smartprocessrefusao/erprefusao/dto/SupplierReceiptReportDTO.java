package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;
import com.smartprocessrefusao.erprefusao.projections.SupplierReceiptReportProjection;

public class SupplierReceiptReportDTO {

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

	public SupplierReceiptReportDTO() {

	}

	public SupplierReceiptReportDTO(Long id, String typeMaterial, LocalDate dateReceipt, Long partnerId,
			String partnerName, Long supplierId, String supplierDescription, String transaction, String costs,
			BigDecimal amountSupplier, BigDecimal unitValue, BigDecimal totalValue) {
		this.id = id;

		this.typeMaterial = typeMaterial;
		this.dateReceipt = dateReceipt;
		this.partnerId = partnerId;
		this.partnerName = partnerName;
		this.supplierId = supplierId;
		this.supplierDescription = supplierDescription;
		this.transaction = transaction;
		this.costs = costs;
		this.amountSupplier = amountSupplier;
		this.unitValue = unitValue;
		this.totalValue = totalValue;
	}

	public SupplierReceiptReportDTO(SupplierReceiptReportProjection projection) {

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
