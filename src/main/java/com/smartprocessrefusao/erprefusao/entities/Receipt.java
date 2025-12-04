package com.smartprocessrefusao.erprefusao.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_receipt")
public class Receipt extends Ticket implements Serializable {
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "id.receipt", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ReceiptItem> receiptItems = new HashSet<>();

	@OneToMany(mappedBy = "receipt")
	private List<Inventory> inventories = new ArrayList<>();

	public Receipt() {
	}

	public Receipt(Long id, Long numTicket, LocalDate dateTicket, String numberPlate, BigDecimal netWeight) {
		super(id, numTicket, dateTicket, numberPlate, netWeight);
	}

	public Set<ReceiptItem> getReceiptItems() {
		return receiptItems;
	}

	public List<Inventory> getInventories() {
		return inventories;
	}

}
