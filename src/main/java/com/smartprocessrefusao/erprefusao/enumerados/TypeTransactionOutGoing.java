package com.smartprocessrefusao.erprefusao.enumerados;

public enum TypeTransactionOutGoing {

	JOB_RETURN_TO_CUSTOMER(1L, "JOB_RETURN_TO_CUSTOMER"), SALE(2L, "SALE"), RETURN_TO_SUPPLIER(3L, "RETURN_TO_SUPPLIER"),
	REWORK_DELIVERY(4L, "REWORK_DELIVERY"), RENT(5L, "RENT"), EXIT_ADJUSTMENT(6L, "EXIT_ADJUSTMENT");

	private final Long id;
	private final String description;

	TypeTransactionOutGoing(Long id, String description) {
		this.id = id;
		this.description = description;
	}

	public static TypeTransactionOutGoing fromId(Long id) {
		for (TypeTransactionOutGoing type : values()) {
			if (type.getId().equals(id)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Id inválido para TypeTransactionReceipt: " + id);
	}

	public static TypeTransactionOutGoing fromDescription(String description) {
		for (TypeTransactionOutGoing type : values()) {
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
