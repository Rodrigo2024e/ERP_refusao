package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;
import com.smartprocessrefusao.erprefusao.projections.ReceiptItemReportProjection;

public class ReceiptItemReportDTO {

	private Long receiptId;
	private Long numTicketId;
	private Integer itemSequence;
	private Long partnerId;
	private String partnerName;
	private String documentNumber;
	private String typeReceipt;
	private String typeCosts;
	private Long materialCode;
	private String materialDescription;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal recoveryYield;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal quantity;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal price;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalValue;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal quantityMco;
	private String observation;

	public ReceiptItemReportDTO() {
	}

	public ReceiptItemReportDTO(ReceiptItemReportProjection p) {
		receiptId = p.getReceiptId();
		numTicketId = p.getNumTicketId();
		itemSequence = p.getItemSequence();
		partnerId = p.getPartnerId();
		partnerName = p.getPartnerName();
		documentNumber = p.getDocumentNumber();
		typeReceipt = p.getTypeReceipt();
		typeCosts = p.getTypeCosts();
		materialCode = p.getMaterialCode();
		materialDescription = p.getMaterialDescription();
		recoveryYield = p.getRecoveryYield();
		quantity = p.getQuantity();
		price = p.getPrice();
		totalValue = p.getTotalValue();
		quantityMco = p.getQuantityMco();
		observation = p.getObservation();
	}
	
	public Long getReceiptId() {
		return receiptId;
	}

	public Long getNumTicketId() {
		return numTicketId;
	}

	public Integer getItemSequence() {
		return itemSequence;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public Long getMaterialCode() {
		return materialCode;
	}

	public String getMaterialDescription() {
		return materialDescription;
	}

	public BigDecimal getRecoveryYield() {
		return recoveryYield;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public String getTypeReceipt() {
		return typeReceipt;
	}

	public String getTypeCosts() {
		return typeCosts;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public BigDecimal getQuantityMco() {
		return quantityMco;
	}

	public String getObservation() {
		return observation;
	}

}
