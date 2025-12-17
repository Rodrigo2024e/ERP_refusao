package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.projections.EmployeeMeltingProjection;

public class EmployeeMeltingDTO {

	private Long employeeId;
	private String employeeName;
	private Long departamentId;
	private String departamentName;
	private String departamentProcess;
	private String employeePosition;

	public EmployeeMeltingDTO() {

	}

	public EmployeeMeltingDTO(Long employeeId, String employeeName, Long departamentId, String departamentName,
			String departamentProcess, String employeePosition) {
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.departamentId = departamentId;
		this.departamentName = departamentName;
		this.departamentProcess = departamentProcess;
		this.employeePosition = employeePosition;
	}

	public EmployeeMeltingDTO(Employee entity) {
		employeeId = entity.getId();
		employeeName = entity.getName();
		departamentId = entity.getDepartament().getId();
		departamentName = entity.getDepartament().getName();
		departamentProcess = entity.getDepartament().getProcess();
		employeePosition = entity.getDepartament().getPosition().toString();

	}

	public EmployeeMeltingDTO(EmployeeMeltingProjection projection) {
		employeeId = projection.getEmployeeId();
		employeeName = projection.getEmployeeName();
		departamentId = projection.getDepartamentId();
		departamentName = projection.getDepartamentName();
		departamentProcess = projection.getDepartamentProcess();
		employeePosition = projection.getEmployeePosition();

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

	public String getEmployeePosition() {
		return employeePosition;
	}

}
