package com.smartprocessrefusao.erprefusao.enumerados;

public enum TypeTransactionReceipt {

	SENT_FOR_PROCESSING(1L, "SENT_FOR_PROCESSING"), PURCHASE(2L, "PURCHASE") ,
	SALES_SCRAP(3L, "SALES_SCRAP"), SCRAP_SALES_RETURN(4L, "SCRAP_SALES_RETURN"), ADJUSTMENT_ENTRY(5L, "ADJUSTMENT_ENTRY"), ADJUSTMENT_EXIT(6L, "ADJUSTMENT_EXIT");

	private final Long id;
	private final String description;

	TypeTransactionReceipt(Long id, String description) {
		this.id = id;
		this.description = description;
	}

	public static TypeTransactionReceipt fromId(Long id) {
		for (TypeTransactionReceipt type : values()) {
			if (type.getId().equals(id)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Id inválido para TypeTransactionReceipt: " + id);
	}

	public static TypeTransactionReceipt fromDescription(String description) {
		for (TypeTransactionReceipt type : values()) {
			if (type.getDescription().equalsIgnoreCase(description)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Descrição inválida para TypeTransactionReceipt: " + description);
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
}
