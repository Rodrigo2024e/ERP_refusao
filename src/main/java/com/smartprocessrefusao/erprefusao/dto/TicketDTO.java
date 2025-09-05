package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.entities.Ticket;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.IntegerBrazilianSerializerWithoutDecimal;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TicketDTO {

	private Instant moment;
	@Column(unique = true)
	@JsonSerialize(using = IntegerBrazilianSerializerWithoutDecimal.class)
	private Integer numTicket;

	@NotNull(message = "Campo requerido")
	private LocalDate dateTicket;

	@Size(min = 8, max = 8, message = "Informar a placa do veículo no formato (ABC-1234)")
	private String numberPlate;

	@NotNull(message = "Campo requerido")
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal netWeight;

	public TicketDTO() {

	}

	public TicketDTO(Instant moment, Integer numTicket, LocalDate dateTicket, String numberPlate,
			BigDecimal netWeight) {
		this.moment = moment;
		this.numTicket = numTicket;
		this.dateTicket = dateTicket;
		this.numberPlate = numberPlate;
		this.netWeight = netWeight;
	}

	public TicketDTO(Ticket entity) {
		moment = entity.getMoment();
		numTicket = entity.getNumTicket();
		dateTicket = entity.getDateTicket();
		numberPlate = entity.getNumberPlate();
		netWeight = entity.getNetWeight();

	}

	public Instant getMoment() {
		return moment;
	}

	public Integer getNumTicket() {
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

}
