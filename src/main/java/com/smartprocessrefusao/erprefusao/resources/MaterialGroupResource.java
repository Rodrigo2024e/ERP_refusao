package com.smartprocessrefusao.erprefusao.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.smartprocessrefusao.erprefusao.dto.MaterialGroupDTO;
import com.smartprocessrefusao.erprefusao.services.MaterialGroupService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/matGroups")
public class MaterialGroupResource {

	@Autowired
	private MaterialGroupService materialGroupService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping
	public ResponseEntity<List<MaterialGroupDTO>> findAll() {
		List<MaterialGroupDTO> list = materialGroupService.findAll();
		return ResponseEntity.ok().body(list);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<MaterialGroupDTO> findById(@PathVariable Long id) {
		MaterialGroupDTO dto = materialGroupService.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<MaterialGroupDTO> insert(@Valid @RequestBody MaterialGroupDTO dto) {
		dto = materialGroupService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<MaterialGroupDTO> update(@PathVariable Long id, @Valid @RequestBody MaterialGroupDTO dto) {
		dto = materialGroupService.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		materialGroupService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
