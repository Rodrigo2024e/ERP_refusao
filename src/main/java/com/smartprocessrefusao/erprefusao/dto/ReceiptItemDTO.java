package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.entities.ReceiptItem;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;
import com.smartprocessrefusao.erprefusao.projections.ReceiptItemReportProjection;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ReceiptItemDTO {

	private Long receiptId;

	private Integer itemSequence;

	@NotNull(message = "Informe o id do partner")
	private Long partnerId;

	private String partnerName;

	@NotNull(message = "Informe o tipo material")
	private Long materialCode;

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

	public ReceiptItemDTO(Long receiptId, Integer itemSequence, Long partnerId, String partnerName, Long materialCode,
			BigDecimal recoveryYield, String documentNumber, String typeReceipt, String typeCosts, BigDecimal quantity,
			BigDecimal price, BigDecimal totalValue, BigDecimal quantityMco, String observation) {
		this.receiptId = receiptId;
		this.itemSequence = itemSequence;
		this.partnerId = partnerId;
		this.partnerName = partnerName;
		this.materialCode = materialCode;
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
		this.partnerName = entity.getId().getPartner().getName();
		this.materialCode = entity.getId().getMaterial().getMaterialCode();
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

	public ReceiptItemDTO(ReceiptItemReportProjection p) {
		receiptId = p.getReceiptId();
		itemSequence = p.getItemSequence();
		partnerId = p.getPartnerId();
		partnerName = p.getPartnerName();
		materialCode = p.getMaterialCode();
		recoveryYield = p.getRecoveryYield();
		documentNumber = p.getDocumentNumber();
		typeReceipt = p.getTypeReceipt();
		typeCosts = p.getTypeCosts();
		quantity = p.getQuantity();
		price = p.getPrice();
		totalValue = p.getTotalValue();
		quantityMco = p.getQuantity();
		observation = p.getObservation();
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

	public String getPartnerName() {
		return partnerName;
	}

	public Long getMaterialCode() {
		return materialCode;
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
