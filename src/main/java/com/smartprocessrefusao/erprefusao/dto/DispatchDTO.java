package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.smartprocessrefusao.erprefusao.entities.Dispatch;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public class DispatchDTO {

	private Long id;

	@NotNull(message = "Informe o número do ticket")
	@Column(name = "numTicket", unique = true)
	private Long numTicket;

	@NotNull(message = "Informe a data do ticket")
	@PastOrPresent(message = "A data do produto não pode ser futura")
	private LocalDate dateTicket;

	@NotNull(message = "Informe a placa do veículo")
	private String numberPlate;

	@NotNull(message = "Informe o peso líquido do ticket")
	private BigDecimal netWeight;

	@NotNull(message = "Informe os itens de recebimento")
	private List<DispatchItemDTO> dispatchItems = new ArrayList<>();

	public DispatchDTO() {

	}

	public DispatchDTO(Long id, Long numTicket, LocalDate dateTicket, String numberPlate, BigDecimal netWeight) {
		this.id = id;
		this.numTicket = numTicket;
		this.dateTicket = dateTicket;
		this.numberPlate = numberPlate;
		this.netWeight = netWeight;

	}

	public DispatchDTO(Dispatch entity) {
		id = entity.getId();
		numTicket = entity.getNumTicket();
		dateTicket = entity.getDateTicket();
		numberPlate = entity.getNumberPlate();
		netWeight = entity.getNetWeight();
		
		dispatchItems = entity.getDispatchItems().stream()
		        .map(item -> new DispatchItemDTO(item))
		        .collect(Collectors.toList());
		
	
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

	public List<DispatchItemDTO> getDispatchItems() {
		return dispatchItems;
	}


}