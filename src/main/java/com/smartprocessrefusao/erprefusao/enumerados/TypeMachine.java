package com.smartprocessrefusao.erprefusao.enumerados;

public enum TypeMachine {

	FURNACE_1(1L, "FURNACE_1"), FURNACE_2(2L, "FURNACE_2") ,
	FURNACE_HO(3L, "FURNACE_HO");

	private final Long id;
	private final String description;

	TypeMachine(Long id, String description) {
		this.id = id;
		this.description = description;
	}

	public static TypeMachine fromId(Long id) {
		for (TypeMachine type : values()) {
			if (type.getId().equals(id)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Id inválido para TypeMachine: " + id);
	}

	public static TypeMachine fromDescription(String description) {
		for (TypeMachine type : values()) {
			if (type.getDescription().equalsIgnoreCase(description)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Descrição inválida para TypeMachine: " + description);
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
}
