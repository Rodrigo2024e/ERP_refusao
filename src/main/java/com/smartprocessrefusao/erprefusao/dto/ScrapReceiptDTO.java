package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.entities.ScrapReceipt;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.IntegerBrazilianSerializerWithoutDecimal;
import com.smartprocessrefusao.erprefusao.projections.ScrapReceiptProjection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ScrapReceiptDTO {

	private Long id;

	private Instant moment;

	@NotNull(message = "Informar o número do ticket associado")
	@JsonSerialize(using = IntegerBrazilianSerializerWithoutDecimal.class)
	private Integer numTicketId;

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
	private BigDecimal amountScrap;

	@NotNull(message = "Campo requerido")
	@Positive(message = "O valor unitário deve ser positivo")
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal unitValue;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalValue;

	@NotNull(message = "Campo requerido")
	@Positive(message = "O rendimento metálico teórico deve ser positivo")
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal metalYield;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal metalWeight;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal slag;

	public ScrapReceiptDTO() {

	}

	public ScrapReceiptDTO(Long id, Instant moment, Integer numTicketId, Long partnerId, String partnerName,
			String transactionDescription, String costs, Long inputId, String inputDescription, BigDecimal amountScrap,
			BigDecimal unitValue, BigDecimal totalValue, BigDecimal metalYield, BigDecimal metalWeight,
			BigDecimal slag) {
		this.id = id;
		this.moment = moment;
		this.numTicketId = numTicketId;
		this.partnerId = partnerId;
		this.partnerName = partnerName;
		this.transactionDescription = transactionDescription;
		this.costs = costs;
		this.inputId = inputId;
		this.inputDescription = inputDescription;
		this.amountScrap = amountScrap;
		this.unitValue = unitValue;
		this.totalValue = totalValue;
		this.metalYield = metalYield;
		this.metalWeight = metalWeight;
		this.slag = slag;
	}

	public ScrapReceiptDTO(ScrapReceipt entity) {
		id = entity.getId();
		moment = entity.getMoment();
		numTicketId = entity.getNumTicket().getNumTicket();
		partnerId = entity.getPartner().getId();
		partnerName = entity.getPartner().getName();
		transactionDescription = entity.getTransaction().getDescription();
		costs = entity.getCosts().toString();
		inputId = entity.getInput().getId();
		inputDescription = entity.getInput().getDescription();
		amountScrap = entity.getAmountScrap();
		unitValue = entity.getUnitValue();
		totalValue = entity.getTotalValue();
		metalYield = entity.getMetalYield();
		metalWeight = entity.getMetalWeight();
		slag = entity.getSlag();

	}

	public ScrapReceiptDTO(ScrapReceiptProjection projection) {
		id = projection.getId();
		moment = projection.getMoment();
		numTicketId = projection.getNumTicketId();

		if (projection.getPartnerId() != null) {
			partnerId = projection.getPartnerId();
			partnerName = projection.getPartnerName();
		}

		if (projection.getTransactionDescription() != null) {
			transactionDescription = projection.getTransactionDescription();

		}

		if (projection.getCosts() != null) {
			costs = projection.getCosts().toString();

		}

		if (projection.getInputId() != null) {
			inputId = projection.getInputId();
			inputDescription = projection.getInputDescription();
		}

		amountScrap = projection.getAmountScrap();
		unitValue = projection.getUnitValue();
		totalValue = projection.getTotalValue();
		metalYield = projection.getMetalYield();
		metalWeight = projection.getMetalWeight();
		slag = projection.getSlag();

	}

	public Long getId() {
		return id;
	}

	public Instant getMoment() {
		return moment;
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

	public BigDecimal getAmountScrap() {
		return amountScrap;
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
