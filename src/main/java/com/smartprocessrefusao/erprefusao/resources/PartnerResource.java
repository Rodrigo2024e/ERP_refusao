package com.smartprocessrefusao.erprefusao.resources;

import java.net.URI;
import java.util.List;

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

import com.smartprocessrefusao.erprefusao.dto.PartnerDTO;
import com.smartprocessrefusao.erprefusao.dto.PartnerReportDTO;
import com.smartprocessrefusao.erprefusao.services.PartnerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/partners")
public class PartnerResource {

	@Autowired
	private PartnerService partnerService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping
	public ResponseEntity<List<PartnerDTO>> findAll() {
		List<PartnerDTO> list = partnerService.findAll();
		return ResponseEntity.ok().body(list);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(value = "/report")
	public ResponseEntity<Page<PartnerReportDTO>> getReportPartner(@RequestParam(required = false) String name,
			@RequestParam(required = false) Long partnerId, Pageable pageable) {

		Page<PartnerReportDTO> result = partnerService.reportPartner(name, partnerId, pageable);
		return ResponseEntity.ok(result);

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<PartnerDTO> findById(@PathVariable Long id) {
		PartnerDTO dto = partnerService.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<PartnerDTO> insert(@Valid @RequestBody PartnerDTO dto) {
		dto = partnerService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
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
