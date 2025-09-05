package com.smartprocessrefusao.erprefusao.enumerados;

public enum TypeTransactionReceipt {

	SENT_FOR_PROCESSING(1L, "SENT_FOR_PROCESSING"), BUY(2L, "BUY"), SALES_RETURN(3L, "SALES_RETURN"),
	SERVICE_RETURN(4L, "SERVICE_RETURN");

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
