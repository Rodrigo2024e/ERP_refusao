package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;

import com.smartprocessrefusao.erprefusao.projections.ScrapReceiptProjection;

public class ReportScrapReceiptDTO {

	private Long id;
	private Integer numTicketId;
	private Long partnerId;
	private String partnerName;
	private String transactionDescription;
	private String costs;
	private Long inputId;
	private String inputDescription;
	private BigDecimal amountScrap;
	private BigDecimal unitValue;
	private BigDecimal totalValue;
	private BigDecimal metalYield;
	private BigDecimal metalWeight;
	private BigDecimal slag;

	public ReportScrapReceiptDTO() {

	}
	
	public ReportScrapReceiptDTO(ScrapReceiptProjection projection) {
		id = projection.getId();

		if (projection.getNumTicketId() != null) {
			numTicketId = projection.getNumTicketId();
		}

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
