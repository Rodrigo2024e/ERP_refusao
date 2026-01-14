package com.smartprocessrefusao.erprefusao.enumerados;

public enum TypeMaterial {

	SCRAP(1L, "SCRAP"), SUPPLIES(2L, "SUPPLIES"),
	MISC_MATERIALS(3L, "MISC_MATERIALS");

	private final Long id;
	private final String description;

	TypeMaterial(Long id, String description) {
		this.id = id;
		this.description = description;
	}

	public static TypeMaterial fromId(Long id) {
		for (TypeMaterial type : values()) {
			if (type.getId().equals(id)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Id inválido para TypeMaterial: " + id);
	}

	public static TypeMaterial fromDescription(String description) {
		for (TypeMaterial type : values()) {
			if (type.getDescription().equalsIgnoreCase(description)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Descrição inválida para TypeMaterial: " + description);
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
}
