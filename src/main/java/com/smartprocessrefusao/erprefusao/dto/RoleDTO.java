

package com.smartprocessrefusao.erprefusao.dto;

import com.smartprocessrefusao.erprefusao.entities.Role;

public class RoleDTO {

	private long id;
	private String authority;
	
	public RoleDTO() {
		
	}

	public RoleDTO(long id, String authority) {
		this.id = id;
		this.authority = authority;
	}

	public RoleDTO(Role role) {
		id = role.getId();
		authority = role.getAuthority();
	}

	public long getId() {
		return id;
	}

	public String getAuthority() {
		return authority;
	}

	
}
