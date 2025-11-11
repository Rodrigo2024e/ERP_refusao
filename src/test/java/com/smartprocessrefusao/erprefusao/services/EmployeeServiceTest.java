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

import com.smartprocessrefusao.erprefusao.dto.EmployeeDepartamentDTO;
import com.smartprocessrefusao.erprefusao.dto.ReportEmployeeDTO;
import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.entities.Departament;
import com.smartprocessrefusao.erprefusao.projections.EmployeeDepartamentProjection;
import com.smartprocessrefusao.erprefusao.projections.EmployeeReportProjection;
import com.smartprocessrefusao.erprefusao.repositories.EmployeeRepository;
import com.smartprocessrefusao.erprefusao.repositories.DepartamentRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.EmployeeFactory;
import com.smartprocessrefusao.erprefusao.tests.EmployeeFactory.Factory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

	@InjectMocks
	private EmployeeService service;

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private DepartamentRepository departamentRepository;

	private Employee employee;
	private EmployeeDepartamentDTO employeeDTO;
	private Departament departament;

	@BeforeEach
	void setUp() {
		employee = Factory.createEmployee();
		employeeDTO = Factory.createEmployeeDTO();
		departament = Factory.createSector();
		EmployeeFactory.create();
	}

	// 1 - Report Employee
	@Test
	void reportEmployeeShouldReturnPagedReportEmployeeDTO() {
		EmployeeReportProjection projection = Mockito.mock(EmployeeReportProjection.class);
		Page<EmployeeReportProjection> page = new PageImpl<>(List.of(projection));
		when(employeeRepository.searchPeopleNameByOrId(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(page);

		Page<ReportEmployeeDTO> result = service.reportEmployee("João", 1L, PageRequest.of(0, 10));

		Assertions.assertNotNull(result);
	}

	// 2 - Report Employee with Sector
	@Test
	void reportEmployeeShouldReturnPagedReportEmployeeByDepartamentDTO() {
		EmployeeDepartamentProjection projection = Mockito.mock(EmployeeDepartamentProjection.class);
		Page<EmployeeDepartamentProjection> page = new PageImpl<>(List.of(projection));
		when(employeeRepository.searchEmployeeByDepartament(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(page);

		Page<EmployeeDepartamentDTO> result = service.reportEmployeeBySector("João", 1L, PageRequest.of(0, 10));

		Assertions.assertNotNull(result);
	}

	// 3 - FindById
	@Test
	void findByIdShouldReturnEmployeeDTOWhenIdExists() {
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

		EmployeeDepartamentDTO result = service.findById(1L);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("JONATHAS JUNIO", result.getName());
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
		when(departamentRepository.findById(12L)).thenReturn(Optional.of(departament));
		when(employeeRepository.save(Mockito.any())).thenReturn(employee);

		EmployeeDepartamentDTO result = service.insert(employeeDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("JONATHAS JUNIO", result.getName());
		verify(employeeRepository).save(Mockito.any());
	}

	// 6 - Insert Sector Invalid
	@Test
	void copyDtoToEntityShoulNotFoundExceptionWhenSectorDTOInvalid() {
		when(departamentRepository.findById(12L)).thenReturn(Optional.empty());

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.insert(employeeDTO);
		});
	}

	// 8 - Update Employee
	@Test
	void updateShouldUpdateEmployeeDTOWhenIdExists() {
		when(employeeRepository.getReferenceById(1L)).thenReturn(employee);
		when(departamentRepository.findById(12L)).thenReturn(Optional.of(departament));
		when(employeeRepository.save(employee)).thenReturn(employee);

		EmployeeDepartamentDTO result = service.update(1L, employeeDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("JONATHAS JUNIO", result.getName());
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
