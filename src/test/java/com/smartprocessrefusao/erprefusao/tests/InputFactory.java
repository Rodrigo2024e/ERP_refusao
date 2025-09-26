package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.dto.InputDTO;
import com.smartprocessrefusao.erprefusao.entities.Input;
import com.smartprocessrefusao.erprefusao.entities.MaterialGroup;
import com.smartprocessrefusao.erprefusao.entities.TaxClassification;
import com.smartprocessrefusao.erprefusao.entities.Unit;
import com.smartprocessrefusao.erprefusao.enumerados.TypeMaterial;
import com.smartprocessrefusao.erprefusao.projections.InputReportProjection;

public class InputFactory {

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
		tax.setNumber(7602000);
		return tax;

	}

	public static MaterialGroup createGroup() {
		MaterialGroup group = new MaterialGroup();
		group.setId(1L);
		group.setDescription("SUCATA DE ALUMINIO");
		return group;
	}

	public static Input createInput() {
		Input input = new Input();
		input.setTypeMaterial(TypeMaterial.SCRAP);
		input.setId(1L);
		input.setDescription("PERFIL DE PROCESSO");
		input.setUomMaterial(createUnit());
		input.setTaxClassMaterial(createTaxClassification());
		input.setMaterialGroup(createGroup());
		return input;
	}

	public static InputDTO createTypeMaterialInvalid() {
		return new InputDTO(1L, "INVALID", "Perfil de processo", 1L, "kg", 1L, "Sucata de alumínio", 7602000, 1L,
				"Sucata de alumínio");
	}

	public static InputDTO createInputDTO() {
		Input input = createInput();
		return new InputDTO(input);
	}

	public static InputReportProjection create() {
		return new InputReportProjection() {

			@Override
			public Long getId() {
				return 1L;
			}

			@Override
			public String getType_Material() {
				return "SCARP";
			}

			@Override
			public String getDescription() {
				return "PERFIL DE PROCESSO";
			}

			@Override
			public String getUnit() {
				return "kg";
			}

			@Override
			public Long getTaxClassId() {
				return 1L;
			}

			@Override
			public String getTax_Classification() {
				return "SUCATA DE ALUMINIO";
			}

			@Override
			public Integer getNumber() {
				return 7602000;
			}

			@Override
			public Long getMatGroupId() {
				return 1L;

			}

			@Override
			public String getMaterial_Group() {
				return "SUCATA DE ALUMINIO";
			}
		};
	}

}
