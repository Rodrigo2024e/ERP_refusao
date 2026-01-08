package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;

public class ReceiptReportDTO {

	private Long id;

	private Long numTicket;

	private LocalDate dateTicket;

	private String numberPlate;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal netWeight;

	private List<ReceiptItemDTO> receiptItems;

	public ReceiptReportDTO() {

	}

	public ReceiptReportDTO(Long id, Long numTicket, LocalDate dateTicket, String numberPlate, BigDecimal netWeight,
			List<ReceiptItemDTO> receiptItems) {
		this.id = id;
		this.numTicket = numTicket;
		this.dateTicket = dateTicket;
		this.numberPlate = numberPlate;
		this.netWeight = netWeight;
		this.receiptItems = receiptItems;
	}

	public Long getId() {
		return id;
	}

	public Long getNumTicket() {
		return numTicket;
	}

	public LocalDate getDateTicket() {
		return dateTicket;
	}

	public String getNumberPlate() {
		return numberPlate;
	}

	public BigDecimal getNetWeight() {
		return netWeight;
	}

	public List<ReceiptItemDTO> getReceiptItems() {
		return receiptItems;
	}

}
