package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.dto.MaterialDTO;
import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.entities.MaterialGroup;
import com.smartprocessrefusao.erprefusao.entities.TaxClassification;
import com.smartprocessrefusao.erprefusao.entities.Unit;
import com.smartprocessrefusao.erprefusao.enumerados.TypeMaterial;
import com.smartprocessrefusao.erprefusao.projections.MaterialReportProjection;

public class MaterialFactory {

	public static Unit createUnit() {
		Unit unit = new Unit();
		unit.setId(1L);
		unit.setDescription("KILOGRMA");
		unit.setAcronym("kg");
		return unit;
	}

	public static TaxClassification createTaxClassification() {
		TaxClassification tax = new TaxClassification();
		tax.setId(1L);
		tax.setDescription("SUCATA DE ALUMINIO");
		tax.setNcmCode(7602000);
		return tax;

	}

	public static MaterialGroup createGroup() {
		MaterialGroup group = new MaterialGroup();
		group.setId(1L);
		group.setDescription("SUCATA DE ALUMINIO");
		return group;
	}

	public static Material createMaterial() {
		Material material = new Material();
		material.setMaterialCode(1001L);
		material.setDescription("PERFIL DE PROCESSO");
		material.setType(TypeMaterial.SCRAP);
		material.setUnit(createUnit());
		material.setTaxClass(createTaxClassification());
		material.setMaterialGroup(createGroup());
		return material;
	}

	public static MaterialDTO createTypeMaterialInvalid() {
		return new MaterialDTO(1L, 1111L, "INVALID", TypeMaterial.SCRAP.toString(), 1L, "kg", 1L,
				"Sucata de alumínio", 7602000, 1L, "Sucata de alumínio");
	}

	public static MaterialDTO createMaterialDTO() {
		Material material = createMaterial();
		return new MaterialDTO(material);
	}

	public static MaterialReportProjection create() {
		return new MaterialReportProjection() {

			@Override
			public Long getId() {
				return 1L;
			}

			@Override
			public Long getMaterialCode() {
				return 1111L;
			}

			@Override
			public String getDescription() {
				return "PERFIL DE PROCESSO";
			}

			@Override
			public String getType() {
				return "SCRAP";
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
				return 1L;
			}

			@Override
			public String getTaxClassification() {
				return "SUCATA DE ALUMINIO";
			}

			@Override
			public Integer getNcmCode() {
				return 7602000;
			}

			@Override
			public Long getMatGroupId() {
				return 1L;

			}

			@Override
			public String getMaterialGroup() {
				return "SUCATA DE ALUMINIO";
			}

		};
	}

}
