package com.smartprocessrefusao.erprefusao.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "tb_ticket")
public class Ticket {

	private LocalDate dataTicket;
	@Id
	private Integer numTicket;
	private Double pesoBruto;
	private Double pesoDesconto;
	private Double pesoLiquido;
	
	public Ticket () {
		
	}

	public Ticket(LocalDate dataTicket, Integer numTicket, Double pesoBruto, Double pesoDesconto, Double pesoLiquido) {
		this.dataTicket = dataTicket;
		this.numTicket = numTicket;
		this.pesoBruto = pesoBruto;
		this.pesoDesconto = pesoDesconto;
		this.pesoLiquido = pesoLiquido;
	}

	public LocalDate getDataTicket() {
		return dataTicket;
	}

	public void setDataTicket(LocalDate dataTicket) {
		this.dataTicket = dataTicket;
	}

	public Integer getNumTicket() {
		return numTicket;
	}

	public void setNumTicket(Integer numTicket) {
		this.numTicket = numTicket;
	}

	public Double getPesoBruto() {
		return pesoBruto;
	}

	public void setPesoBruto(Double pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	public Double getPesoDesconto() {
		return pesoDesconto;
	}

	public void setPesoDesconto(Double pesoDesconto) {
		this.pesoDesconto = pesoDesconto;
	}

	public Double getPesoLiquido() {
		return pesoLiquido;
	}

	public void setPesoLiquido(Double pesoLiquido) {
		this.pesoLiquido = pesoLiquido;
	}
	
	
}
