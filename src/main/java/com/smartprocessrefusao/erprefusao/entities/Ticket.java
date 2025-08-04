package com.smartprocessrefusao.erprefusao.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_ticket")
public class Ticket {

	@Id
	private Integer numTicket;

	private LocalDate dateTicket;
	private String numberPlate;
	private Double netWeight;

	@OneToMany(mappedBy = "numTicket")
	private List<Movement> movements = new ArrayList<>();

	public Ticket() {

	}

	public Ticket(Integer numTicket, LocalDate dateTicket, String numberPlate, Double netWeight) {
		this.numTicket = numTicket;
		this.dateTicket = dateTicket;
		this.numberPlate = numberPlate;
		this.netWeight = netWeight;
	}

	public Integer getNumTicket() {
		return numTicket;
	}

	public void setNumTicket(Integer numTicket) {
		this.numTicket = numTicket;
	}

	public LocalDate getDateTicket() {
		return dateTicket;
	}

	public void setDateTicket(LocalDate dateTicket) {
		this.dateTicket = dateTicket;
	}

	public String getNumberPlate() {
		return numberPlate;
	}

	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
	}

	public Double getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	public List<Movement> getMovements() {
		return movements;
	}

	public void setMovements(List<Movement> movements) {
		this.movements = movements;
	}

	@Override
	public int hashCode() {
		return Objects.hash(numTicket);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		return Objects.equals(numTicket, other.numTicket);
	}

}
