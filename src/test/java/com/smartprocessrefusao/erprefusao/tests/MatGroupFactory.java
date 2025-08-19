package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.dto.MaterialGroupDTO;
import com.smartprocessrefusao.erprefusao.entities.MaterialGroup;

public class MatGroupFactory {

	public static MaterialGroup createGroup() {
		MaterialGroup group = new MaterialGroup();
		group.setId(1L);
		group.setDescription("PRODUTO ACABADO");
		return group;
	}

	public static MaterialGroupDTO createGroupDTO() {
		return new MaterialGroupDTO(1L, "SUCATA DE ALUMÍNIO");
	}

}
