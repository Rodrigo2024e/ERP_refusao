package com.smartprocessrefusao.erprefusao.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_dispatch")
public class Dispatch extends Ticket implements Serializable {
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "dispatch", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DispatchItem> dispatchItems = new ArrayList<>();

	public Dispatch() {

	}

	public Dispatch(Long id, Long numTicket, LocalDate dateTicket, String numberPlate, BigDecimal netWeight) {
		super(id, numTicket, dateTicket, numberPlate, netWeight);
	}

	public List<DispatchItem> getDispatchItems() {
		return dispatchItems;
	}

}
