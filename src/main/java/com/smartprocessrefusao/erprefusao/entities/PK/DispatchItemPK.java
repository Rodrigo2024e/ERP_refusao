package com.smartprocessrefusao.erprefusao.entities.PK;

import java.io.Serializable;
import java.util.Objects;

import com.smartprocessrefusao.erprefusao.entities.Dispatch;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class DispatchItemPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "dispatch_id")
	private Dispatch dispatch;

	@ManyToOne
	@JoinColumn(name = "partner_id")
	private Partner partner;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(name = "ITEM_SEQUENCE")
	private Integer itemSequence;

	public DispatchItemPK() {
	}

	public DispatchItemPK(Dispatch dispatch, Partner partner, Product product) {
		this.dispatch = dispatch;
		this.partner = partner;
		this.product = product;
	}

	public Dispatch getDispatch() {
		return dispatch;
	}

	public void setDispatch(Dispatch dispatch) {
		this.dispatch = dispatch;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getItemSequence() {
		return itemSequence;
	}

	public void setItemSequence(Integer itemSequence) {
		this.itemSequence = itemSequence;
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemSequence, product, partner, dispatch);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DispatchItemPK other = (DispatchItemPK) obj;
		return Objects.equals(itemSequence, other.itemSequence) && Objects.equals(product, other.product)
				&& Objects.equals(partner, other.partner) && Objects.equals(dispatch, other.dispatch);
	}

}
