package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.projections.EmployeeMeltingProjection;

public class EmployeeMeltingReportDTO {

	private Long employeeId;
	private String employeeName;
	private Long departamentId;
	private String departamentName;
	private String departamentProcess;


	public EmployeeMeltingReportDTO() {

	}

	public EmployeeMeltingReportDTO(Long employeeId, String employeeName, Long departamentId, String departamentName,
			String departamentProcess) {
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.departamentId = departamentId;
		this.departamentName = departamentName;
		this.departamentProcess = departamentProcess;
		
	}

	public EmployeeMeltingReportDTO(Employee entity) {
		employeeId = entity.getId();
		employeeName = entity.getName();
		departamentId = entity.getDepartament().getId();
		departamentName = entity.getDepartament().getName();
		departamentProcess = entity.getDepartament().getProcess();

	}

	public EmployeeMeltingReportDTO(EmployeeMeltingProjection projection) {
		employeeId = projection.getEmployeeId();
		employeeName = projection.getEmployeeName();
		departamentId = projection.getDepartamentId();
		departamentName = projection.getDepartamentName();
		departamentProcess = projection.getDepartamentProcess();
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public Long getDepartamentId() {
		return departamentId;
	}

	public String getDepartamentName() {
		return departamentName;
	}

	public String getDepartamentProcess() {
		return departamentProcess;
	}

}
