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
	private Material material;

	@Column(name = "ITEM_SEQUENCE")
	private Integer itemSequence;

	public MeltingItemPK() {
	}

	public MeltingItemPK(Melting melting, Material material) {
		this.melting = melting;
		this.material = material;
	}

	public Melting getMelting() {
		return melting;
	}

	public void setMelting(Melting melting) {
		this.melting = melting;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
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
	            && Objects.equals(material, that.material)
	            && Objects.equals(itemSequence, that.itemSequence);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(melting, material, itemSequence);
	    }

}
