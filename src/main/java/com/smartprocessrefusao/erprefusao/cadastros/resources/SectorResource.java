package com.smartprocessrefusao.erprefusao.cadastros.resources;

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

import com.smartprocessrefusao.erprefusao.cadastros.dto.SectorDTO;
import com.smartprocessrefusao.erprefusao.cadastros.services.SectorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/sectors")
public class SectorResource {

	@Autowired
	private SectorService sectorService;
	
/*	
	@GetMapping
	public ResponseEntity<Page<SetorDTO>> findAll(
			@RequestParam(defaultValue = "") String setor,
			@RequestParam(defaultValue = "0") String funcionarioId,
			Pageable pageable){
			Page<SetorDTO> list = setorService.findAllPaged(setor, funcionarioId, pageable);		
			return ResponseEntity.ok().body(list);
		}
*/
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
	@GetMapping
	public ResponseEntity<List<SectorDTO>> findAll(){
			List<SectorDTO> list = sectorService.findAll();		
			return ResponseEntity.ok().body(list);
		}
/*	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
	@GetMapping
	public ResponseEntity<List<SetorFuncionarioDTO>> findAllSetorWithEmployees(){
			List<SetorFuncionarioDTO> list = setorService.findAllSetorWithEmployees();		
			return ResponseEntity.ok().body(list);
		}
*/	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<SectorDTO> findById(@PathVariable Long id){
		SectorDTO dto = sectorService.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<SectorDTO> insert(@Valid @RequestBody SectorDTO dto) {
		dto = sectorService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<SectorDTO> update(@PathVariable Long id, @Valid @RequestBody SectorDTO dto) {
		dto = sectorService.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		sectorService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
	
}
