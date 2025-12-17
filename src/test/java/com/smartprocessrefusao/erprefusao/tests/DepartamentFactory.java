package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.dto.DepartamentDTO;
import com.smartprocessrefusao.erprefusao.entities.Departament;

public class DepartamentFactory {

	public static Departament createDepartament() {
		Departament departament = new Departament();
		departament.setId(1L);
		departament.setName("PRODUÇÃO");
		departament.setProcess("Corte de tarugos");
		return departament;
	}

	public static DepartamentDTO createDepartamentDTO() {
		return new DepartamentDTO(createDepartament());
	}

}
