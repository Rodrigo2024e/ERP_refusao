package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.entities.ProductDispatch;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.IntegerBrazilianSerializerWithoutDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductDispatchDTO {

	private Long id;

	@NotNull(message = "Informar o número do ticket associado")
	@JsonSerialize(using = IntegerBrazilianSerializerWithoutDecimal.class)
	private Integer numTicketId;

	@NotNull(message = "Informar o id do parceiro")
	private Long partnerId;
	private String partnerName;

	@NotNull(message = "Informar o tipo de operação")
	private String transactionDescription;

	@NotNull(message = "Informar o id do produto")
	private Long productId;
	private String productDescription;

	private Integer alloy;

	private Integer billetDiameter;

	private Double billetLength;

	@NotNull(message = "Campo requerido")
	@Positive(message = "A quantidade recebida deve ser positiva")
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal amountProduct;

	@NotNull(message = "Campo requerido")
	@Positive(message = "O valor unitário deve ser positivo")
	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal unitValue;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalValue;

	public ProductDispatchDTO() {

	}

	public ProductDispatchDTO(Long id, Integer numTicketId, Long partnerId, String partnerName,
			String transactionDescription, Long productId, String productDescription, Integer alloy,
			Integer billetDiameter, Double billetLength, BigDecimal amountProduct, BigDecimal unitValue,
			BigDecimal totalValue) {
		this.id = id;
		this.numTicketId = numTicketId;
		this.partnerId = partnerId;
		this.partnerName = partnerName;
		this.transactionDescription = transactionDescription;
		this.productId = productId;
		this.productDescription = productDescription;
		this.alloy = alloy;
		this.billetDiameter = billetDiameter;
		this.billetLength = billetLength;
		this.amountProduct = amountProduct;
		this.unitValue = unitValue;
		this.totalValue = totalValue;
	}

	public ProductDispatchDTO(ProductDispatch entity) {
		id = entity.getId();
		numTicketId = entity.getNumTicket().getNumTicket();
		partnerId = entity.getPartner().getId();
		partnerName = entity.getPartner().getName();
		transactionDescription = entity.getTransaction().getDescription();
		productId = entity.getProduct().getId();
		productDescription = entity.getProduct().getDescription();
		alloy = entity.getProduct().getAlloy();
		billetDiameter = entity.getProduct().getBilletDiameter();
		billetLength = entity.getProduct().getBilletLength();
		amountProduct = entity.getAmountProduct();
		unitValue = entity.getUnitValue();
		totalValue = entity.getTotalValue();

	}

	public Long getId() {
		return id;
	}

	public Integer getNumTicketId() {
		return numTicketId;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public String getTransactionDescription() {
		return transactionDescription;
	}

	public Long getProductId() {
		return productId;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public Integer getAlloy() {
		return alloy;
	}

	public Integer getBilletDiameter() {
		return billetDiameter;
	}

	public Double getBilletLength() {
		return billetLength;
	}

	public BigDecimal getAmountProduct() {
		return amountProduct;
	}

	public BigDecimal getUnitValue() {
		return unitValue;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

}
