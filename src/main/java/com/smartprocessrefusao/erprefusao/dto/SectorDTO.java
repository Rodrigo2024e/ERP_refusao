package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.Sector;

import jakarta.validation.constraints.Size;

public class SectorDTO {
	
	private Long id;
	
	@Size(min = 3, max = 20, message = "O nome do setor deve ter entre 3 a 15 caracteres")
	private String nameSector;
	
//	@Size(min = 3, max = 20, message = "O nome do processo deve ter entre 3 a 16 caracteres")
	private String process;
	
	public SectorDTO() {
		
	}
	

	public SectorDTO(Long id, String nameSector, String process) {
		super();
		this.id = id;
		this.nameSector = nameSector;
		this.process = process;
	}


	public SectorDTO(Sector entity) {
		id = entity.getId();
		nameSector = entity.getNameSector();
		process = entity.getProcess();
		
	}
	
	public Long getId() {
		return id;
	}

	public String getNameSector() {
		return nameSector;
	}

	public String getProcess() {
		return process;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
