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

import com.smartprocessrefusao.erprefusao.dto.MeltingDTO;
import com.smartprocessrefusao.erprefusao.dto.MeltingReportDTO;
import com.smartprocessrefusao.erprefusao.services.MeltingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/meltings")
public class MeltingResource {

	@Autowired
	private MeltingService meltingService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(value = "/{numberMelting}")
	public ResponseEntity<MeltingDTO> findById(@PathVariable Long numberMelting) {
		MeltingDTO dto = meltingService.findByNumberMelting(numberMelting);
		return ResponseEntity.ok().body(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<MeltingDTO> insert(@Valid @RequestBody MeltingDTO dto) {
		dto = meltingService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getNumberMelting()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "/{numberMelting}")
	public ResponseEntity<MeltingDTO> update(@PathVariable Long numberMelting, @Valid @RequestBody MeltingDTO dto) {
		dto = meltingService.update(numberMelting, dto);
		return ResponseEntity.ok().body(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping("/{numberMelting}")
	public ResponseEntity<Void> deleteByNumber(@PathVariable Long numberMelting) {
		meltingService.deleteByNumberMelting(numberMelting);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/report")
	public ResponseEntity<Page<MeltingReportDTO>> searchMeltingReport(
	        @RequestParam(required = false) Long partnerId,
	        @RequestParam(required = false) Long numberMelting,
	        @RequestParam(required = false)
	        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	        @RequestParam(required = false)
	        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
	        Pageable pageable) {

	    Page<MeltingReportDTO> page = meltingService.findDetails(
	            partnerId,
	            numberMelting,
	            startDate,
	            endDate,
	            pageable
	    );

	    return ResponseEntity.ok(page);
	}

}
