package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.entities.MeltingItem;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class MeltingItemDTO {

	private Long meltingId;
	private Integer itemSequence;

	@NotNull(message = "Informe o tipo material")
	private Long materialCode;
	private String description;

	@NotNull(message = "A quantidade não pode ser nula")
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	@Column(nullable = false)
	private BigDecimal quantity = BigDecimal.ZERO;

	@NotNull(message = "Campo requerido")
	@Positive(message = "O valor unitário deve ser positivo")
	@Column(nullable = false)
	private BigDecimal averagePrice = BigDecimal.ZERO;

	@Column(nullable = false)
	private BigDecimal totalValue = BigDecimal.ZERO;

	@NotNull(message = "Informe o rendimento metálico")
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	@Column(nullable = false)
	private BigDecimal averageRecoveryYield = BigDecimal.ZERO;

	private BigDecimal quantityMco;
	private BigDecimal slagWeight;

	public MeltingItemDTO() {
	}

	public MeltingItemDTO(Long meltingId, Integer itemSequence, Long materialCode, String description,
			BigDecimal quantity, BigDecimal averagePrice, BigDecimal totalValue, BigDecimal averageRecoveryYield,
			BigDecimal quantityMco, BigDecimal slagWeight) {
		this.meltingId = meltingId;
		this.itemSequence = itemSequence;
		this.materialCode = materialCode;
		this.description = description;
		this.quantity = quantity;
		this.averagePrice = averagePrice;
		this.totalValue = totalValue;
		this.averageRecoveryYield = averageRecoveryYield;
		this.quantityMco = quantityMco;
		this.slagWeight = slagWeight;
	}

	public MeltingItemDTO(MeltingItem entity) {
		meltingId = entity.getId().getMelting().getId();
		itemSequence = entity.getId().getItemSequence();
		materialCode = entity.getId().getMaterial().getMaterialCode();
		description = entity.getId().getMaterial().getDescription();
		quantity = entity.getQuantity();
		averagePrice = entity.getAveragePrice();
		totalValue = entity.getTotalValue();
		averageRecoveryYield = entity.getAverageRecoveryYield();
		quantityMco = entity.getQuantityMco();
		slagWeight = entity.getSlagWeight();
	}

	public Long getMeltingId() {
		return meltingId;
	}

	public Integer getItemSequence() {
		return itemSequence;
	}

	public Long getMaterialCode() {
		return materialCode;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getAveragePrice() {
		return averagePrice;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public BigDecimal getAverageRecoveryYield() {
		return averageRecoveryYield;
	}

	public BigDecimal getQuantityMco() {
		return quantityMco;
	}

	public BigDecimal getSlagWeight() {
		return slagWeight;
	}

}
