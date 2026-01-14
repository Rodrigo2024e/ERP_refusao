package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;

public class DispatchReportDTO {

	private Long dispatchId;

	private Long numTicketId;

	private LocalDate dateTicket;

	private String numberPlate;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal netWeight;

	private List<DispatchItemReportDTO> dispatchItems;

	public DispatchReportDTO() {

	}

	public DispatchReportDTO(Long dispatchId, Long numTicketId, LocalDate dateTicket, String numberPlate,
			BigDecimal net_weight, List<DispatchItemReportDTO> dispatchItems) {
		this.dispatchId = dispatchId;
		this.numTicketId = numTicketId;
		this.dateTicket = dateTicket;
		this.numberPlate = numberPlate;
		this.netWeight = net_weight;
		this.dispatchItems = dispatchItems;
	}

	public Long getDispatchId() {
		return dispatchId;
	}

	public Long getNumTicketId() {
		return numTicketId;
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

	public List<DispatchItemReportDTO> getDispatchItems() {
		return dispatchItems;
	}

}
