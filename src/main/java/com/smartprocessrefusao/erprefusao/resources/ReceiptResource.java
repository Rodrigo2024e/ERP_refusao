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

import com.smartprocessrefusao.erprefusao.dto.ReceiptDTO;
import com.smartprocessrefusao.erprefusao.dto.ReportReceiptDTO;
import com.smartprocessrefusao.erprefusao.services.ReceiptService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/receipts")
public class ReceiptResource {

	@Autowired
	private ReceiptService receiptService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(value = "/report")
	public ResponseEntity<Page<ReportReceiptDTO>> getReportReceipt(@RequestParam(required = false) Long receiptId,
			@RequestParam(required = false) String description, @RequestParam(required = false) Long numTicket,
			@RequestParam(required = false) Long people_id, Pageable pageable) {

		Page<ReportReceiptDTO> result = receiptService.reportReceipt(receiptId, description, numTicket, people_id,
				pageable);
		return ResponseEntity.ok(result);

	}

	@PostMapping
	public ResponseEntity<ReceiptDTO> insert(@Valid @RequestBody ReceiptDTO dto) {
		ReceiptDTO newDto = receiptService.insert(dto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();

		return ResponseEntity.created(uri).body(newDto);
	}

	@PutMapping(value = "/numTicket/{numTicket}")
	public ResponseEntity<ReceiptDTO> updateByNumTicket(@PathVariable Long numTicket, @RequestBody ReceiptDTO dto) {
		ReceiptDTO updatedDto = receiptService.updateByNumTicket(numTicket, dto);
		return ResponseEntity.ok(updatedDto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/numTicket/{numTicket}")
	public ResponseEntity<Void> delete(@PathVariable Long numTicket) {
		receiptService.delete(numTicket);
		return ResponseEntity.noContent().build();
	}
}