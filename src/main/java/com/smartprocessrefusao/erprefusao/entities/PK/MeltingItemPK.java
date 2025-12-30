package com.smartprocessrefusao.erprefusao.entities.PK;

import java.io.Serializable;
import java.util.Objects;

import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.entities.Melting;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class MeltingItemPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "melting_id")
	private Melting melting;

	@ManyToOne
	@JoinColumn(name = "material_code")
	private Material materialCode;

	@Column(name = "ITEM_SEQUENCE")
	private Integer itemSequence;

	public MeltingItemPK() {
	}

	public MeltingItemPK(Melting melting, Material materialCode) {
		this.melting = melting;
		this.materialCode = materialCode;
	}

	public Melting getMelting() {
		return melting;
	}

	public void setMelting(Melting melting) {
		this.melting = melting;
	}

	public Material getMaterialCode() {
		return materialCode;
	}

	public void setMaterial(Material materialCode) {
		this.materialCode = materialCode;
	}

	public Integer getItemSequence() {
		return itemSequence;
	}

	public void setItemSequence(Integer itemSequence) {
		this.itemSequence = itemSequence;
	}

	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof MeltingItemPK that)) return false;
	        return Objects.equals(melting, that.melting)
	            && Objects.equals(materialCode, that.materialCode)
	            && Objects.equals(itemSequence, that.itemSequence);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(melting, materialCode, itemSequence);
	    }

}
