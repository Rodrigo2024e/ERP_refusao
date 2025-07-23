package com.smartprocessrefusao.erprefusao.services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartprocessrefusao.erprefusao.entities.User;
import com.smartprocessrefusao.erprefusao.services.exceptions.ForbiddenException;

@Service
public class AuthService {

	@Autowired
	private UserService userService;
	
	public void validateSelfOrAdmin(Long userId) {
		User me = userService.authenticated();
		if (me.hasRole("ROLE_ADMIN")) {
			return;
		}
		if (!Objects.equals(me.getId(), userId)) {
		    throw new ForbiddenException("Access denied. Should be self or admin.");
		}
	}
}
