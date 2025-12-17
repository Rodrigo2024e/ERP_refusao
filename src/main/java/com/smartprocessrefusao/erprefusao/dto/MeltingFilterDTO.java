package com.smartprocessrefusao.erprefusao.dto;

import java.time.LocalDate;

public class MeltingFilterDTO {

	private Long partnerId;
	private Integer meltingNumber;
	private LocalDate startDate;
	private LocalDate endDate;

	public MeltingFilterDTO() {
	}

	public MeltingFilterDTO(Long partnerId, Integer meltingNumber, LocalDate startDate, LocalDate endDate) {
		this.partnerId = partnerId;
		this.meltingNumber = meltingNumber;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public Integer getMeltingNumber() {
		return meltingNumber;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

}
