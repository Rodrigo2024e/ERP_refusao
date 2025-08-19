package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.dto.SectorDTO;
import com.smartprocessrefusao.erprefusao.entities.Sector;

public class SectorFactory {

	public static Sector createSector() {
		Sector sector = new Sector();
		sector.setId(1L);
		sector.setNameSector("PRODUÇÃO");
		sector.setProcess("Corte de tarugos");
		return sector;
	}

	public static SectorDTO createSectorDTO() {
		return new SectorDTO(createSector());
	}

}
