package com.smartprocessrefusao.erprefusao.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.EmployeeSectorDTO;
import com.smartprocessrefusao.erprefusao.dto.EmployeeReportDTO;
import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.entities.Sector;
import com.smartprocessrefusao.erprefusao.projections.EmployeeSectorProjection;
import com.smartprocessrefusao.erprefusao.projections.EmployeeReportProjection;
import com.smartprocessrefusao.erprefusao.repositories.EmployeeRepository;
import com.smartprocessrefusao.erprefusao.repositories.SectorRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private SectorRepository sectorRepository;

	@Transactional(readOnly = true)
	public Page<EmployeeSectorDTO> reportEmployeeBySector(String name, Long sectorId, Pageable pageable) {
		Page<EmployeeSectorProjection> page = employeeRepository.searchEmployeeBySector(name, sectorId, pageable);
		return page.map(EmployeeSectorDTO::new);
	}

	@Transactional(readOnly = true)
	public Page<EmployeeReportDTO> reportEmployee(String name, Long peopleId, Pageable pageable) {
		Page<EmployeeReportProjection> page = employeeRepository.searchPeopleNameByOrId(name, peopleId, pageable);
		return page.map(EmployeeReportDTO::new);
	}

	@Transactional(readOnly = true)
	public EmployeeSectorDTO findById(Long id) {
		try {
			Optional<Employee> obj = employeeRepository.findById(id);
			Employee entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
			return new EmployeeSectorDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}

	}

	@Transactional
	public EmployeeSectorDTO insert(EmployeeSectorDTO dto) {
		Employee entity = new Employee();
		copyDtoToEntity(dto, entity);
		entity = employeeRepository.save(entity);
		return new EmployeeSectorDTO(entity);
	}

	@Transactional
	public EmployeeSectorDTO update(Long id, EmployeeSectorDTO dto) {
		try {
			Employee entity = employeeRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = employeeRepository.save(entity);
			return new EmployeeSectorDTO(entity);
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

	public void copyDtoToEntity(EmployeeSectorDTO dto, Employee entity) {
		entity.setName(dto.getName().toUpperCase());
		entity.setEmail(dto.getEmail().toUpperCase());
		entity.setCellPhone(dto.getCellPhone());
		entity.setTelephone(dto.getTelephone());
		entity.setCpf(dto.getCpf());
		entity.setRg(dto.getRg());
		entity.setSysUser(dto.isSysUser());

		Optional.ofNullable(dto.getSectorId()).ifPresent(id -> {
			Sector sector = sectorRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Setor não encontrado"));
			entity.setSector(sector);
		});

	}
}
