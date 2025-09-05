package com.smartprocessrefusao.erprefusao.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.BigDecimalBrazilianSerializer;
import com.smartprocessrefusao.erprefusao.formatBigDecimal.IntegerBrazilianSerializerWithoutDecimal;
import com.smartprocessrefusao.erprefusao.projections.ReportTicketProjection;

public class ReportTicketDTO {

	private Instant moment;

	@JsonSerialize(using = IntegerBrazilianSerializerWithoutDecimal.class)
	private Integer numTicket;

	private LocalDate dateTicket;

	private String numberPlate;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal netWeight;

	@JsonSerialize(using = IntegerBrazilianSerializerWithoutDecimal.class)
	private Integer numTicketId;
	private Long partnerId;

	private String namePartner;

	private Long scrapId;

	private String scrapDescription;

	@JsonSerialize(using = BigDecimalBrazilianSerializer.class)
	private BigDecimal amountScrap;

	public ReportTicketDTO() {

	}

	public ReportTicketDTO(Instant moment, Integer numTicket, LocalDate dateTicket, String numberPlate,
			BigDecimal netWeight, Integer numTicketId, Long partnerId, String namePartner, Long scrapId,
			String scrapDescription, BigDecimal amountScrap) {
		this.moment = moment;
		this.numTicket = numTicket;
		this.dateTicket = dateTicket;
		this.numberPlate = numberPlate;
		this.netWeight = netWeight;
		this.numTicketId = numTicketId;
		this.partnerId = partnerId;
		this.namePartner = namePartner;
		this.scrapId = scrapId;
		this.scrapDescription = scrapDescription;
		this.amountScrap = amountScrap;
	}

	public ReportTicketDTO(ReportTicketProjection projection) {

		moment = projection.getMoment();
		numTicket = projection.getNumTicket();
		dateTicket = projection.getDateTicket();
		numberPlate = projection.getNumberPlate();
		netWeight = projection.getNetWeight();
		numTicketId = projection.getNumTicket();
		partnerId = projection.getPartnerId();
		namePartner = projection.getNamePartner();
		scrapId = projection.getScrapId();
		scrapDescription = projection.getScrapDescription();
		amountScrap = projection.getAmountScrap();
	}

	public Instant getMoment() {
		return moment;
	}

	public Integer getNumTicket() {
		return numTicket;
	}

	public LocalDate getDateTicket() {
		return dateTicket;
	}

	public String getNumberPlate() {
		return numberPlate;
	}

	public BigDecimal getNetWeight() {
		return netWeight;
	}

	public Integer getNumTicketId() {
		return numTicketId;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public String getNamePartner() {
		return namePartner;
	}

	public Long getScrapId() {
		return scrapId;
	}

	public String getScrapDescription() {
		return scrapDescription;
	}

	public BigDecimal getAmountScrap() {
		return amountScrap;
	}

}
