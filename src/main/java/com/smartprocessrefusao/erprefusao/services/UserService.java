package com.smartprocessrefusao.erprefusao.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.RoleDTO;
import com.smartprocessrefusao.erprefusao.dto.UserDTO;
import com.smartprocessrefusao.erprefusao.dto.UserInsertDTO;
import com.smartprocessrefusao.erprefusao.dto.UserUpdateDTO;
import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.entities.Role;
import com.smartprocessrefusao.erprefusao.entities.User;
import com.smartprocessrefusao.erprefusao.projections.UserDetailsReportProjection;
import com.smartprocessrefusao.erprefusao.repositories.EmployeeRepository;
import com.smartprocessrefusao.erprefusao.repositories.RoleRepository;
import com.smartprocessrefusao.erprefusao.repositories.UserRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.util.CustomUserUtil;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository repository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private CustomUserUtil customUserUtil;

	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(Pageable pageable) {
		Page<User> list = repository.findAll(pageable);
		return list.map(x -> new UserDTO(x));
	}

	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		try {
			Optional<User> obj = repository.findById(id);
			User entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
			return new UserDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}

	}

	@Transactional
	public UserDTO insert(UserInsertDTO dto) {
		if (repository.existsByUsername(dto.getUsername())) {
			throw new IllegalArgumentException("Usuário já cadastrado!");
		}

		User entity = new User();
		copyDtoToEntity(dto, entity);
		entity.setUsername(dto.getUsername());
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		entity = repository.save(entity);
		return new UserDTO(entity);
	}

	@Transactional
	public UserDTO update(Long id, UserUpdateDTO dto) {
		if (repository.existsByUsername(dto.getUsername())) {
			throw new IllegalArgumentException("Usuário já cadastrado!");
		}
		
		try {
			User entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity.setUsername(dto.getUsername());
			entity = repository.save(entity);
			return new UserDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	@Transactional
	public void delete(Long id) {
		User entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));

		if (entity.getEmployee() != null) {
			entity.getEmployee().setUser(null);
			entity.setEmployee(null);
		}

		try {
			repository.delete(entity);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	private void copyDtoToEntity(UserDTO dto, User entity) {

		Optional.ofNullable(dto.getEmployee_id()).ifPresent(id -> {
			Employee employee = employeeRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
			entity.setEmployee(employee);
		});

		entity.getRoles().clear();
		for (RoleDTO roleDto : dto.getRoles()) {
			Role role = roleRepository.getReferenceById(roleDto.getId());
			entity.getRoles().add(role);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		List<UserDetailsReportProjection> result = repository.searchUserAndRolesByUsername(username);
		if (result.size() == 0) {
			throw new UsernameNotFoundException("Username not found");
		}

		User user = new User();
		user.setUsername(result.get(0).getUsername());
		user.setPassword(result.get(0).getPassword());
		for (UserDetailsReportProjection projection : result) {
			user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
		}

		return user;
	}

	protected User authenticated() {
		try {
			String username = customUserUtil.getLoggedUsername();
			return repository.findByUsername(username).get();
		} catch (Exception e) {
			throw new UsernameNotFoundException("Invalid user");
		}
	}

	@Transactional(readOnly = true)
	public UserDTO getMe() {
		User entity = authenticated();
		return new UserDTO(entity);
	}

}
