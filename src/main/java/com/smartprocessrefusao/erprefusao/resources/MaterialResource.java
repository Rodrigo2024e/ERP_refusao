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
import com.smartprocessrefusao.erprefusao.dto.ReportMaterialDTO;
import com.smartprocessrefusao.erprefusao.services.MaterialService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/materials")
public class MaterialResource {

	@Autowired
	private MaterialService materialService;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(value = "/report")
	 public ResponseEntity<Page<ReportMaterialDTO>> getReportMaterial(
			 @RequestParam(name = "description", required = false) String description,
	            @RequestParam(name = "materialId", required = false) Long materialId,
	            Pageable pageable) {
	        
	        Page<ReportMaterialDTO> result = materialService.reportMaterial(description, materialId, pageable);
	        return ResponseEntity.ok(result);
	    
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<MaterialDTO> findById(@PathVariable Long id){
		MaterialDTO dto = materialService.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<MaterialDTO> insert(@Valid @RequestBody MaterialDTO dto) {
		dto = materialService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<MaterialDTO> update(@PathVariable Long id, @Valid @RequestBody MaterialDTO dto) {
		dto = materialService.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		materialService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
	
}
