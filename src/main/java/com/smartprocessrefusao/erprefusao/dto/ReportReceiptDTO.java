package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;
import com.smartprocessrefusao.erprefusao.projections.ReportReceiptProjection;

public class ReportReceiptDTO {

	private Long receipt_id;
	private Long item_sequence;

	private Long numTicket;

	private LocalDate date_ticket;

	private Long partner_id;

	private String name;

	private String number_plate;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal net_weight;

	private Long material_id;

	private String description;

	private String document_number;

	private String type_receipt;

	private String type_costs;

	private BigDecimal quantity;

	private BigDecimal price;

	private BigDecimal totalValue;

	private String observation;

	public ReportReceiptDTO() {

	}

	public ReportReceiptDTO(Long receipt_id, Long item_sequence, Long numTicket, LocalDate date_ticket,
			Long partner_id, String name, String number_plate, BigDecimal net_weight, Long material_id,
			String description, String document_number, String type_receipt, String type_costs, BigDecimal quantity,
			BigDecimal price, BigDecimal totalValue, String observation) {
		this.receipt_id = receipt_id;
		this.item_sequence = item_sequence;
		this.numTicket = numTicket;
		this.date_ticket = date_ticket;
		this.partner_id = partner_id;
		this.name = name;
		this.number_plate = number_plate;
		this.net_weight = net_weight;
		this.material_id = material_id;
		this.description = description;
		this.document_number = document_number;
		this.type_receipt = type_receipt;
		this.type_costs = type_costs;
		this.quantity = quantity;
		this.price = price;
		this.totalValue = totalValue;
		this.observation = observation;
	}

	public ReportReceiptDTO(ReportReceiptProjection projection) {
		receipt_id = projection.getReceipt_id();
		item_sequence = projection.getItem_sequence();
		numTicket = projection.getNumTicket();
		date_ticket = projection.getDate_ticket();
		partner_id = projection.getPartner_id();
		name = projection.getName();
		number_plate = projection.getNumber_plate();
		net_weight = projection.getNet_weight();
		material_id = projection.getMaterial_id();
		description = projection.getDescription();
		document_number = projection.getDocumentNumber();
		type_receipt = projection.getType_receipt();
		type_costs = projection.getType_costs();
		quantity = projection.getQuantity();
		price = projection.getPrice();
		totalValue = projection.getTotal_value();
		observation = projection.getObservation();
	}

	public Long getReceipt_id() {
		return receipt_id;
	}

	public Long getItem_sequence() {
		return item_sequence;
	}

	public Long getNumTicket() {
		return numTicket;
	}

	public LocalDate getDate_ticket() {
		return date_ticket;
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

	public String getType_receipt() {
		return type_receipt;
	}

	public String getType_costs() {
		return type_costs;
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
