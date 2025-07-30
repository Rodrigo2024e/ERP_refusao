package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.dto.MaterialDTO;
import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.entities.ProductGroup;
import com.smartprocessrefusao.erprefusao.entities.TaxClassification;
import com.smartprocessrefusao.erprefusao.entities.Unit;
import com.smartprocessrefusao.erprefusao.projections.ReportMaterialProjection;

public class MaterialFactory {
	
	public static Unit createUnit() {
		Unit unit = new Unit();
		unit.setId(1L);
		unit.setDescription("kilograma");
		unit.setAcronym("kg");
		return unit;
	}
	
	public static TaxClassification createTaxClassification() {
		TaxClassification tax = new TaxClassification();
		tax.setId(1L);
		tax.setDescription("Sucata de alumínio");
		tax.setNumber(7602000);
		return tax;
		
	}
	
	public static ProductGroup createGroup() {
		ProductGroup group = new ProductGroup();
		group.setId(1L);
		group.setDescription("Sucata de alumínio");
		return group;
	}
	
	public static Material createMaterial() {
		Material material = new Material();
		material.setId(1L);
		material.setDescription("Sucata de alumínio");
		material.setUom(createUnit());
		material.setTaxClass(createTaxClassification());
		material.setProdGroup(createGroup());
		return material;
	}
	
	public static MaterialDTO createMaterialDTO() {
		Material material = createMaterial();
		return new MaterialDTO(material);
	}
	
	public static ReportMaterialProjection create() {
		return new ReportMaterialProjection() {
			
			@Override
			public Long getId() {
				return 1L;	
			}
			
			@Override
			public String getDescription() {
				return "Sucata de alumínio";
			}
			
			@Override
			public String getUnit() {
				return "kg";
			}
			
			@Override
			public String getTax_Classification() {
				return "Sucata de alumínio";
			}
			
			@Override
			public Integer getNumber() {
				return 7602000;
			}
			
			@Override
			public String getProduct_Group() {
				return "Sucata de alumínio";
			}		
		};
	}
		
}
