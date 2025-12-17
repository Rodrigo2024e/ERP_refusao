package com.smartprocessrefusao.erprefusao.tests;

import java.time.LocalDate;
import java.util.List;

import org.springframework.test.util.ReflectionTestUtils;

import com.smartprocessrefusao.erprefusao.dto.RoleDTO;
import com.smartprocessrefusao.erprefusao.dto.UserInsertDTO;
import com.smartprocessrefusao.erprefusao.dto.UserUpdateDTO;
import com.smartprocessrefusao.erprefusao.entities.Address;
import com.smartprocessrefusao.erprefusao.entities.Departament;
import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.entities.Role;
import com.smartprocessrefusao.erprefusao.entities.User;

public class UserFactory {
	
	public static Address createAddress() {
		Address address = new Address();
		address.setId(1L);
		address.setStreet("RUA A");
		address.setNumber(123);
		address.setComplement("CASA");
		address.setNeighborhood("CENTRO");
		address.setZipCode("12345-678");
		address.setCity(CityFactory.createCity());
		address.setPeople(PeopleFactory.createPeople());
		return address;
	}

	public static Departament createDepartament() {
		Departament departament = new Departament();
		departament.setId(1L);
		departament.setName("PRODUÇÃO");
		departament.setProcess("CORTE DE TARUGOS");
		return departament;
	}

	public static User createClientUser() {
		User user = new User(2L, "nomeUsuario", "$2a$10$N7SkKCa3r17ga.i.dF9iy.BFUBL2n3b6Z1CWSZWi/qy7ABq/E6VpO", null);
		user.addRole(new Role(2L, "ROLE_CLIENT"));
		return user;
	}

	public static User createAdminUser() {
		User user = new User(1L, "nomeUsuario", "$2a$10$N7SkKCa3r17ga.i.dF9iy.BFUBL2n3b6Z1CWSZWi/qy7ABq/E6VpO",
				null);
		user.addRole(new Role(1L, "ROLE_ADMIN"));
		return user;
	}

	public static User createCustomClientUser(Long id, String email) {
		User user = new User(id, "nomeUsuario", "$2a$10$N7SkKCa3r17ga.i.dF9iy.BFUBL2n3b6Z1CWSZWi/qy7ABq/E6VpO", null);
		user.addRole(new Role(2L, "ROLE_CLIENT"));
		return user;
	}

	public static User createCustomAdminUser(Long id, String email) {
		User user = new User(id, "nomeUsuario", "$2a$10$N7SkKCa3r17ga.i.dF9iy.BFUBL2n3b6Z1CWSZWi/qy7ABq/E6VpO", null);
		user.addRole(new Role(1L, "ROLE_ADMIN"));
		return user;
	}

	public static UserInsertDTO createUserInsertDTO() {
		UserInsertDTO dto = new UserInsertDTO();
		ReflectionTestUtils.setField(dto, "username", "nomeUsuario");
		ReflectionTestUtils.setField(dto, "password", "123456");
		ReflectionTestUtils.setField(dto, "employee_id", 10L);
		ReflectionTestUtils.setField(dto, "roles", List.of(new RoleDTO(1L, "ROLE_USER")));
		return dto;
	}

	public static UserUpdateDTO createUserUpdateDTO() {
		UserUpdateDTO dto = new UserUpdateDTO();
		ReflectionTestUtils.setField(dto, "username", "nomeUsuario");
		ReflectionTestUtils.setField(dto, "employee_id", 1L);
		ReflectionTestUtils.setField(dto, "roles", List.of(new RoleDTO(1L, "ROLE_USER")));
		return dto;
	}

	public static User createUser() {
		Employee emp = new Employee(1L, "John Doe", "LUCIANO@GMAIL.COM", "99999-9999", "9999-9999", null,
				"99.999.999-99", null, null, null);
		Role role = new Role(1L, "ROLE_USER");

		User user = new User(1L, "nomeUsuario", "123456", emp);
		user.addRole(role);
		return user;
	}

	public static Employee createEmployee() {
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("JONATHAS JUNIO");
		employee.setEmail("jonathas@alunova.com");
		employee.setCellPhone("44-12345-7652");
		employee.setTelephone("01-1000-1000");
		employee.setAddress(createAddress());
		employee.setCpf("058.651.619-03");
		employee.setDateOfBirth(LocalDate.now());
		employee.setUser(createUser());
		employee.setDepartament(createDepartament());
		return employee;
	}

	public static Role createRole() {
		Role role = new Role(1L, "ROLE_USER");
		return role;
	}

}
