package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.projections.MeltingEmployeeProjection;

public class EmployeeMeltingReportDTO {

	private Long meltingId;
	private Long employeeId;
	private String employeeName;
	private Long departamentId;
	private String departamentName;
	private String departamentProcess;
	private String employeePosition;

	public EmployeeMeltingReportDTO() {

	}

	public EmployeeMeltingReportDTO(Long meltingId, Long employeeId, String employeeName, Long departamentId,
			String departamentName, String departamentProcess, String employeePosition) {
		this.meltingId = meltingId;
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.departamentId = departamentId;
		this.departamentName = departamentName;
		this.departamentProcess = departamentProcess;
		this.employeePosition = employeePosition;

	}

	public EmployeeMeltingReportDTO(MeltingEmployeeProjection projection) {
		meltingId = projection.getMeltingId();
		employeeId = projection.getEmployeeId();
		employeeName = projection.getEmployeeName();
		departamentId = projection.getDepartamentId();
		departamentName = projection.getDepartamentName();
		departamentProcess = projection.getDepartamentProcess();
		employeePosition = projection.getEmployeePosition();
	}

	public Long getMeltingId() {
		return meltingId;
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
