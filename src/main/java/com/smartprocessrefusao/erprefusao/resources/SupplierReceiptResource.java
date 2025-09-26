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

import com.smartprocessrefusao.erprefusao.dto.SupplierReceiptReportDTO;
import com.smartprocessrefusao.erprefusao.dto.SupplierReceiptDTO;
import com.smartprocessrefusao.erprefusao.services.SupplierReceiptService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/supplierReceipts")
public class SupplierReceiptResource {

	@Autowired
	private SupplierReceiptService supplierReceiptService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(value = "/report")
	public ResponseEntity<Page<SupplierReceiptReportDTO>> getReportSupplierReceipt(
			@RequestParam(required = false) Long inputId, Pageable pageable) {

		Page<SupplierReceiptReportDTO> result = supplierReceiptService.reportSupplierReceipt(inputId, pageable);
		return ResponseEntity.ok(result);

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<SupplierReceiptDTO> findById(@PathVariable Long id) {
		SupplierReceiptDTO dto = supplierReceiptService.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<SupplierReceiptDTO> insert(@Valid @RequestBody SupplierReceiptDTO dto) {
		dto = supplierReceiptService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<SupplierReceiptDTO> update(@PathVariable Long id,
			@Valid @RequestBody SupplierReceiptDTO dto) {
		dto = supplierReceiptService.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		supplierReceiptService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
