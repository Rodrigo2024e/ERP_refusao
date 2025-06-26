package com.smartprocessrefusao.erprefusao.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.smartprocessrefusao.erprefusao.dto.RoleDTO;
import com.smartprocessrefusao.erprefusao.dto.UserDTO;
import com.smartprocessrefusao.erprefusao.dto.UserInsertDTO;
import com.smartprocessrefusao.erprefusao.dto.UserUpdateDTO;
import com.smartprocessrefusao.erprefusao.entities.Role;
import com.smartprocessrefusao.erprefusao.entities.User;
import com.smartprocessrefusao.erprefusao.projections.UserDetailsProjection;
import com.smartprocessrefusao.erprefusao.repositories.RoleRepository;
import com.smartprocessrefusao.erprefusao.repositories.UserRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.UserFactory;

import jakarta.persistence.EntityNotFoundException;

public class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllPagedShouldReturnPageOfDTOs() {
        Pageable pageable = PageRequest.of(0, 10);
        List<User> list = List.of(UserFactory.createClientUser());
        Page<User> page = new PageImpl<>(list);

        when(repository.findAll(pageable)).thenReturn(page);

        Page<UserDTO> result = service.findAllPaged(pageable);

        assertFalse(result.isEmpty());
        verify(repository).findAll(pageable);
    }

    @Test
    void findByIdShouldReturnDTOWhenIdExists() {
        User user = UserFactory.createClientUser();
        when(repository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO result = service.findById(1L);

        assertEquals(result.getEmail(), user.getEmail());
    }

    @Test
    void findByIdShouldThrowWhenIdDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(99L);
        });
    }

    @Test
    void insertShouldSaveAndReturnDTO() {
        UserInsertDTO dto = new UserInsertDTO();
        dto.setEmail("ana@gmail.com");
        dto.setPassword("123456");
        dto.setRoles(List.of(new RoleDTO(1L, "ROLE_CLIENT")));

        User savedUser = UserFactory.createClientUser();

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleRepository.getReferenceById(1L)).thenReturn(new Role(1L, "ROLE_CLIENT"));
        when(repository.save(any())).thenReturn(savedUser);

        UserDTO result = service.insert(dto);

        assertNotNull(result);
        assertEquals(dto.getEmail(), result.getEmail());
    }

    @Test
    void updateShouldReturnDTOWhenIdExists() {
        User user = UserFactory.createClientUser();
        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setEmail("luciano@gmail.com");
        dto.setRoles(List.of(new RoleDTO(2L, "ROLE_ADMIN")));

        when(repository.getReferenceById(1L)).thenReturn(user);
        when(roleRepository.getReferenceById(2L)).thenReturn(new Role(2L, "ROLE_ADMIN"));
        when(repository.save(any())).thenReturn(user);

        UserDTO result = service.update(1L, dto);

        assertNotNull(result);
        assertEquals(dto.getEmail(), result.getEmail());
    }
    
    @Test
    void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Long nonExistingId = 999L;
        UserUpdateDTO dto = new UserUpdateDTO();

        when(repository.getReferenceById(nonExistingId))
            .thenThrow(EntityNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, dto);
        });
    }

    @Test
    void loadUserByUsernameShouldReturnUserWhenExists() {
        UserDetailsProjection projection = mock(UserDetailsProjection.class);
        when(projection.getUsername()).thenReturn("ana@gmail.com");
        when(projection.getPassword()).thenReturn("pass");
        when(projection.getRoleId()).thenReturn(1L);
        when(projection.getAuthority()).thenReturn("ROLE_CLIENT");

        when(repository.searchUserAndRolesByEmail("ana@gmail.com")).thenReturn(List.of(projection));

        UserDetails user = service.loadUserByUsername("ana@gmail.com");

        assertNotNull(user);
        assertEquals("ana@gmail.com", user.getUsername());
    }

    @Test
    void loadUserByUsernameShouldThrowWhenNotFound() {
        when(repository.searchUserAndRolesByEmail("notfound@gmail.com")).thenReturn(Collections.emptyList());

        assertThrows(UsernameNotFoundException.class, () -> {
            service.loadUserByUsername("notfound@gmail.com");
        });
    }
    
    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Long nonExistingId = 999L;

        when(repository.existsById(nonExistingId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });

        verify(repository, never()).deleteById(any());
    }
    @Test
    void deleteShouldThrowDatabaseExceptionWhenIntegrityViolationOccurs() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(true);
        doThrow(DataIntegrityViolationException.class)
            .when(repository).deleteById(id);

        assertThrows(DatabaseException.class, () -> {
            service.delete(id);
        });

        verify(repository).deleteById(id);
    }

    
    @Test
    void deleteShouldDoNothingWhenIdExists() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        assertDoesNotThrow(() -> {
            service.delete(1L);
        });
    }

    @Test
    void deleteShouldThrowWhenIdDoesNotExist() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(99L);
        });
    }
}

