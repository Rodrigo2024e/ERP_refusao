package com.smartprocessrefusao.erprefusao.entities.PK;

import java.io.Serializable;
import java.util.Objects;

import com.smartprocessrefusao.erprefusao.entities.Dispatch;
import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.entities.Partner;

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
	@JoinColumn(name = "material_code")
	private Material material;

	@Column(name = "ITEM_SEQUENCE")
	private Long itemSequence;

	public DispatchItemPK() {
	}

	public DispatchItemPK(Dispatch dispatch, Partner partner, Material material) {
		this.dispatch = dispatch;
		this.partner = partner;
		this.material = material;
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

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Long getItemSequence() {
		return itemSequence;
	}

	public void setItemSequence(Long itemSequence) {
		this.itemSequence = itemSequence;
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemSequence, material, partner, dispatch);
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
		return Objects.equals(itemSequence, other.itemSequence) && Objects.equals(material, other.material)
				&& Objects.equals(partner, other.partner) && Objects.equals(dispatch, other.dispatch);
	}

}
