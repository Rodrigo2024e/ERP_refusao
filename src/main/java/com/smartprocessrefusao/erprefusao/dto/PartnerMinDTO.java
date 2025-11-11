package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.Partner;

public class PartnerMinDTO {

	private Long id;
	private String name;

	public PartnerMinDTO() {

	}

	public PartnerMinDTO(Long id, String name) {
		this.id = id;
		this.name = name;

	}

	public PartnerMinDTO(Partner entity) {
		id = entity.getId();
		name = entity.getName();

	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
