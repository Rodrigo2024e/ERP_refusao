package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.IntegerBrazilianSerializerWithoutDecimal;
import com.smartprocessrefusao.erprefusao.projections.ProductDispatchProjection;

public class ReportProductDispatchDTO {

	private Long id;

	@JsonSerialize(using = IntegerBrazilianSerializerWithoutDecimal.class)
	private Integer numTicketId;

	private Long partnerId;
	private String partnerName;

	private String transactionDescription;

	private Long productId;
	private String productDescription;

	private Integer alloy;

	private Integer billetDiameter;

	private Double billetLength;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal amountProduct;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal unitValue;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal totalValue;

	public ReportProductDispatchDTO() {

	}

	public ReportProductDispatchDTO(Long id, Integer numTicketId, Long partnerId, String partnerName,
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

	public ReportProductDispatchDTO(ProductDispatchProjection projection) {
		id = projection.getId();
		numTicketId = projection.getNumTicketId();

		if (projection.getPartnerId() != null) {
			partnerId = projection.getPartnerId();
			partnerName = projection.getPartnerName();
		}

		if (projection.getTransactionDescription() != null) {
			transactionDescription = projection.getTransactionDescription();

		}

		if (projection.getProductId() != null) {
			productId = projection.getProductId();
			productDescription = projection.getProductDescription();
			alloy = projection.getAlloy();
			billetDiameter = projection.getBilletDiameter();
			billetLength = projection.getBilletLength();

		}

		amountProduct = projection.getAmountProduct();
		unitValue = projection.getUnitValue();
		totalValue = projection.getTotalValue();

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
