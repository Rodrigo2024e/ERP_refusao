package com.smartprocessrefusao.erprefusao.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.smartprocessrefusao.erprefusao.audit.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_ticket")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Ticket extends Auditable<String> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private Long numTicket;

	@Column(nullable = false)
	private LocalDate dateTicket;
	
	private String numberPlate;

	private BigDecimal netWeight;

	public Ticket() {

	}

	public Ticket(Long id, Long numTicket, LocalDate dateTicket, String numberPlate, BigDecimal netWeight) {
		this.id = id;
		this.numTicket = numTicket;
		this.dateTicket = dateTicket;
		this.numberPlate = numberPlate;
		this.netWeight = netWeight;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumTicket() {
		return numTicket;
	}

	public void setNumTicket(Long numTicket) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id, numTicket);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		return Objects.equals(id, other.id) && Objects.equals(numTicket, other.numTicket);
	}

}
