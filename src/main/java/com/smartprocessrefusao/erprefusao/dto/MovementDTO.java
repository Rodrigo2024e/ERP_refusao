package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.entities.Movement;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;
import com.smartprocessrefusao.erprefusao.projections.MovementProjection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class MovementDTO {

	private Long id;

	@NotNull(message = "Informar o número do ticket associado")
	private Integer numTicketId;

	@NotNull(message = "Informar o id do parceiro")
	private Long partnerId;
	private String partnerName;

	@NotNull(message = "Informar o tipo de operação")
	private Long transactionId;
	private String transactionDescription;

	@NotBlank(message = "Informar o tipo de gastos")
	private String expenses;

	@NotNull(message = "Informar o id do material")
	private Long inputId;
	private String inputDescription;

	@NotNull(message = "Campo requerido")
	@Positive(message = "A quantidade recebida deve ser positiva")
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal amountMaterial;

	@NotNull(message = "Campo requerido")
	@Positive(message = "O valor unitário deve ser positivo")
	private BigDecimal unitValue;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalValue;

	@NotNull(message = "Campo requerido")
	@Positive(message = "O rendimento metálico teórico deve ser positivo")
	private BigDecimal metalYield;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal metalWeight;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal slag;

	public MovementDTO() {

	}

	public MovementDTO(Long id, Integer numTicketId, Long partnerId, String partnerName, Long transactionId,
			String transactionDescription, String expenses, Long inputId, String inputDescription,
			BigDecimal amountMaterial, BigDecimal unitValue, BigDecimal totalValue, BigDecimal metalYield,
			BigDecimal metalWeight, BigDecimal slag) {
		this.id = id;
		this.numTicketId = numTicketId;
		this.partnerId = partnerId;
		this.partnerName = partnerName;
		this.transactionId = transactionId;
		this.transactionDescription = transactionDescription;
		this.expenses = expenses;
		this.inputId = inputId;
		this.inputDescription = inputDescription;
		this.amountMaterial = amountMaterial;
		this.unitValue = unitValue;
		this.totalValue = totalValue;
		this.metalYield = metalYield;
		this.metalWeight = metalWeight;
		this.slag = slag;
	}

	public MovementDTO(Movement entity) {
		id = entity.getId();
		numTicketId = entity.getNumTicket().getNumTicket();
		partnerId = entity.getPartner().getId();
		partnerName = entity.getPartner().getName();
		transactionId = entity.getTransaction().getId();
		transactionDescription = entity.getTransaction().getDescription();
		expenses = entity.getExpenses().toString();
		inputId = entity.getInput().getId();
		inputDescription = entity.getInput().getDescription();
		amountMaterial = entity.getAmountMaterial();
		unitValue = entity.getUnitValue();
		totalValue = entity.getTotalValue();
		metalYield = entity.getMetalYield();
		metalWeight = entity.getMetalWeight();
		slag = entity.getSlag();

	}

	public MovementDTO(MovementProjection projection) {
		this.id = projection.getId();
		this.numTicketId = projection.getNumTicketId();

		if (projection.getPartnerId() != null) {
			this.partnerId = projection.getPartnerId();
			this.partnerName = projection.getPartnerName();
		}

		if (projection.getTransactionId() != null) {
			this.transactionId = projection.getTransactionId();
			this.transactionDescription = projection.getTransactionDescription();

		}

		if (projection.getExpenses() != null) {
			this.expenses = projection.getExpenses().toString();

		}

		if (projection.getInputId() != null) {
			this.inputId = projection.getInputId();
			this.inputDescription = projection.getInputDescription();
		}

		this.amountMaterial = projection.getAmountMaterial();
		this.unitValue = projection.getUnitValue();
		this.totalValue = projection.getTotalValue();
		this.metalYield = projection.getMetalYield();
		this.metalWeight = projection.getMetalWeight();
		this.slag = projection.getSlag();

	}

	public Long getId() {
		return id;
	}

	public Integer getNumTicketId() {
		return numTicketId;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public String getTransactionDescription() {
		return transactionDescription;
	}

	public String getExpenses() {
		return expenses;
	}

	public Long getInputId() {
		return inputId;
	}

	public String getInputDescription() {
		return inputDescription;
	}

	public BigDecimal getAmountMaterial() {
		return amountMaterial;
	}

	public BigDecimal getUnitValue() {
		return unitValue;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public BigDecimal getMetalYield() {
		return metalYield;
	}

	public BigDecimal getMetalWeight() {
		return metalWeight;
	}

	public BigDecimal getSlag() {
		return slag;
	}

}
