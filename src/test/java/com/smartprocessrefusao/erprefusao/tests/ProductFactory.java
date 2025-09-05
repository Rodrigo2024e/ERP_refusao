package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.dto.ProductDTO;
import com.smartprocessrefusao.erprefusao.entities.Product;
import com.smartprocessrefusao.erprefusao.enumerados.TypeMaterial;
import com.smartprocessrefusao.erprefusao.projections.ReportProductProjection;

public class ProductFactory {

	public static Product createProduct() {
		Product product = new Product();
		product.setId(1L);
		product.setTypeMaterial(TypeMaterial.FINISHED_PRODUCTS);
		product.setDescription("TARUGO DE ALUMÍNIO");
		product.setAlloy(6060);
		product.setBilletDiameter(4);
		product.setBilletLength(6.0);
		product.setUomMaterial(UnitFactory.createUnit());
		product.setTaxClassMaterial(TaxClassificationFactory.createTaxClass());
		product.setMaterialGroup(MatGroupFactory.createGroup());
		return product;
	}

	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product);
	}

	public static ProductDTO createTaxClassificationInvalid() {
		return new ProductDTO(1L, "FINISHED_PRODUCTS", "Tarugo de alumínio", 6060, 6, 6.0, (long) 1, "kg", 99L,
				"Tax Class Invalid", 7604000, 2L, "Produto acabado");
	}

	public static ProductDTO createTypeMaterialInvalid() {
		return new ProductDTO(1L, "", "Type Material Invalid", 6060, 6, 6.0, (long) 1, "kg", 2L, "Tarugo de alumínio",
				7604000, 2L, "Produto acabado");
	}

	public static ReportProductProjection create() {
		return new ReportProductProjection() {

			@Override
			public Long getId() {
				return 1L;
			}

			@Override
			public String getTypeMaterial() {
				return "FINISHED_PRODUCTS";
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
			public Integer getBilletDiameter() {
				return 5;
			}

			@Override
			public Double getBilletLength() {
				return 6.0;
			}

			@Override
			public Long getUnitId() {
				return 1L;
			}

			@Override
			public String getAcronym() {
				return "kg";
			}

			@Override
			public Long getTaxClassId() {
				return 2L;
			}

			@Override
			public String getDescription_taxclass() {
				return "Produto acabado";
			}

			@Override
			public Integer getNumber() {
				return 7604000;
			}

			@Override
			public Long getMatGroupId() {
				return 2L;
			}
			@Override
			public String getDescription_matGroup() {
				return "Produto acabado";
			}
		};
	}

}
