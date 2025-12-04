package com.smartprocessrefusao.erprefusao.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.smartprocessrefusao.erprefusao.projections.IdProjection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_partner")
public class Partner extends People implements IdProjection<Long> {
	private static final long serialVersionUID = 1L;

	private String cnpj;
	private String ie;
	private Boolean supplier;
	private Boolean client;
	private Boolean active;

	@OneToMany(mappedBy = "id.partner", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReceiptItem> receiptItems = new ArrayList<>();

	@OneToMany(mappedBy = "partner")
	private List<InventoryItem> InventoryItems = new ArrayList<>();

	public Partner() {

	}

	public Partner(Long id, String name, String email, String cellPhone, String telephone, Address address, String cnpj,
			String ie, Boolean supplier, Boolean client, Boolean active) {
		super(id, name, email, cellPhone, telephone, address);
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

	public List<ReceiptItem> getReceiptItems() {
		return receiptItems;
	}

	public List<InventoryItem> getInventoryItems() {
		return InventoryItems;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(cnpj);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Partner other = (Partner) obj;
		return Objects.equals(cnpj, other.cnpj);
	}

}
