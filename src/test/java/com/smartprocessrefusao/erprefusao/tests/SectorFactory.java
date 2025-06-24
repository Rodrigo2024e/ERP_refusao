package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.dto.SectorDTO;
import com.smartprocessrefusao.erprefusao.entities.Sector;

public class SectorFactory {

	//para id conhecido
	public static Sector createSector(Long id, String nameSector, String process) {
		return new Sector(id, nameSector, process);
	}
	
	//para id não conhecido
	public static Sector createSector(String nameSector, String process) {
		return new Sector(null, nameSector, process);
	}
	
	//para id conhecido
	public static SectorDTO createSectorDTO(Long id, String nameSector, String process) {
		SectorDTO dto = new SectorDTO();
		dto.setId(id);
		dto.setNameSector(nameSector);
		dto.setProcess(process);
		return dto;
	}
	//para id não conhecido
	public static SectorDTO createSectorDTO(String nameSector, String process) {
		SectorDTO dto = new SectorDTO();
		dto.setNameSector(nameSector);
		dto.setProcess(process);
		return dto;
	}
	
	//construtor para criação de entidades
	public static SectorDTO createSectorDTO(Sector entity) {
		
		SectorDTO dto = new SectorDTO();
		dto.setId(entity.getId());
		dto.setNameSector(entity.getNameSector());
		dto.setProcess(entity.getProcess());
		return dto;
	}
	
}
