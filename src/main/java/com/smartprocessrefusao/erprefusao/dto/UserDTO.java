

package com.smartprocessrefusao.erprefusao.dto;

import java.util.ArrayList;
import java.util.List;

import com.smartprocessrefusao.erprefusao.entities.User;

import jakarta.validation.constraints.Email;

public class UserDTO {
	
	private long id;
	
	@Email(message = "Favor entrar um email válido")
	private String email;

	List<RoleDTO> roles = new ArrayList<>();
	
	public UserDTO() {
		
	}

	public UserDTO(long id, String email) {
		this.id = id;
		this.email = email;
	}
	
	public UserDTO(User entity) {
		id = entity.getId();
		email = entity.getEmail();
		entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<RoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDTO> roles) {
		this.roles = roles;
	}
	
}
