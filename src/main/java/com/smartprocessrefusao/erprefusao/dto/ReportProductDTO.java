package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.Product;
import com.smartprocessrefusao.erprefusao.projections.ReportProductProjection;

public class ReportProductDTO {

	private Long id;
	private String typeMaterial;
	private String description;
	private Integer alloy;
	private Integer billetDiameter;
	private double BilletLength;

	public ReportProductDTO() {

	}

	public ReportProductDTO(Product entity) {
		id = entity.getId();
		typeMaterial = entity.getTypeMaterial().toString();
		description = entity.getDescription();
		alloy = entity.getAlloy();
		billetDiameter = entity.getBilletDiameter();
		BilletLength = entity.getBilletLength();

	}

	public ReportProductDTO(ReportProductProjection projection) {
		this.id = projection.getId();
		this.typeMaterial = projection.getTypeMaterial();
		this.description = projection.getDescription();
		this.alloy = projection.getAlloy();
		this.billetDiameter = projection.getBilletDiameter();
		this.BilletLength = projection.getBilletLength();

	}

	public Long getId() {
		return id;
	}

	public String getTypeMaterial() {
		return typeMaterial;
	}

	public String getDescription() {
		return description;
	}

	public Integer getAlloy() {
		return alloy;
	}

	public Integer getBilletDiameter() {
		return billetDiameter;
	}

	public double getBilletLength() {
		return BilletLength;
	}


}
