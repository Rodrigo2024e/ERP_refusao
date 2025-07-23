package com.smartprocessrefusao.erprefusao.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.smartprocessrefusao.erprefusao.dto.EmployeeDTO;
import com.smartprocessrefusao.erprefusao.dto.ReportEmployeeDTO;
import com.smartprocessrefusao.erprefusao.dto.ReportEmployeeSectorDTO;
import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.entities.Sector;
import com.smartprocessrefusao.erprefusao.projections.ReportEmployeeProjection;
import com.smartprocessrefusao.erprefusao.projections.ReportEmployeeSectorProjection;
import com.smartprocessrefusao.erprefusao.repositories.EmployeeRepository;
import com.smartprocessrefusao.erprefusao.repositories.SectorRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.EmployeeFactory.Factory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

	@InjectMocks
	private EmployeeService service;

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private SectorRepository sectorRepository;

	private Employee employee;
	private EmployeeDTO employeeDTO;
	private Sector sector;

	@BeforeEach
	void setUp() {
		employee = Factory.createEmployee();
		employeeDTO = Factory.createEmployeeDTO();
		sector = Factory.createSector();
	}

	// 1 - Report Employee
	@Test
	void reportEmployeeShouldReturnPagedReportEmployeeDTO() {
		ReportEmployeeProjection projection = Mockito.mock(ReportEmployeeProjection.class);
		Page<ReportEmployeeProjection> page = new PageImpl<>(List.of(projection));
		when(employeeRepository.searchPeopleNameByOrId(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(page);

		Page<ReportEmployeeDTO> result = service.reportEmployee("João", 1L, PageRequest.of(0, 10));

		Assertions.assertNotNull(result);
	}

	// 2 - Report Employee with Sector
	@Test
	void reportEmployeeShouldReturnPagedReportEmployeeBySectorDTO() {
		ReportEmployeeSectorProjection projection = Mockito.mock(ReportEmployeeSectorProjection.class);
		Page<ReportEmployeeSectorProjection> page = new PageImpl<>(List.of(projection));
		when(employeeRepository.searchEmployeeBySector(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(page);

		Page<ReportEmployeeSectorDTO> result = service.reportEmployeeBySector("João", 1L, PageRequest.of(0, 10));

		Assertions.assertNotNull(result);
	}

	// 3 - FindById
	@Test
	void findByIdShouldReturnEmployeeDTOWhenIdExists() {
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

		EmployeeDTO result = service.findById(1L);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("João", result.getName());
	}

	// 4 - FindById-EntityNotFoundException
	@Test
	void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(99L);
		});
	}

	// 5 - Insert Employee
	@Test
	void insertShouldSaveEmployeeAndReturnDTO() {
		when(sectorRepository.findById(1L)).thenReturn(Optional.of(sector));
		when(employeeRepository.save(Mockito.any())).thenReturn(employee);

		EmployeeDTO result = service.insert(employeeDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("João", result.getName());
		verify(employeeRepository).save(Mockito.any());
	}

	// 6 - Insert Sector Invalid
	@Test
	void copyDtoToEntityShoulNotFoundExceptionWhenSectorDTOInvalid() {
		when(sectorRepository.findById(1L)).thenReturn(Optional.empty());

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.insert(employeeDTO);
		});
	}

	// 8 - Update Employee
	@Test
	void updateShouldUpdateEmployeeDTOWhenIdExists() {
		when(employeeRepository.getReferenceById(1L)).thenReturn(employee);
		when(sectorRepository.findById(1L)).thenReturn(Optional.of(sector));
		when(employeeRepository.save(employee)).thenReturn(employee);

		EmployeeDTO result = service.update(1L, employeeDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("João", result.getName());
	}

	// 8 - Update Employee Invalid
	@Test
	void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		when(employeeRepository.getReferenceById(999L)).thenThrow(EntityNotFoundException.class);

		assertThrows(ResourceNotFoundException.class, () -> {
			service.update(999L, employeeDTO);
		});
	}

	// 9 - Delete Id exists
	@Test
	void deleteShouldDoNothingWhenIdExists() {
		Mockito.when(employeeRepository.existsById(1L)).thenReturn(true);
		Mockito.doNothing().when(employeeRepository).deleteById(1L);

		Assertions.assertDoesNotThrow(() -> service.delete(1L));
	}

	// 10 - Delete Id Does Not exists
	@Test
	void deleteShouldThrowResourceNotFoundWhenIdDoesNotExist() {
		Mockito.when(employeeRepository.existsById(99L)).thenReturn(false);

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(99L);
		});
	}

	// 11 - Delete Id Dependent
	@Test
	void deleteShouldThrowDatabaseExceptionOnIntegrityViolation() {
		Mockito.when(employeeRepository.existsById(1L)).thenReturn(true);
		Mockito.doThrow(DataIntegrityViolationException.class).when(employeeRepository).deleteById(1L);

		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(1L);
		});
	}

}
