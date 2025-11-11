package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;
import com.smartprocessrefusao.erprefusao.projections.ReportDispatchProjection;

public class ReportDispatchDTO {

	private Long numTicket;

	private LocalDate date_ticket;

	private Long item_sequence;

	private Long partner_id;

	private String name;

	private String number_plate;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal net_weight;

	private Long material_id;

	private String description;

	private String document_number;

	private String transactionDescription;

	private String alloy;

	private String alloyPol;

	private String alloyFootage;

	private BigDecimal quantity;

	private BigDecimal price;

	private BigDecimal totalValue;

	private String observation;

	public ReportDispatchDTO() {

	}

	public ReportDispatchDTO(Long numTicket, LocalDate date_ticket, Long item_sequence, Long partner_id, String name,
			String number_plate, BigDecimal net_weight, Long material_id, String description, String document_number,
			String transactionDescription, String alloy, String alloyPol, String alloyFootage, BigDecimal quantity,
			BigDecimal price, BigDecimal totalValue, String observation) {
		this.numTicket = numTicket;
		this.date_ticket = date_ticket;
		this.item_sequence = item_sequence;
		this.partner_id = partner_id;
		this.name = name;
		this.number_plate = number_plate;
		this.net_weight = net_weight;
		this.material_id = material_id;
		this.description = description;
		this.document_number = document_number;
		this.transactionDescription = transactionDescription;
		this.alloy = alloy;
		this.alloyPol = alloyPol;
		this.alloyFootage = alloyFootage;
		this.quantity = quantity;
		this.price = price;
		this.totalValue = totalValue;
		this.observation = observation;
	}

	public ReportDispatchDTO(ReportDispatchProjection projection) {
		numTicket = projection.getNumTicket();
		date_ticket = projection.getDate_ticket();
		item_sequence = projection.getItem_sequence();
		partner_id = projection.getPartner_id();
		name = projection.getName();
		number_plate = projection.getNumber_plate();
		net_weight = projection.getNet_weight();
		material_id = projection.getMaterial_id();
		description = projection.getDescription();
		document_number = projection.getDocumentNumber();
		transactionDescription = projection.getTransactionDescription();
		alloy = projection.getAlloy();
		alloyPol = projection.getAlloyPol();
		alloyFootage = projection.getAlloyFootage();
		quantity = projection.getQuantity();
		price = projection.getPrice();
		totalValue = projection.getTotal_value();
		observation = projection.getObservation();

	}

	public Long getNumTicket() {
		return numTicket;
	}

	public LocalDate getDate_ticket() {
		return date_ticket;
	}

	public Long getItem_sequence() {
		return item_sequence;
	}

	public Long getPartner_id() {
		return partner_id;
	}

	public String getName() {
		return name;
	}

	public String getNumber_plate() {
		return number_plate;
	}

	public BigDecimal getNet_weight() {
		return net_weight;
	}

	public Long getMaterial_id() {
		return material_id;
	}

	public String getDescription() {
		return description;
	}

	public String getDocument_number() {
		return document_number;
	}

	public String getTransactionDescription() {
		return transactionDescription;
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
