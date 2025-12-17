package com.smartprocessrefusao.erprefusao.projections;

public interface UserDetailsReportProjection {

	String getUsername();
	String getPassword();
	Long getRoleId();
	String getAuthority();
}
