package com.smartprocessrefusao.erprefusao.entities.PK;

/*
@Embeddable
public class ReceiptItemPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "receipt_id", nullable = false)
	private Receipt receipt;

	@ManyToOne(optional = false)
	@JoinColumn(name = "partner_id", nullable = false)
	private Partner partner;

	@ManyToOne(optional = false)
	@JoinColumn(name = "material_id", nullable = false)
	private Material material;

	@Column(name = "item_sequence")
	private Integer itemSequence;

	public ReceiptItemPK() {
	}

	public ReceiptItemPK(Receipt receipt, Partner partner, Material material) {
		this.receipt = receipt;
		this.partner = partner;
		this.material = material;
	}

	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
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

	public Integer getItemSequence() {
		return itemSequence;
	}

	public void setItemSequence(Integer itemSequence) {
		this.itemSequence = itemSequence;
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemSequence, material, partner, receipt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReceiptItemPK other = (ReceiptItemPK) obj;
		return Objects.equals(itemSequence, other.itemSequence) && Objects.equals(material, other.material)
				&& Objects.equals(partner, other.partner) && Objects.equals(receipt, other.receipt);
	}

}
*/
