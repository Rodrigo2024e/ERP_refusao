package com.smartprocessrefusao.erprefusao.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.smartprocessrefusao.erprefusao.entities.Inventory;

public class InventoryDTO {

	private Long id;

	private LocalDate dateInventory;

	private Long receiptId;

	private String description;

	private String observation;

	private List<InventoryItemDTO> items = new ArrayList<>();

	public InventoryDTO() {
	}

	public InventoryDTO(Long id, LocalDate dateInventory, Long receiptId, String description, String observation) {
		this.id = id;
		this.dateInventory = dateInventory;
		this.receiptId = receiptId;
		this.description = description;
		this.observation = observation;

	}

	public InventoryDTO(Inventory entity) {
		id = entity.getId();
		dateInventory = entity.getDateInventory();
		receiptId = entity.getReceipt().getId();
		description = entity.getDescription();
		observation = entity.getObservation();

		this.items = entity.getItems().stream().map(item -> new InventoryItemDTO(item)).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public LocalDate getDateInventory() {
		return dateInventory;
	}

	public Long getReceiptId() {
		return receiptId;
	}

	public String getDescription() {
		return description;
	}

	public String getObservation() {
		return observation;
	}

	public List<InventoryItemDTO> getItems() {
		return items;
	}

}
