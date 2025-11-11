package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;

import com.smartprocessrefusao.erprefusao.projections.MaterialMovementProjection;

public class ReportMaterialMovementReceiptDTO {

	private Long id;
	private Integer ticketId;
	private Long partnerId;
	private String partnerName;
	private String transactionDescription;
	private String costs;
	private Long materialId;
	private String materialDescription;
	private BigDecimal amount;
	private BigDecimal unitValue;
	private BigDecimal totalValue;
	

	public ReportMaterialMovementReceiptDTO() {

	}
	
	public ReportMaterialMovementReceiptDTO(MaterialMovementProjection projection) {
		id = projection.getId();

		if (projection.getTicketId() != null) {
			ticketId = projection.getTicketId();
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

		if (projection.getMaterialId() != null) {
			materialId = projection.getMaterialId();
			materialDescription = projection.getInputDescription();
		}

		amount = projection.getAmount();
		unitValue = projection.getUnitValue();
		totalValue = projection.getTotalValue();

	}

	public Long getId() {
		return id;
	}

	public Integer getTicketId() {
		return ticketId;
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

	public Long getMaterialId() {
		return materialId;
	}

	public String getMaterialDescription() {
		return materialDescription;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public BigDecimal getUnitValue() {
		return unitValue;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}



}
