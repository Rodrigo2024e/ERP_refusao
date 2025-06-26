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

import com.smartprocessrefusao.erprefusao.dto.UnitOfMeasureDTO;
import com.smartprocessrefusao.erprefusao.services.UnitOfMeasureService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/units")
public class UnitOfMeasureResource {

	@Autowired
	private UnitOfMeasureService UnitOfMeasureService;
	
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping
	public ResponseEntity<List<UnitOfMeasureDTO>> findAll(){
			List<UnitOfMeasureDTO> list = UnitOfMeasureService.findAll();		
			return ResponseEntity.ok().body(list);
		}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<UnitOfMeasureDTO> findById(@PathVariable Long id){
		UnitOfMeasureDTO dto = UnitOfMeasureService.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<UnitOfMeasureDTO> insert(@Valid @RequestBody UnitOfMeasureDTO dto) {
		dto = UnitOfMeasureService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<UnitOfMeasureDTO> update(@PathVariable Long id, @Valid @RequestBody UnitOfMeasureDTO dto) {
		dto = UnitOfMeasureService.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		UnitOfMeasureService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
}
