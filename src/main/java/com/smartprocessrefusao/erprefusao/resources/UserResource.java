package com.smartprocessrefusao.erprefusao.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smartprocessrefusao.erprefusao.dto.UserDTO;
import com.smartprocessrefusao.erprefusao.dto.UserInsertDTO;
import com.smartprocessrefusao.erprefusao.dto.UserUpdateDTO;
import com.smartprocessrefusao.erprefusao.services.UserService;

import jakarta.validation.Valid;

	@RestController
	@RequestMapping("/users")
	public class UserResource {

	    @Autowired
	    private UserService service;

		@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	    @GetMapping
	    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
	        Page<UserDTO> list = service.findAllPaged(pageable);
	        return ResponseEntity.ok(list);
	    }

		@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	    @GetMapping(value = "/{id}")
	    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
	        UserDTO dto = service.findById(id);
	        return ResponseEntity.ok(dto);
	    }

		@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	    @PostMapping
	    public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserInsertDTO dto) {
	        UserDTO newDto = service.insert(dto);
	        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	                .buildAndExpand(newDto.getId()).toUri();
	        return ResponseEntity.created(uri).body(newDto);
	    }

		@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	    @PutMapping(value = "/{id}")
	    public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO dto) {
	        UserDTO newDto = service.update(id, dto);
	        return ResponseEntity.ok(newDto);
	    }

		@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	    @DeleteMapping(value = "/{id}")
	    public ResponseEntity<Void> delete(@PathVariable Long id) {
	        service.delete(id);
	        return ResponseEntity.noContent().build();
	    }
	}


