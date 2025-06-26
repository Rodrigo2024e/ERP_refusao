package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.entities.Role;
import com.smartprocessrefusao.erprefusao.entities.User;

public class UserFactory {


	    public static User createClientUser() {
	        // Criar employee
	        Employee employee = new Employee();
	        employee.setId(1L);
	        employee.setname("Ana Cliente");
	        employee.setEmail("ana@gmail.com");
	        employee.setCellPhone("43999990000");
	        employee.setTelephone("4333330000");
	        employee.setCpf("123.456.789-00");
	        employee.setRg("MG-12.345.678");

	        // Criar user
	        User user = new User();
	        user.setId(1L);
	        user.setEmail("ana@gmail.com");
	        user.setPassword("2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG"); // senha criptografada
	        user.setEmployee(employee); // associa employee ao user

	        // opcional: também associar user ao employee (se necessário)
	        employee.setUser(user); // <- se houver navegação inversa

	        // Atribui uma role
	        Role role = new Role();
	        role.setId(1L);
	        role.setAuthority("ROLE_CLIENT");
	        user.getRoles().add(role);

	        return user;
	    }
	}

	
	
/*	
	public static User createClientUser() {
		User user = new User(1L,"ana@gmail.com", "2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG");
		user.addRole(new Role(1L, "ROLE_CLIENT"));
		return user;
	}
	
	public static User createAdminUser() {
		User user = new User(1L,"luciano@gmail.com","$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG");
		user.addRole(new Role(1L, "ROLE_ADMIN"));
		return user;
	}
	
	public static User createCustomClienUser(Long id, String username) {
		User user = new User(id,"ana@gmail.com", "2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG");
		user.addRole(new Role(1L, "ROLE_CLIENT"));
		return user;
	}
	
	
	public static User createCustomAdminUser(Long id, String username) {
		User user = new User(id, "luciano@gmail.com","$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG");
		user.addRole(new Role(2L, "ROLE_ADMIN"));
		return user;
	}

	
}
*/
