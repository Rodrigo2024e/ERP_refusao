package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.entities.SupplierReceipt;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SupplierReceiptDTO {

	private Long id;

	private Instant moment;

	@NotNull(message = "Favor informar a data de recebimento do material")
	private LocalDate dateReceipt;

	@NotNull(message = "Informar o id do parceiro")
	private Long partnerId;
	private String partnerName;

	@NotNull(message = "Informar o tipo de operação")
	private String transactionDescription;

	@NotBlank(message = "Informar o tipo de gastos")
	private String costs;

	@NotNull(message = "Informar o id do material")
	private Long inputId;
	private String inputDescription;

	@NotNull(message = "Campo requerido")
	@Positive(message = "A quantidade recebida deve ser positiva")
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal amountSupplier;

	@NotNull(message = "Campo requerido")
	@Positive(message = "O valor unitário deve ser positivo")
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal unitValue;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalValue;

	public SupplierReceiptDTO() {

	}

	public SupplierReceiptDTO(Long id, Instant moment, LocalDate dateReceipt, Long partnerId, String partnerName,
			String transactionDescription, String costs, Long inputId, String inputDescription,
			BigDecimal amountSupplier, BigDecimal unitValue, BigDecimal totalValue) {
		this.id = id;
		this.moment = moment;
		this.dateReceipt = dateReceipt;
		this.partnerId = partnerId;
		this.partnerName = partnerName;
		this.transactionDescription = transactionDescription;
		this.costs = costs;
		this.inputId = inputId;
		this.inputDescription = inputDescription;
		this.amountSupplier = amountSupplier;
		this.unitValue = unitValue;
		this.totalValue = totalValue;
	}

	public SupplierReceiptDTO(SupplierReceipt entity) {
		id = entity.getId();
		moment = entity.getMoment();
		dateReceipt = entity.getDateReceipt();
		partnerId = entity.getPartner().getId();
		partnerName = entity.getPartner().getName();
		transactionDescription = entity.getTransaction().toString();
		costs = entity.getCosts().toString();
		inputId = entity.getInput().getId();
		inputDescription = entity.getInput().getDescription();
		amountSupplier = entity.getAmountSupplier();
		unitValue = entity.getUnitValue();
		totalValue = entity.getTotalValue();
	}

	public Long getId() {
		return id;
	}

	public Instant getMoment() {
		return moment;
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

	public String getTransactionDescription() {
		return transactionDescription;
	}

	public String getCosts() {
		return costs;
	}

	public Long getInputId() {
		return inputId;
	}

	public String getInputDescription() {
		return inputDescription;
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
