package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.entities.ReceiptItem;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ReceiptItemDTO {

	private Long receiptId;

	private Integer itemSequence;

	@NotNull(message = "Informe o id do partner")
	private Long partnerId;

	@NotNull(message = "Informe o tipo material")
	private Long code;

	@NotNull(message = "Campo requerido")
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal recoveryYield;

	private String documentNumber;

	@NotNull(message = "Informe o tipo do recebimento")
	private String typeReceipt;

	@NotNull(message = "Informe o tipo de custo")
	private String typeCosts;

	@NotNull(message = "A quantidade não pode ser nula")
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal quantity;

	@NotNull(message = "Campo requerido")
	@Positive(message = "O valor unitário deve ser positivo")
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal price;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalValue;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal quantityMco;

	private String observation;

	public ReceiptItemDTO() {
	}

	public ReceiptItemDTO(Long receiptId, Integer itemSequence, Long partnerId, Long code,
			BigDecimal recoveryYield, String documentNumber, String typeReceipt, String typeCosts, BigDecimal quantity,
			BigDecimal price, BigDecimal totalValue, BigDecimal quantityMco, String observation) {

		this.receiptId = receiptId;
		this.itemSequence = itemSequence;
		this.partnerId = partnerId;
		this.code = code;
		this.recoveryYield = recoveryYield;
		this.documentNumber = documentNumber;
		this.typeReceipt = typeReceipt;
		this.typeCosts = typeCosts;
		this.quantity = quantity;
		this.price = price;
		this.totalValue = totalValue;
		this.quantityMco = quantityMco;
		this.observation = observation;
	}

	public ReceiptItemDTO(ReceiptItem entity) {
		this.receiptId = entity.getId().getReceipt().getId();
		this.itemSequence = entity.getId().getItemSequence();
		this.partnerId = entity.getId().getPartner().getId();
		this.code = entity.getId().getMaterial().getCode();
		this.recoveryYield = entity.getRecoveryYield();
		this.documentNumber = entity.getDocumentNumber();
		this.typeReceipt = entity.getTypeReceipt().toString();
		this.typeCosts = entity.getTypeCosts().toString();
		this.quantity = entity.getQuantity();
		this.price = entity.getPrice();
		this.totalValue = entity.getTotalValue();
		this.quantityMco = entity.getQuantityMco();
		this.observation = entity.getObservation();
	}

	public Long getReceiptId() {
		return receiptId;
	}

	public Integer getItemSequence() {
		return itemSequence;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public Long getCode() {
		return code;
	}

	public BigDecimal getRecoveryYield() {
		return recoveryYield;
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

	public BigDecimal getQuantityMco() {
		return quantityMco;
	}

	public String getObservation() {
		return observation;
	}

}
