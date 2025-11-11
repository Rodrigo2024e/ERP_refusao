package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.dto.DepartamentDTO;
import com.smartprocessrefusao.erprefusao.entities.Departament;

public class SectorFactory {

	public static Departament createSector() {
		Departament sector = new Departament();
		sector.setId(1L);
		sector.setName("PRODUÇÃO");
		sector.setProcess("Corte de tarugos");
		return sector;
	}

	public static DepartamentDTO createSectorDTO() {
		return new DepartamentDTO(createSector());
	}

}
