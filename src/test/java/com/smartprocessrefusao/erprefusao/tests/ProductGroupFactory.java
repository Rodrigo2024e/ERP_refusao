package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.entities.ProductGroup;

public class ProductGroupFactory {

	public static ProductGroup createGroup() {
		ProductGroup group = new ProductGroup();
		group.setId(1L);
		group.setDescription("Produto acabado");
		return group;
	}

}
