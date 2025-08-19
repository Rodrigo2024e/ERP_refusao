package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.smartprocessrefusao.erprefusao.entities.Ticket;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TicketDTO {

	@Column(unique = true)
	private Integer numTicket;

	@NotNull(message = "Campo requerido")
	private LocalDate dateTicket;

	@Size(min = 8, max = 8, message = "Informar a placa do veículo no formato (ABC-1234)")
	private String numberPlate;

	@NotNull(message = "Campo requerido")
	private BigDecimal netWeight;

	public TicketDTO() {

	}

	public TicketDTO(Integer numTicket, LocalDate dateTicket, String numberPlate, BigDecimal netWeight) {
		this.numTicket = numTicket;
		this.dateTicket = dateTicket;
		this.numberPlate = numberPlate;
		this.netWeight = netWeight;
	}

	public TicketDTO(Ticket entity) {
		numTicket = entity.getNumTicket();
		dateTicket = entity.getDateTicket();
		numberPlate = entity.getNumberPlate();
		netWeight = entity.getNetWeight();

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
