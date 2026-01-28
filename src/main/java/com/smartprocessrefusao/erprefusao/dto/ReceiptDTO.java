package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.entities.Receipt;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public class ReceiptDTO {

	private Long ticketId;

	@NotNull(message = "Informe o número do ticket")
	@Column(name = "numTicket", unique = true)
	private Long numTicket;

	@NotNull(message = "Informe a data do ticket")
	@PastOrPresent(message = "A data do produto não pode ser futura")
	private LocalDate dateTicket;

	@NotNull(message = "Informe a placa do veículo")
	private String numberPlate;

	@NotNull(message = "Informe o peso líquido do ticket")
    @JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal netWeight;

	@NotNull(message = "Informe os itens de recebimento")
	private List<ReceiptItemDTO> receiptItems = new ArrayList<>();

	public ReceiptDTO() {
	}

	public ReceiptDTO(Long ticketId, Long numTicket, LocalDate dateTicket, String numberPlate, BigDecimal netWeight) {
		this.ticketId = ticketId;
		this.numTicket = numTicket;
		this.dateTicket = dateTicket;
		this.numberPlate = numberPlate;
		this.netWeight = netWeight;

	}

	public ReceiptDTO(Receipt entity) {
		ticketId = entity.getId();
		numTicket = entity.getNumTicket();
		numTicket = entity.getNumTicket();
		dateTicket = entity.getDateTicket();
		numberPlate = entity.getNumberPlate();
		netWeight = entity.getNetWeight();

		receiptItems = entity.getReceiptItems().stream().map(item -> new ReceiptItemDTO(item))
				.collect(Collectors.toList());

	}

	public Long getTicketId() {
		return ticketId;
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
