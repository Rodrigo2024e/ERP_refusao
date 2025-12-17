package com.smartprocessrefusao.erprefusao.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.EmployeeDepartamentDTO;
import com.smartprocessrefusao.erprefusao.dto.EmployeeReportDTO;
import com.smartprocessrefusao.erprefusao.entities.Departament;
import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.projections.EmployeeDepartamentReportProjection;
import com.smartprocessrefusao.erprefusao.projections.EmployeeReportProjection;
import com.smartprocessrefusao.erprefusao.repositories.DepartamentRepository;
import com.smartprocessrefusao.erprefusao.repositories.EmployeeRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DepartamentRepository departamentRepository;

	@Transactional(readOnly = true)
	public Page<EmployeeDepartamentDTO> reportEmployeeBySector(String name, Long sectorId, Pageable pageable) {
		Page<EmployeeDepartamentReportProjection> page = employeeRepository.searchEmployeeByDepartament(name, sectorId,
				pageable);
		return page.map(EmployeeDepartamentDTO::new);
	}

	@Transactional(readOnly = true)
	public Page<EmployeeReportDTO> reportEmployee(String name, Long peopleId, Pageable pageable) {
		Page<EmployeeReportProjection> page = employeeRepository.searchPeopleNameByOrId(name, peopleId, pageable);
		return page.map(EmployeeReportDTO::new);
	}

	@Transactional(readOnly = true)
	public EmployeeDepartamentDTO findById(Long id) {
		try {
			Optional<Employee> obj = employeeRepository.findById(id);
			Employee entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
			return new EmployeeDepartamentDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}

	}

	@Transactional
	public EmployeeDepartamentDTO insert(EmployeeDepartamentDTO dto) {
		if (employeeRepository.existsByEmail(dto.getEmail())) {
			throw new IllegalArgumentException("E-mail já cadastrado!");
		}

		if (employeeRepository.existsByCpf(dto.getCpf())) {
			throw new IllegalArgumentException("CPF já cadastrado!");
		}

		Employee entity = new Employee();
		copyDtoToEntity(dto, entity);
		entity = employeeRepository.save(entity);
		return new EmployeeDepartamentDTO(entity);
	}

	@Transactional
	public EmployeeDepartamentDTO update(Long id, EmployeeDepartamentDTO dto) {

		try {
			Employee entity = employeeRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = employeeRepository.save(entity);
			return new EmployeeDepartamentDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!employeeRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			employeeRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	public void copyDtoToEntity(EmployeeDepartamentDTO dto, Employee entity) {
		entity.setName(dto.getName().toUpperCase());
		entity.setEmail(dto.getEmail().toLowerCase());
		entity.setCellPhone(dto.getCellPhone());
		entity.setTelephone(dto.getTelephone());
		entity.setCpf(dto.getCpf());
		entity.setDateOfBirth(dto.getDateOfBirth());

		Optional.ofNullable(dto.getDepartamentId()).ifPresent(id -> {
			Departament departament = departamentRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Setor não encontrado"));
			entity.setDepartament(departament);
		});

	}
}
