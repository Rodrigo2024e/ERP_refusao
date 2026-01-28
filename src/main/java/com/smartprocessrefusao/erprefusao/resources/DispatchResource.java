package com.smartprocessrefusao.erprefusao.resources;

import java.net.URI;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.smartprocessrefusao.erprefusao.dto.DispatchDTO;
import com.smartprocessrefusao.erprefusao.dto.DispatchReportDTO;
import com.smartprocessrefusao.erprefusao.services.DispatchService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/dispatches")
public class DispatchResource {

	@Autowired
	private DispatchService dispatchService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<DispatchDTO> insert(@Valid @RequestBody DispatchDTO dto) {
		dto = dispatchService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getTicketId())
				.toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping(value = "/numTicket/{numTicket}")
	public ResponseEntity<DispatchDTO> updateByNumTicket(
			@PathVariable Long numTicket, 
			@RequestBody @Valid DispatchDTO dto) {
		DispatchDTO updatedDto = dispatchService.updateByNumTicket(numTicket, dto);
		return ResponseEntity.ok(updatedDto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/numTicket/{numTicket}")
	public ResponseEntity<Void> delete(@PathVariable Long numTicket) {
		dispatchService.delete(numTicket);
		return ResponseEntity.noContent().build();
	}

	// REPORT
	@GetMapping("/report-page")
	public ResponseEntity<Page<DispatchReportDTO>> reportDispatch(
			@RequestParam(required = false) Long dispatchIds,
			@RequestParam(required = false) Long numTicketId, 
			@RequestParam(required = false) Long partnerId,
			@RequestParam(required = false) String productDescription,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam(required = false) Long productCode, 
			Pageable pageable) {

		Page<DispatchReportDTO> page = dispatchService.findDetails(
				dispatchIds, 
				numTicketId, 
				startDate, 
				endDate, 
				partnerId,
				productDescription, 
				productCode, 
				pageable);

		return ResponseEntity.ok(page);
	}

}
