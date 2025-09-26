package com.smartprocessrefusao.erprefusao.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.smartprocessrefusao.erprefusao.audit.Auditable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_ticket")
public class Ticket extends Auditable<String> {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer numTicket;
	private LocalDate dateTicket;
	private String numberPlate;
	private BigDecimal netWeight;

	@OneToMany(mappedBy = "numTicket")
	private Set<ScrapReceipt> scrapReceipts = new HashSet<>();

	public Ticket() {

	}

	public Ticket(Integer numTicket,  LocalDate dateTicket, String numberPlate, BigDecimal netWeight) {
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
