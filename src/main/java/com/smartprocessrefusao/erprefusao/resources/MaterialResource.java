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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smartprocessrefusao.erprefusao.dto.MaterialDTO;
import com.smartprocessrefusao.erprefusao.dto.MateriaReportlDTO;
import com.smartprocessrefusao.erprefusao.services.MaterialService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/materials")
public class MaterialResource {

	@Autowired
	private MaterialService materialService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(value = "/report")
	public ResponseEntity<Page<MateriaReportlDTO>> getReportMaterial(@RequestParam(required = false) String type, @RequestParam(required = false) Long code, @RequestParam(required = false) String description,
			@RequestParam(required = false) Long groupId, Pageable pageable) {

		Page<MateriaReportlDTO> result = materialService.reportMaterial(type, code, description, groupId, pageable);
		return ResponseEntity.ok(result);

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(value = "/{code}")
	public ResponseEntity<MaterialDTO> findByCode(@PathVariable Long code) {
		MaterialDTO dto = materialService.findByCode(code);
		return ResponseEntity.ok().body(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<MaterialDTO> insert(@Valid @RequestBody MaterialDTO dto) {
		dto = materialService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getCode())
				.toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "/{code}")
	public ResponseEntity<MaterialDTO> update(@PathVariable Long code, @Valid @RequestBody MaterialDTO dto) {
		dto = materialService.update(code, dto);
		return ResponseEntity.ok().body(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{code}")
	public ResponseEntity<Void> delete(@PathVariable Long code) {
		materialService.delete(code);
		return ResponseEntity.noContent().build();
	}

}
