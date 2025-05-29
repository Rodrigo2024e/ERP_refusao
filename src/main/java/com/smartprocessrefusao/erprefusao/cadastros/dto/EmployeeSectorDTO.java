package com.smartprocessrefusao.erprefusao.cadastros.dto;

import com.smartprocessrefusao.erprefusao.cadastros.entities.Employee;

public class EmployeeSectorDTO {
	
	private Long id;
	private String name;
	private Boolean sysUser;
	private Long sectorId;
	private String nameSector;
	private String process;
	
	public EmployeeSectorDTO() {
		
	}
	
	public EmployeeSectorDTO(Employee entity) {
		id = entity.getId();
		name = entity.getName();
		sysUser = entity.getSysUser();
		sectorId = entity.getSector().getId();
		nameSector = entity.getSector().getNameSector();
		process = entity.getSector().getProcess();
		
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Boolean getSysUser() {
		return sysUser;
	}

	public Long getSectorId() {
		return sectorId;
	}

	public String getNameSector() {
		return nameSector;
	}

	public String getProcess() {
		return process;
	}

}

