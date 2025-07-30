package com.smartprocessrefusao.erprefusao.tests;

import java.util.List;

import org.springframework.test.util.ReflectionTestUtils;

import com.smartprocessrefusao.erprefusao.dto.RoleDTO;
import com.smartprocessrefusao.erprefusao.dto.UserInsertDTO;
import com.smartprocessrefusao.erprefusao.dto.UserUpdateDTO;
import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.entities.Role;
import com.smartprocessrefusao.erprefusao.entities.User;

public class UserFactory {

	public static User createClientUser() {
		User user = new User(2L, "ana@gmail.com", "$2a$10$N7SkKCa3r17ga.i.dF9iy.BFUBL2n3b6Z1CWSZWi/qy7ABq/E6VpO", null);
		user.addRole(new Role(2L, "ROLE_CLIENT"));
		return user;
	}

	public static User createAdminUser() {
		User user = new User(1L, "luciano@gmail.com", "$2a$10$N7SkKCa3r17ga.i.dF9iy.BFUBL2n3b6Z1CWSZWi/qy7ABq/E6VpO",
				null);
		user.addRole(new Role(1L, "ROLE_ADMIN"));
		return user;
	}

	public static User createCustomClientUser(Long id, String username) {
		User user = new User(id, username, "$2a$10$N7SkKCa3r17ga.i.dF9iy.BFUBL2n3b6Z1CWSZWi/qy7ABq/E6VpO", null);
		user.addRole(new Role(2L, "ROLE_CLIENT"));
		return user;
	}

	public static User createCustomAdminUser(Long id, String username) {
		User user = new User(id, username, "$2a$10$N7SkKCa3r17ga.i.dF9iy.BFUBL2n3b6Z1CWSZWi/qy7ABq/E6VpO", null);
		user.addRole(new Role(1L, "ROLE_ADMIN"));
		return user;
	}

	public static UserInsertDTO createUserInsertDTO() {
		UserInsertDTO dto = new UserInsertDTO();
		ReflectionTestUtils.setField(dto, "email", "luciano@gmail.com");
		ReflectionTestUtils.setField(dto, "password", "123456");
		ReflectionTestUtils.setField(dto, "employee_id", 10L);
		ReflectionTestUtils.setField(dto, "roles", List.of(new RoleDTO(1L, "ROLE_USER")));
		return dto;
	}

	public static UserUpdateDTO createUserUpdateDTO() {
		UserUpdateDTO dto = new UserUpdateDTO();
		ReflectionTestUtils.setField(dto, "email", "luciano@gmail.com");
		ReflectionTestUtils.setField(dto, "employee_id", 1L);
		ReflectionTestUtils.setField(dto, "roles", List.of(new RoleDTO(1L, "ROLE_USER")));
		return dto;
	}

	public static User createUser() {
		Employee emp = new Employee(1L, "John Doe", "luciano@gmail.com", "99999-9999", "9999-9999", null,
				"99.999.999-99", null, true, null, null);
		Role role = new Role(1L, "ROLE_USER");

		User user = new User(1L, "luciano@gmail.com", "123456", emp);
		user.addRole(role);
		return user;
	}

	public static Employee createEmployee() {
		Employee emp = new Employee(1L, "John Doe", "luciano@gmail.com", "99999-9999", "9999-9999", null,
				"99.999.999-99", null, true, null, null);
		return emp;
	}

	public static Role createRole() {
		Role role = new Role(1L, "ROLE_USER");
		return role;
	}

}
