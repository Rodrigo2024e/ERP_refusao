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

import com.smartprocessrefusao.erprefusao.dto.DispatchDTO;
import com.smartprocessrefusao.erprefusao.dto.DispatchReportDTO;
import com.smartprocessrefusao.erprefusao.services.DispatchService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/dispatches")
public class DispatchResource {

	@Autowired
	private DispatchService dispatchService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(value = "/report")
	public ResponseEntity<Page<DispatchReportDTO>> getReportDispatch(@RequestParam(required = false) String description,
			@RequestParam(required = false) Long numTicket, @RequestParam(required = false) Long people_id,
			Pageable pageable) {

		Page<DispatchReportDTO> result = dispatchService.reportDispatch(description, numTicket, people_id, pageable);
		return ResponseEntity.ok(result);

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<DispatchDTO> insert(@Valid @RequestBody DispatchDTO dto) {
		dto = dispatchService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getNumTicket())
				.toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping(value = "/numTicket/{numTicket}")
	public ResponseEntity<DispatchDTO> updateByNumTicket(@PathVariable Long numTicket, @RequestBody DispatchDTO dto) {
		DispatchDTO updatedDto = dispatchService.updateByNumTicket(numTicket, dto);
		return ResponseEntity.ok(updatedDto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/numTicket/{numTicket}")
	public ResponseEntity<Void> delete(@PathVariable Long numTicket) {
		dispatchService.delete(numTicket);
		return ResponseEntity.noContent().build();
	}

}
