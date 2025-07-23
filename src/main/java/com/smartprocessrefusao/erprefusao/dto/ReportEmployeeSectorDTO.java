package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.projections.ReportEmployeeSectorProjection;

public class ReportEmployeeSectorDTO {

	private Long idPessoa;
	private String name;
	private Long sectorId;
	private String nameSector;
	private String process;
	private Boolean sysUser;

    
    public ReportEmployeeSectorDTO() {
    	
    }

	public ReportEmployeeSectorDTO(Employee entity) {
		idPessoa = entity.getId();
		name = entity.getName();
		sectorId = entity.getSector().getId();
		nameSector = entity.getSector().getNameSector();
		process = entity.getSector().getProcess();
		sysUser = entity.getSysUser();		
	}

	public ReportEmployeeSectorDTO(ReportEmployeeSectorProjection projection) {
		this.idPessoa = projection.getIdPessoa();
		this.name = projection.getName();
		this.sectorId = projection.getSectorId();
		this.nameSector = projection.getNameSector();
		this.process = projection.getProcess();
		this.sysUser = projection.getSysUser();
	
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public String getName() {
		return name;
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

	public Boolean getSysUser() {
		return sysUser;
	}

	
	
}
