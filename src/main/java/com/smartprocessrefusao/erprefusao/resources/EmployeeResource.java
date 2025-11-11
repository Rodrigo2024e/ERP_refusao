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

import com.smartprocessrefusao.erprefusao.dto.EmployeeDepartamentDTO;
import com.smartprocessrefusao.erprefusao.dto.ReportEmployeeDTO;
import com.smartprocessrefusao.erprefusao.services.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/employees")
public class EmployeeResource {

	@Autowired
	private EmployeeService employeeService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping
	public ResponseEntity<Page<EmployeeDepartamentDTO>> findEmployeeBySetor(@RequestParam(required = false) String name,
			@RequestParam(required = false) Long sectorId, Pageable pageable) {

		Page<EmployeeDepartamentDTO> list = employeeService.reportEmployeeBySector(name, sectorId, pageable);

		return ResponseEntity.ok(list);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(value = "/report")
	public ResponseEntity<Page<ReportEmployeeDTO>> getReportEmployee(@RequestParam(required = false) String name,
			@RequestParam(required = false) Long peopleId, Pageable pageable) {

		Page<ReportEmployeeDTO> result = employeeService.reportEmployee(name, peopleId, pageable);
		return ResponseEntity.ok(result);

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<EmployeeDepartamentDTO> findById(@PathVariable Long id) {
		EmployeeDepartamentDTO dto = employeeService.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<EmployeeDepartamentDTO> insert(@Valid @RequestBody EmployeeDepartamentDTO dto) {
		dto = employeeService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId())
				.toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<EmployeeDepartamentDTO> update(@PathVariable Long id, @Valid @RequestBody EmployeeDepartamentDTO dto) {
		dto = employeeService.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		employeeService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
