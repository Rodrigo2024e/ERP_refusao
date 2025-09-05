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

import com.smartprocessrefusao.erprefusao.dto.ScrapReceiptDTO;
import com.smartprocessrefusao.erprefusao.services.ScrapReceiptService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/scrapReceipts")
public class ScrapReceiptResource {

	@Autowired
	private ScrapReceiptService scrapReceiptService;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping
	 public ResponseEntity<Page<ScrapReceiptDTO>> getReportMovement(
			 @RequestParam(required = false) Integer numTicketId, Pageable pageable) {
	        
	        Page<ScrapReceiptDTO> result = scrapReceiptService.reportMovement(numTicketId, pageable);
	        return ResponseEntity.ok(result);
	    
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<ScrapReceiptDTO> findById(@PathVariable Long id){
		ScrapReceiptDTO dto = scrapReceiptService.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<ScrapReceiptDTO> insert(@Valid @RequestBody ScrapReceiptDTO dto) {
		dto = scrapReceiptService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<ScrapReceiptDTO> update(@PathVariable Long id, @Valid @RequestBody ScrapReceiptDTO dto) {
		dto = scrapReceiptService.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		scrapReceiptService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
	
}
