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

import com.smartprocessrefusao.erprefusao.dto.ReceiptDTO;
import com.smartprocessrefusao.erprefusao.dto.ReceiptReportDTO;
import com.smartprocessrefusao.erprefusao.services.ReceiptService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/receipts")
public class ReceiptResource {

	@Autowired
	private ReceiptService receiptService;
/*
	@Autowired
	private ReceiptReportService receiptReportService;
*/
	@PostMapping
	public ResponseEntity<ReceiptDTO> insert(@Valid @RequestBody ReceiptDTO dto) {
		ReceiptDTO newDto = receiptService.insert(dto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();

		return ResponseEntity.created(uri).body(newDto);
	}

	@PutMapping("/numTicket/{numTicket}")
	public ResponseEntity<ReceiptDTO> updateByNumTicket(@PathVariable Long numTicket,
			@RequestBody @Valid ReceiptDTO dto) {

		ReceiptDTO updatedDto = receiptService.updateByNumTicket(numTicket, dto);
		return ResponseEntity.ok(updatedDto);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/numTicket/{numTicket}")
	public ResponseEntity<Void> delete(@PathVariable Long numTicket) {
		receiptService.delete(numTicket);
		return ResponseEntity.noContent().build();
	}

	// REPORT
	@GetMapping("/report-page")
	public ResponseEntity<Page<ReceiptReportDTO>> reportReceipt(
			@RequestParam(required = false) Long id,
			@RequestParam(required = false) Long numTicket, 
			@RequestParam(required = false) Long partnerId,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam(required = false) Long materialCode, Pageable pageable) {

		Page<ReceiptReportDTO> page = receiptService.findDetails(
				id,
				numTicket, 
				startDate,
				endDate, 
				pageable);

		return ResponseEntity.ok(page);
	}
}
