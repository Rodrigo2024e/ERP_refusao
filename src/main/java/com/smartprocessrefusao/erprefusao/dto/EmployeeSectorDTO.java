package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.Employee;

public class EmployeeSectorDTO {
	
	private Long id;
	private String name;
	private Long sectorId;
	private String nameSector;
	private String process;
	private boolean sysUser;
	
	public EmployeeSectorDTO() {
		
	}
	
	public EmployeeSectorDTO(Employee entity) {
		id = entity.getId();
		name = entity.getName();
		sectorId = entity.getSector().getId();
		nameSector = entity.getSector().getNameSector();
		process = entity.getSector().getProcess();
		sysUser = entity.getSysUser();
		
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

