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

import com.smartprocessrefusao.erprefusao.dto.ProductDispatchDTO;
import com.smartprocessrefusao.erprefusao.dto.ReportProductDispatchDTO;
import com.smartprocessrefusao.erprefusao.services.ProductDispatchService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/productDispatches")
public class ProductDispatchResource {

	@Autowired
	private ProductDispatchService productDispatchService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(value = "/report")
	public ResponseEntity<Page<ReportProductDispatchDTO>> getReportMovement(
			@RequestParam(required = false) Integer numTicketId, Pageable pageable) {

		Page<ReportProductDispatchDTO> result = productDispatchService.reportDispatch(numTicketId, pageable);
		return ResponseEntity.ok(result);

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDispatchDTO> findById(@PathVariable Long id) {
		ProductDispatchDTO dto = productDispatchService.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<ProductDispatchDTO> insert(@Valid @RequestBody ProductDispatchDTO dto) {
		dto = productDispatchService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDispatchDTO> update(@PathVariable Long id,
			@Valid @RequestBody ProductDispatchDTO dto) {
		dto = productDispatchService.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		productDispatchService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
