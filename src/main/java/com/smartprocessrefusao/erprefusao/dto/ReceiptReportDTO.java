package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;
import com.smartprocessrefusao.erprefusao.projections.ReceiptReportProjection;

public class ReceiptReportDTO {

	private Long numTicket;

	private LocalDate date_ticket;

	private String number_plate;

	private String document_number;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal net_weight;

	private String type_receipt;

	private String type_costs;

	private Long partner_id;

	private String name;

	private Long item_sequence;

	private Long material_id;

	private Long code;

	private String description;

	private BigDecimal quantity;

	private BigDecimal recovery_yield;

	private BigDecimal price;

	@JsonProperty("total_value")
	private BigDecimal totalValue;

	private BigDecimal quantity_mco;

	private String observation;

	public ReceiptReportDTO() {

	}

	public ReceiptReportDTO(Long numTicket, LocalDate date_ticket, String number_plate, String document_number,
			BigDecimal net_weight, String type_receipt, String type_costs, Long partner_id, String name,
			Long item_sequence, Long material_id, Long code, String description, BigDecimal quantity,
			BigDecimal recovery_yield, BigDecimal price, BigDecimal totalValue, BigDecimal quantity_mco,
			String observation) {
		this.numTicket = numTicket;
		this.date_ticket = date_ticket;
		this.number_plate = number_plate;
		this.document_number = document_number;
		this.net_weight = net_weight;
		this.type_receipt = type_receipt;
		this.type_costs = type_costs;
		this.partner_id = partner_id;
		this.name = name;
		this.item_sequence = item_sequence;
		this.material_id = material_id;
		this.code = code;
		this.description = description;
		this.quantity = quantity;
		this.recovery_yield = recovery_yield;
		this.price = price;
		this.totalValue = totalValue;
		this.quantity_mco = quantity_mco;
		this.observation = observation;
	}

	public ReceiptReportDTO(ReceiptReportProjection projection) {
		numTicket = projection.getNumTicket();
		date_ticket = projection.getDate_ticket();
		number_plate = projection.getNumber_plate();
		document_number = projection.getDocumentNumber();
		net_weight = projection.getNet_weight();
		type_receipt = projection.getType_receipt();
		type_costs = projection.getType_costs();
		partner_id = projection.getPartner_id();
		name = projection.getName();
		item_sequence = projection.getItem_sequence();
		material_id = projection.getMaterialId();
		code = projection.getCode();
		description = projection.getDescription();
		quantity = projection.getQuantity();
		recovery_yield = projection.getRecovery_yield();
		price = projection.getPrice();
		totalValue = projection.getTotal_value();
		quantity_mco = projection.getQuantity_mco();
		observation = projection.getObservation();
	}

	public Long getNumTicket() {
		return numTicket;
	}

	public LocalDate getDate_ticket() {
		return date_ticket;
	}

	public String getNumber_plate() {
		return number_plate;
	}

	public String getDocument_number() {
		return document_number;
	}

	public BigDecimal getNet_weight() {
		return net_weight;
	}

	public String getType_receipt() {
		return type_receipt;
	}

	public String getType_costs() {
		return type_costs;
	}

	public Long getPartner_id() {
		return partner_id;
	}

	public String getName() {
		return name;
	}

	public Long getItem_sequence() {
		return item_sequence;
	}

	public Long getMaterial_id() {
		return material_id;
	}

	public Long getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getRecovery_yield() {
		return recovery_yield;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public BigDecimal getQuantity_mco() {
		return quantity_mco;
	}

	public String getObservation() {
		return observation;
	}

}
