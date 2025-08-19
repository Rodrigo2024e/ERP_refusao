package com.smartprocessrefusao.erprefusao.entities;

import com.smartprocessrefusao.erprefusao.projections.IdProjection;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_product")
public class Product extends Material implements IdProjection<Long> {
	private static final long serialVersionUID = 1L;

	private String description;
	private Integer alloy;
	private Integer billetDiameter;
	private Double billetLength;

	public Product() {

	}

	public Product(String description, Integer alloy, Integer billetDiameter, Double billetLength) {
		this.description = description;
		this.alloy = alloy;
		this.billetDiameter = billetDiameter;
		this.billetLength = billetLength;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAlloy() {
		return alloy;
	}

	public void setAlloy(Integer alloy) {
		this.alloy = alloy;
	}

	public Integer getBilletDiameter() {
		return billetDiameter;
	}

	public void setBilletDiameter(Integer billetDiameter) {
		this.billetDiameter = billetDiameter;
	}

	public Double getBilletLength() {
		return billetLength;
	}

	public void setBilletLength(Double billetLength) {
		this.billetLength = billetLength;
	}

}
