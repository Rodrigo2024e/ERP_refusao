package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.entities.ReceiptItem;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ReceiptItemDTO {

	private Long receiptId;

	private Long itemSequence;

	@NotNull(message = "Informe o id do partner")
	private Long partnerId;

	@NotNull(message = "Informe o tipo material")
	private Long materialId;

	private String documentNumber;

	@NotNull(message = "Informe o tipo do recebimento")
	private String typeReceipt;

	@NotNull(message = "Informe o tipo de custo")
	private String typeCosts;

	@NotNull(message = "Campo requerido")
	@Positive(message = "A quantidade recebida deve ser positiva")
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal quantity;

	@NotNull(message = "Campo requerido")
	@Positive(message = "O valor unitário deve ser positivo")
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal price;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalValue;

	private String observation;

	public ReceiptItemDTO() {
	}

	public ReceiptItemDTO(Long receiptId, Long itemSequence, Long partnerId, Long materialId, String documentNumber,
			String typeReceipt, String typeCosts, BigDecimal quantity, BigDecimal price, BigDecimal totalValue,
			String observation) {
		this.receiptId = receiptId;
		this.partnerId = partnerId;
		this.materialId = materialId;
		this.itemSequence = itemSequence;
		this.documentNumber = documentNumber;
		this.typeReceipt = typeReceipt;
		this.typeCosts = typeCosts;
		this.typeReceipt = typeReceipt;
		this.typeCosts = typeCosts;
		this.quantity = quantity;
		this.price = price;
		this.totalValue = totalValue;
		this.observation = observation;
	}

	public ReceiptItemDTO(ReceiptItem entity) {
		receiptId = entity.getId().getReceipt().getId();
		 this.itemSequence = entity.getId().getItemSequence();
		partnerId = entity.getId().getPartner().getId();
		materialId = entity.getId().getMaterial().getId();
		documentNumber = entity.getDocumentNumber();
		typeReceipt = entity.getTypeReceipt().toString();
		typeCosts = entity.getTypeCosts().toString();
		quantity = entity.getQuantity();
		price = entity.getPrice();
		totalValue = entity.getTotalValue();
		observation = entity.getObservation();

	}

	public Long getReceiptId() {
		return receiptId;
	}

	public Long getItemSequence() {
		return itemSequence;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public String getTypeReceipt() {
		return typeReceipt;
	}

	public String getTypeCosts() {
		return typeCosts;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public String getObservation() {
		return observation;
	}

}
