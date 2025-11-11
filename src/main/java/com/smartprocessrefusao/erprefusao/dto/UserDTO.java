
package com.smartprocessrefusao.erprefusao.dto;

import java.util.ArrayList;
import java.util.List;

import com.smartprocessrefusao.erprefusao.entities.User;

import jakarta.persistence.Column;

public class UserDTO {

	private Long id;
	
	@Column(unique = true)
	private String username;
	private Long employee_id;
	private String employee;

	List<RoleDTO> roles = new ArrayList<>();

	public UserDTO() {

	}

	public UserDTO(Long id, String username, Long employee_id, String employee) {
		super();
		this.id = id;
		this.username = username;
		this.employee_id = employee_id;
		this.employee = employee;

	}

	public UserDTO(User entity) {
		id = entity.getId();
		username = entity.getUsername();
		

		if (entity.getEmployee() != null) {
			this.employee_id = entity.getEmployee().getId();
			this.employee = entity.getEmployee().getName();
		} 

		entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public Long getEmployee_id() {
		return employee_id;
	}

	public String getEmployee() {
		return employee;
	}

	public List<RoleDTO> getRoles() {
		return roles;
	}

}
