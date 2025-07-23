
package com.smartprocessrefusao.erprefusao.dto;

import java.util.ArrayList;
import java.util.List;

import com.smartprocessrefusao.erprefusao.entities.User;

import jakarta.validation.constraints.Email;

public class UserDTO {
	
	private long id;
	
	@Email(message = "Favor entrar um email válido")
	private String email;
	
	private Long employee_id;
	private String nameEmployee;

	List<RoleDTO> roles = new ArrayList<>();
	
	public UserDTO() {
		
	}

	
	public UserDTO(long id, String email, Long employee_id, String nameEmployee) {
		super();
		this.id = id;
		this.email = email;
		this.employee_id = employee_id;
		this.nameEmployee = nameEmployee;
		
	}

	public UserDTO(User entity) {
		id = entity.getId();
		email = entity.getEmail();
		
		  if (entity.getEmployee() != null) {
		        this.employee_id = entity.getEmployee().getId();
		        this.nameEmployee = entity.getEmployee().getName();
		    } else {
		        this.employee_id = null;
		        this.nameEmployee = null;
		    }
		
		

		entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
	}


	public long getId() {
		return id;
	}


	public String getEmail() {
		return email;
	}


	public Long getEmployee_id() {
		return employee_id;
	}


	public String getNameEmployee() {
		return nameEmployee;
	}


	public List<RoleDTO> getRoles() {
		return roles;
	}
	
}
