package com.smartprocessrefusao.erprefusao.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.smartprocessrefusao.erprefusao.dto.UserDTO;
import com.smartprocessrefusao.erprefusao.dto.UserInsertDTO;
import com.smartprocessrefusao.erprefusao.dto.UserUpdateDTO;
import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.entities.Role;
import com.smartprocessrefusao.erprefusao.entities.User;
import com.smartprocessrefusao.erprefusao.projections.UserDetailsProjection;
import com.smartprocessrefusao.erprefusao.repositories.EmployeeRepository;
import com.smartprocessrefusao.erprefusao.repositories.RoleRepository;
import com.smartprocessrefusao.erprefusao.repositories.UserRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.EmployeeFactory;
import com.smartprocessrefusao.erprefusao.tests.UserFactory;
import com.smartprocessrefusao.erprefusao.util.CustomUserUtil;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private CustomUserUtil customUserUtil;

	private User user;
	private Role role;
	private Employee employee;
	private UserInsertDTO insertDTO;
	private UserUpdateDTO updateDTO;
	private Long existingId;
	private Long nonExistingId;

	private

	@BeforeEach void setUp() {

		existingId = 1L;
		nonExistingId = 999L;

		user = UserFactory.createUser();
		insertDTO = UserFactory.createUserInsertDTO();
		updateDTO = UserFactory.createUserUpdateDTO();
		employee = UserFactory.createEmployee();
		role = UserFactory.createRole();

	}

	@Test
	void findAllPagedShouldReturnPageOfUserDTO() {
		User user1 = UserFactory.createUser();
		User user2 = UserFactory.createUser();
		Pageable pageable = PageRequest.of(0, 10);
		Page<User> page = new PageImpl<>(List.of(user1, user2));

		when(userRepository.findAll(pageable)).thenReturn(page);

		Page<UserDTO> result = userService.findAllPaged(pageable);

		assertNotNull(result);
		assertEquals(2, result.getTotalElements());
		assertEquals(user1.getEmail(), result.getContent().get(0).getEmail());
		assertEquals(user2.getEmail(), result.getContent().get(1).getEmail());
	}

	@Test
	public void findByIdShouldReturnUserDTOWhenIdExists() {
		when(userRepository.findById(existingId)).thenReturn(Optional.of(user));
		UserDTO result = userService.findById(existingId);
		assertNotNull(result);
		assertEquals("LUCIANO@GMAIL.COM", result.getEmail());
	}

	@Test
	public void findByIdShouldThrowResourceNotFoundWhenIdDoesNotExist() {
		when(userRepository.findById(nonExistingId)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> userService.findById(nonExistingId));
	}

	@Test
	public void insertShouldReturnUserDTO() {
		when(employeeRepository.findById(10L)).thenReturn(Optional.of(employee));
		when(roleRepository.getReferenceById(1L)).thenReturn(role);
		when(passwordEncoder.encode("123456")).thenReturn("encoded");
		when(userRepository.save(any())).thenReturn(user);

		UserDTO result = userService.insert(insertDTO);
		assertNotNull(result);
		assertEquals("LUCIANO@GMAIL.COM", result.getEmail());
	}

	@Test
	public void updateShouldReturnUserDTOWhenIdExists() {
		when(userRepository.getReferenceById(existingId)).thenReturn(user);
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		when(roleRepository.getReferenceById(1L)).thenReturn(role);
		when(userRepository.save(any())).thenReturn(user);

		UserDTO result = userService.update(1L, updateDTO);
		assertNotNull(result);
		assertEquals("LUCIANO@GMAIL.COM", result.getEmail());
	}

	@Test
	public void updateShouldThrowWhenIdDoesNotExist() {
		when(userRepository.getReferenceById(1L)).thenThrow(EntityNotFoundException.class);
		assertThrows(ResourceNotFoundException.class, () -> userService.update(1L, updateDTO));
	}

	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		when(userRepository.existsById(1L)).thenReturn(true);
		doNothing().when(userRepository).deleteById(1L);
		assertDoesNotThrow(() -> userService.delete(1L));
	}

	@Test
	public void deleteShouldThrowWhenIdDoesNotExist() {
		when(userRepository.existsById(nonExistingId)).thenReturn(false);
		assertThrows(ResourceNotFoundException.class, () -> userService.delete(nonExistingId));
	}

	@Test
	public void deleteShouldThrowDatabaseExceptionWhenIntegrityViolationOccurs() {
		when(userRepository.existsById(1L)).thenReturn(true);
		doThrow(DataIntegrityViolationException.class).when(userRepository).deleteById(1L);
		assertThrows(DatabaseException.class, () -> userService.delete(1L));
	}

	@Test
	public void loadUserByUsernameShouldReturnUserDetailsWhenUserExists() {
		UserDetailsProjection projection = mock(UserDetailsProjection.class);
		when(projection.getUsername()).thenReturn("user@example.com");
		when(projection.getPassword()).thenReturn("123456");
		when(projection.getRoleId()).thenReturn(1L);
		when(projection.getAuthority()).thenReturn("ROLE_USER");
		when(userRepository.searchUserAndRolesByEmail("user@example.com")).thenReturn(List.of(projection));

		UserDetails result = userService.loadUserByUsername("user@example.com");
		assertNotNull(result);
		assertEquals("user@example.com", result.getUsername());
	}

	@Test
	public void loadUserByUsernameShouldThrowWhenUserNotFound() {
		when(userRepository.searchUserAndRolesByEmail("invalid@example.com")).thenReturn(Collections.emptyList());
		assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("invalid@example.com"));
	}

	@Test
	public void authenticatedShouldReturnUserWhenLogged() {
		when(customUserUtil.getLoggedUsername()).thenReturn("user@example.com");
		when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
		User result = userService.authenticated();
		assertNotNull(result);
	}

	@Test
	public void authenticatedShouldThrowWhenUserNotFound() {
		when(customUserUtil.getLoggedUsername()).thenReturn("LUCIANO@GMAIL.COM");
		when(userRepository.findByEmail("LUCIANO@GMAIL.COM")).thenReturn(Optional.empty());
		assertThrows(UsernameNotFoundException.class, () -> userService.authenticated());
	}

	@Test
	public void getMeShouldReturnUserDTO() {
		when(customUserUtil.getLoggedUsername()).thenReturn("user@example.com");
		when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

		UserDTO result = userService.getMe();
		assertNotNull(result);
		assertEquals("LUCIANO@GMAIL.COM", result.getEmail());
	}

	@Test
	void insertShouldReturnUserDTOWhenEmployeeExists() {
		Employee employee = EmployeeFactory.createEmployee();
		when(userRepository.save(any())).thenReturn(user);
		when(employeeRepository.findById(10L)).thenReturn(Optional.of(employee));
		UserDTO result = userService.insert(insertDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), user.getId());

	}

	@Test
	void insertShouldThrowWhenEmployeeNotFound() {
		// Arrange
		UserInsertDTO dto = UserFactory.createUserInsertDTO();
		when(employeeRepository.findById(dto.getEmployee_id())).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> userService.insert(dto));
	}

}
