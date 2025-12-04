package com.smartprocessrefusao.erprefusao.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_inventory")
public class Inventory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate dateInventory;
	private String description;
	private String observation;

	@ManyToOne
	@JoinColumn(name = "receipt_id")
	private Receipt receipt;

	@OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
	private List<InventoryItem> items = new ArrayList<>();

	public Inventory() {
	}

	public Inventory(Long id, LocalDate dateInventory, String description, String observation, Receipt receipt) {
		this.id = id;
		this.dateInventory = dateInventory;
		this.description = description;
		this.observation = observation;
		this.receipt = receipt;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDateInventory() {
		return dateInventory;
	}

	public void setDateInventory(LocalDate dateInventory) {
		this.dateInventory = dateInventory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

	public List<InventoryItem> getItems() {
		return items;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inventory other = (Inventory) obj;
		return Objects.equals(id, other.id);
	}

}
