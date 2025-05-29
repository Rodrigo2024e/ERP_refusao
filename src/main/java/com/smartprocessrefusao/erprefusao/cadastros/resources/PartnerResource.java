package com.smartprocessrefusao.erprefusao.cadastros.resources;

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

import com.smartprocessrefusao.erprefusao.cadastros.dto.PartnerDTO;
import com.smartprocessrefusao.erprefusao.cadastros.dto.ReportPartnerDTO;
import com.smartprocessrefusao.erprefusao.cadastros.services.PartnerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/partners")
public class PartnerResource {

	@Autowired
	private PartnerService partnerService;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
	@GetMapping(value = "/report")
	 public ResponseEntity<Page<ReportPartnerDTO>> getReportPartner(
			 @RequestParam(name = "name", required = false) String name,
	            @RequestParam(name = "partnerId", required = false) Long partnerId,
	            Pageable pageable) {
	        
	        Page<ReportPartnerDTO> result = partnerService.reportPartner(name, partnerId, pageable);
	        return ResponseEntity.ok(result);
	    
	}
	
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<PartnerDTO> insert(@Valid @RequestBody PartnerDTO dto) {
		dto = partnerService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<PartnerDTO> update(@PathVariable Long id, @Valid @RequestBody PartnerDTO dto) {
		dto = partnerService.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		partnerService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
}
