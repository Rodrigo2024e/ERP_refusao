package com.smartprocessrefusao.erprefusao.projections;

import java.time.LocalDate;

public interface EmployeeDepartamentReportProjection {

	Long getId();
	String getName();
	String getEmail();
	String getCellPhone();
	String getTelephone();
	String getCpf();
	LocalDate getDateOfBirth();
	Long getDepartamentId();
	String getDepartament();
	String getProcess();
	String getPosition();

}
