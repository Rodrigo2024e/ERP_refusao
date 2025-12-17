package com.smartprocessrefusao.erprefusao.tests;

import java.util.ArrayList;
import java.util.List;

import com.smartprocessrefusao.erprefusao.projections.UserDetailsReportProjection;

public class UserDetailsFactory {

	public static List<UserDetailsReportProjection> createCustomClientUser(String username) {
		
		List<UserDetailsReportProjection> list = new ArrayList<>();
		list.add(new UserDetailsImpl(username, "123", 1L, "ROLE_CLIENT"));
		return list;
	}
	
	public static List<UserDetailsReportProjection> createCustomAdminUser(String username) {
		
		List<UserDetailsReportProjection> list = new ArrayList<>();
		list.add(new UserDetailsImpl(username, "123", 2L, "ROLE_ADMIN"));
		return list;
	}
	
	public static List<UserDetailsReportProjection> createCustomAdminClientUser(String username) {
		
		List<UserDetailsReportProjection> list = new ArrayList<>();
		list.add(new UserDetailsImpl(username, "123", 1L, "ROLE_CLIENT"));
		list.add(new UserDetailsImpl(username, "123", 2L, "ROLE_ADMIN"));
		return list;
	}
}
	
class UserDetailsImpl implements UserDetailsReportProjection {
	
	private String username;
	private String password;
	private Long roleId;
	private String authority;
	
	public UserDetailsImpl() {
		
	}

	public UserDetailsImpl(String username, String password, Long roleId, String authority) {
	
		this.username = username;
		this.password = password;
		this.roleId = roleId;
		this.authority = authority;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Long getRoleId() {
		return roleId;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

}


