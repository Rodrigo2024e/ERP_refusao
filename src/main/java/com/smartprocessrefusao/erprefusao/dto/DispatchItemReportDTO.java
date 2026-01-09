package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;
import com.smartprocessrefusao.erprefusao.projections.DispatchItemReportProjection;

public class DispatchItemReportDTO {

	private Long dispatchId;
	private Long numTicketId;
	private Long itemSequence;
	private Long partnerId;
	private String partnerName;
	private Long productCode;
	private String productDescription;
	private String documentNumber;
	private String typeDispatch;
	private String alloy;
	private String alloyPol;
	private String alloyFootage;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal quantity;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal price;
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalValue;
	private String observation;

	public DispatchItemReportDTO() {
	}

	public DispatchItemReportDTO(DispatchItemReportProjection p) {
		dispatchId = p.getDispatchId();
		numTicketId = p.getNumTicketId();
		itemSequence = p.getItemSequence();
		partnerId = p.getPartnerId();
		partnerName = p.getPartnerName();
		documentNumber = p.getDocumentNumber();
		productCode = p.getProductCode();
		productDescription = p.getProductDescription();
		typeDispatch = p.getTypeDispatch();
		alloy = p.getAlloy();
		alloyPol = p.getAlloyPol();
		alloyFootage = p.getAlloyFootage();
		quantity = p.getQuantity();
		price = p.getPrice();
		totalValue = p.getTotalValue();
		observation = p.getObservation();

	}

	public Long getDispatchId() {
		return dispatchId;
	}

	public Long getNumTicketId() {
		return numTicketId;
	}

	public Long getItemSequence() {
		return itemSequence;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public Long getProductCode() {
		return productCode;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public String getTypeDispatch() {
		return typeDispatch;
	}

	public String getAlloy() {
		return alloy;
	}

	public String getAlloyPol() {
		return alloyPol;
	}

	public String getAlloyFootage() {
		return alloyFootage;
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

	public String getObservation() {
		return observation;
	}

}
