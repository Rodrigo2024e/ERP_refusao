package com.smartprocessrefusao.erprefusao.cadastros.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smartprocessrefusao.erprefusao.cadastros.dto.ParceiroDTO;
import com.smartprocessrefusao.erprefusao.cadastros.services.ParceiroService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/parceiros")
public class ParceiroResource {

	@Autowired
	private ParceiroService parceiroService;
	
	@GetMapping
	public ResponseEntity<Page<ParceiroDTO>> findAll(Pageable pageable) {
		Page<ParceiroDTO> list = parceiroService.findAllPaged(pageable);		
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ParceiroDTO> findById(@PathVariable Long id){
		ParceiroDTO dto = parceiroService.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<ParceiroDTO> insert(@Valid @RequestBody ParceiroDTO dto) {
		dto = parceiroService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ParceiroDTO> update(@PathVariable Long id, @Valid @RequestBody ParceiroDTO dto) {
		dto = parceiroService.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		parceiroService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
}
