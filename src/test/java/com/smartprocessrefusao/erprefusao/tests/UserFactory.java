package com.smartprocessrefusao.erprefusao.tests;

import com.smartprocessrefusao.erprefusao.entities.Role;
import com.smartprocessrefusao.erprefusao.entities.User;

public class UserFactory {

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
