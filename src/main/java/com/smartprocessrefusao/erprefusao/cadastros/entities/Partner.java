package com.smartprocessrefusao.erprefusao.cadastros.entities;

import com.smartprocessrefusao.erprefusao.projections.IdProjection;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table (name = "tb_partner")
public class Partner extends People implements IdProjection<Long> {
	private static final long serialVersionUID = 1L;

	private String cnpj;
	private String ie;
	private Boolean supplier;
	private Boolean client;
	private Boolean active;
	
	public Partner() {
		
	}

	public Partner(Long id, String people, String email, String cellPhone, String telephone, 
			Address address, String cnpj, String ie, Boolean supplier, Boolean client, Boolean active) {
		super(id, people, email, cellPhone, telephone, address);
		this.cnpj = cnpj;
		this.ie = ie;
		this.supplier = supplier;
		this.client = client;
		this.active = active;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public Boolean getSupplier() {
		return supplier;
	}

	public void setSupplier(Boolean supplier) {
		this.supplier = supplier;
	}

	public Boolean getClient() {
		return client;
	}

	public void setClient(Boolean client) {
		this.client = client;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
}
