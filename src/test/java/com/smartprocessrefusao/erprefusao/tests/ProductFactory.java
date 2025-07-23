package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.dto.ProductDTO;
import com.smartprocessrefusao.erprefusao.entities.Product;
import com.smartprocessrefusao.erprefusao.projections.ReportProductProjection;

public class ProductFactory {

	public static Product createProduct() {
		Product product = new Product();
		product.setId(1L);
		product.setDescription("Tarugo de alumínio");
		product.setUom(UnitFactory.createUnit());
		product.setTaxclass(TaxClassificationFactory.createTaxClass());
		product.setProdGroup(ProductGroupFactory.createGroup());
		return product;
	}

	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product);
	}

	public static ReportProductProjection create() {
		return new ReportProductProjection() {

			@Override
			public Long getId() {
				return 1L;
			}

			@Override
			public String getDescription() {
				return "Tarugo de alumínio";
			}

			@Override
			public Integer getAlloy() {
				return 6060;
			}

			@Override
			public Integer getInch() {
				return 5;
			}

			@Override
			public Integer getLength() {
				return 6;
			}

			@Override
			public String getUnit() {
				return "kg";
			}

			@Override
			public String getTax_Classification() {
				return "Produto acabado";
			}

			@Override
			public String getNumber() {
				return "7604000";
			}

			@Override
			public String getProduct_Group() {
				return "Produto acabado";
			}
		};
	}

}
