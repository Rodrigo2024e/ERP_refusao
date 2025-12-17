package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.Machine;

import jakarta.validation.constraints.NotNull;

public class MachineDTO {

	private Long id;
	@NotNull(message = "Informe a descrição da máquina")
	private String description;

	@NotNull(message = "Informe a marca da máquina")
	private String brand;

	@NotNull(message = "Informe o modelo da máquina")
	private String model;

	@NotNull(message = "Informe o ano de fabricação da máquina")
	private Integer manufacturingYear;

	@NotNull(message = "Informe a capacidade produtiva da máquina")
	private Double capacity;

	public MachineDTO() {
	}

	public MachineDTO(Long id, String description, String brand, String model, Integer manufacturingYear,
			Double capacity) {
		this.id = id;
		this.description = description;
		this.brand = brand;
		this.model = model;
		this.manufacturingYear = manufacturingYear;
		this.capacity = capacity;
	}

	public MachineDTO(Machine entity) {
		id = entity.getId();
		description = entity.getDescription();
		brand = entity.getBrand();
		model = entity.getModel();
		manufacturingYear = entity.getManufacturingYear();
		capacity = entity.getCapacity();
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public String getBrand() {
		return brand;
	}

	public String getModel() {
		return model;
	}

	public Integer getManufacturingYear() {
		return manufacturingYear;
	}

	public Double getCapacity() {
		return capacity;
	}

}
