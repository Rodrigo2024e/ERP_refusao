package com.smartprocessrefusao.erprefusao.enumerados;

public enum TypeCosts {

	EXPENSES(1L, "EXPENSES"), DIRECT_COSTS(2L, "DIRECT_COSTS"), INDIRECT_COSTS(3L, "INDIRECT_COSTS"),
	INVESTIMENT(4L, "INVESTIMENT"), NO_COSTS(5L, "NO_COSTS"), OTHERS(6L, "OTHERS");

	private final Long id;
	private final String description;

	TypeCosts(Long id, String description) {
		this.id = id;
		this.description = description;
	}

	public static TypeCosts fromId(Long id) {
		for (TypeCosts type : values()) {
			if (type.getId().equals(id)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Id inválido para TypeCosts: " + id);
	}

	public static TypeCosts fromDescription(String description) {
		for (TypeCosts type : values()) {
			if (type.getDescription().equalsIgnoreCase(description)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Descrição inválida para TypeCosts: " + description);
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
}
