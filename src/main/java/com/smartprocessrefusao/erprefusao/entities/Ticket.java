package com.smartprocessrefusao.erprefusao.entities;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_ticket")
public class Ticket {

	@Id
	private Integer numTicket;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant moment;
	private LocalDate dateTicket;
	private String numberPlate;
	private BigDecimal netWeight;

	@OneToMany(mappedBy = "numTicket")
	private Set<ScrapReceipt> scrapReceipts = new HashSet<>();

	public Ticket() {

	}

	public Ticket(Integer numTicket, Instant moment, LocalDate dateTicket, String numberPlate, BigDecimal netWeight) {
		this.numTicket = numTicket;
		this.moment = moment;
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

	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
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

	public BigDecimal getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(BigDecimal netWeight) {
		this.netWeight = netWeight;
	}

	public Set<ScrapReceipt> getScrapReceipts() {
		return scrapReceipts;
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
