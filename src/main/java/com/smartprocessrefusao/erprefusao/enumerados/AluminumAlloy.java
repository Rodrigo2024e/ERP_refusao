package com.smartprocessrefusao.erprefusao.enumerados;

public enum AluminumAlloy {

	AL6005(1L, "AL6005"), AL6060(2L, "AL6060"), AL6063(3L, "AL6063"), AL6351(4L, "AL6351");

	private final Long id;
	private final String description;

	AluminumAlloy(Long id, String description) {
		this.id = id;
		this.description = description;
	}

	public static AluminumAlloy fromId(Long id) {
		for (AluminumAlloy alloy : values()) {
			if (alloy.getId().equals(id)) {
				return alloy;
			}
		}
		throw new IllegalArgumentException("Id inválido para liga: " + id);
	}

	public static AluminumAlloy fromDescription(String description) {
		for (AluminumAlloy alloy : values()) {
			if (alloy.getDescription().equalsIgnoreCase(description)) {
				return alloy;
			}
		}
		throw new IllegalArgumentException("Descrição inválida para liga: " + description);
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
}
