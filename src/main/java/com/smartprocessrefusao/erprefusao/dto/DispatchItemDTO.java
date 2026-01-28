package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.entities.DispatchItem;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DispatchItemDTO {

	private Long dispatchId;

	private Long numTicketId;

	private Integer itemSequence;

	@NotNull(message = "Informe o id do partner")
	private Long partnerId;
	private String partnerName;

	private String documentNumber;

	@NotNull(message = "Informe o tipo material")
	private Long productCode;
	private String productDescription;

	@NotNull(message = "Informe o tipo de saída")
	private String typeDispatch;

	@NotNull(message = "Informe a liga")
	private String alloy;

	@NotNull(message = "Informe a polegada")
	private String alloyPol;

	@NotNull(message = "Informe a metragem")
	private String alloyFootage;

	@NotNull(message = "Campo requerido")
	@Positive(message = "A quantidade recebida deve ser positiva")
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal quantity;

	@NotNull(message = "Campo requerido")
	@Positive(message = "O valor unitário deve ser positivo")
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal price;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalValue;

	private String observation;

	public DispatchItemDTO() {
	}

	public DispatchItemDTO(Long dispatchId, Long numTicketId, Integer itemSequence, Long partnerId,
			String partnerName, String documentNumber, Long productCode, String productDescription, String typeDispatch,
			String alloy, String alloyPol, String alloyFootage, BigDecimal quantity, BigDecimal price,
			BigDecimal totalValue, String observation) {
		this.dispatchId = dispatchId;
		this.numTicketId = numTicketId;
		this.itemSequence = itemSequence;
		this.partnerId = partnerId;
		this.partnerName = partnerName;
		this.documentNumber = documentNumber;
		this.productCode = productCode;
		this.productDescription = productDescription;
		this.typeDispatch = typeDispatch;
		this.alloy = alloy;
		this.alloyPol = alloyPol;
		this.alloyFootage = alloyFootage;
		this.quantity = quantity;
		this.price = price;
		this.totalValue = totalValue;
		this.observation = observation;
	}

	public DispatchItemDTO(DispatchItem entity) {
		dispatchId = entity.getDispatch().getId();
		numTicketId = entity.getDispatch().getNumTicket();
		itemSequence = entity.getItemSequence();
		partnerId = entity.getPartner().getId();
		partnerName = entity.getPartner().getName();
		productCode = entity.getProduct().getProductCode();
		productDescription = entity.getProduct().getDescription();
		documentNumber = entity.getDocumentNumber();
		typeDispatch = entity.getTypeDispatch().toString();
		alloy = entity.getAlloy().toString();
		alloyPol = entity.getAlloyPol().toString();
		alloyFootage = entity.getAlloyFootage().toString();
		quantity = entity.getQuantity();
		price = entity.getPrice();
		totalValue = entity.getTotalValue();
		observation = entity.getObservation();

	}

	public Long getDispatchId() {
		return dispatchId;
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

	public String getDocumentNumber() {
		return documentNumber;
	}

	public Long getProductCode() {
		return productCode;
	}

	public String getProductDescription() {
		return productDescription;
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
