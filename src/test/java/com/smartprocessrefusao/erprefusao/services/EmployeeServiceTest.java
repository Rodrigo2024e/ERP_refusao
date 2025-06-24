package com.smartprocessrefusao.erprefusao.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.smartprocessrefusao.erprefusao.dto.EmployeeDTO;
import com.smartprocessrefusao.erprefusao.dto.EmployeeSectorDTO;
import com.smartprocessrefusao.erprefusao.dto.ReportEmployeeDTO;
import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.entities.Sector;
import com.smartprocessrefusao.erprefusao.projections.ReportEmployeeProjection;
import com.smartprocessrefusao.erprefusao.repositories.EmployeeRepository;
import com.smartprocessrefusao.erprefusao.repositories.SectorRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.EmployeeFactory;

import jakarta.persistence.EntityNotFoundException;

class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService service;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private SectorRepository sectorRepository;

    private Employee employee;
    private EmployeeDTO dto;
    private Sector sector;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = EmployeeFactory.createEmployee();
        dto = EmployeeFactory.createEmployeeDTO();
        sector = EmployeeFactory.createSector();
    }

    // ✅ insert
    @Test
    void insertShouldReturnEmployeeDTOWhenSuccessful() {
        when(sectorRepository.findById(dto.getSectorId())).thenReturn(Optional.of(sector));
        when(employeeRepository.save(any())).thenReturn(employee);

        EmployeeDTO result = service.insert(dto);

        assertNotNull(result);
        assertEquals(dto.getName(), result.getName());
        verify(employeeRepository).save(any());
    }

    // ✅ update sucesso
    @Test
    void updateShouldReturnUpdatedDTOWhenIdExists() {
        when(employeeRepository.getReferenceById(1L)).thenReturn(employee);
        when(sectorRepository.findById(dto.getSectorId())).thenReturn(Optional.of(sector));
        when(employeeRepository.save(any())).thenReturn(employee);

        EmployeeDTO result = service.update(1L, dto);

        assertNotNull(result);
        assertEquals(dto.getEmail(), result.getEmail());
        verify(employeeRepository).getReferenceById(1L);
        verify(employeeRepository).save(any());
    }

    // ✅ update erro
    @Test
    void updateShouldThrowWhenIdDoesNotExist() {
        when(employeeRepository.getReferenceById(999L)).thenThrow(EntityNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> service.update(999L, dto));
    }

    // ✅ delete sucesso
    @Test
    void deleteShouldDoNothingWhenIdExists() {
        when(employeeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(1L);

        assertDoesNotThrow(() -> service.delete(1L));
        verify(employeeRepository).deleteById(1L);
    }

    // ✅ delete id inexistente
    @Test
    void deleteShouldThrowResourceNotFoundWhenIdDoesNotExist() {
        when(employeeRepository.existsById(999L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.delete(999L));
        verify(employeeRepository, never()).deleteById(any());
    }

    // ✅ delete erro integridade
    @Test
    void deleteShouldThrowDatabaseExceptionWhenIntegrityViolation() {
        when(employeeRepository.existsById(1L)).thenReturn(true);
        doThrow(DataIntegrityViolationException.class).when(employeeRepository).deleteById(1L);

        assertThrows(DatabaseException.class, () -> service.delete(1L));
    }

    // ✅ findById sucesso
    @Test
    void findByIdShouldReturnDTOWhenExists() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        EmployeeDTO result = service.findById(1L);

        assertNotNull(result);
        assertEquals(employee.getEmail(), result.getEmail());
    }

    // ✅ findById erro
    @Test
    void findByIdShouldThrowWhenNotFound() {
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(999L));
    }

    // ✅ reportEmployee
    @Test
    void reportEmployeeShouldReturnPageOfDTO() {
        ReportEmployeeProjection projection = mock(ReportEmployeeProjection.class);
        when(projection.getName()).thenReturn("João Carlos");

        Page<ReportEmployeeProjection> page = new PageImpl<>(List.of(projection));
        when(employeeRepository.searchPeopleNameByOrId(any(), any(), any())).thenReturn(page);

        Pageable pageable = PageRequest.of(0, 10);
        Page<ReportEmployeeDTO> result = service.reportEmployee("João", 1L, pageable);

        assertNotNull(result);
        assertEquals("João Carlos", result.getContent().get(0).getName());
    }

    // ✅ EmployeesBySector
    @Test
    void employeesBySectorShouldReturnPageOfDTO() {
        Page<Employee> page = new PageImpl<>(List.of(employee));
        when(sectorRepository.getReferenceById(1L)).thenReturn(sector);
        when(employeeRepository.searchBySector(any(), any(), any())).thenReturn(page);

        Pageable pageable = PageRequest.of(0, 10);
        Page<EmployeeSectorDTO> result = service.EmployeesBySector(1L, "João", pageable);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void employeesBySectorShouldReturnPageWhenSectorIdIsZero() {
        Pageable pageable = PageRequest.of(0, 10);
        String name = "João";
        Long sectorId = 0L;

        Employee employee = EmployeeFactory.createEmployee(); // já tem setor, mas vai ignorar
        Page<Employee> page = new PageImpl<>(List.of(employee));

        when(employeeRepository.searchBySector(null, name, pageable)).thenReturn(page);

        Page<EmployeeSectorDTO> result = service.EmployeesBySector(sectorId, name, pageable);

        assertNotNull(result);
        verify(employeeRepository).searchBySector(null, name, pageable);
    }
    
    @Test
    void insertShouldThrowResourceNotFoundWhenSectorIdDoesNotExist() {
        // Arrange
        EmployeeDTO dto = EmployeeFactory.createEmployeeDTO();
        dto.setSectorId(999L); // ID que não existe no banco
        
        when(sectorRepository.findById(999L)).thenReturn(Optional.empty()); // Simula ausência do setor

        // Act + Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            service.insert(dto);
        });

        verify(sectorRepository).findById(999L);
        verify(employeeRepository, never()).save(any());
    }

    
    
    
    // ✅ copyDtoToEntity setor nulo
    @Test
    void copyDtoToEntityShouldSetNullSectorIfIdNull() {
        Employee entity = new Employee();
        dto.setSectorId(null); // simula DTO sem setor

        service.copyDtoToEntity(dto, entity);

        assertNull(entity.getSector());
        assertEquals(dto.getCpf(), entity.getCpf());
    }

    // ✅ copyDtoToEntity com setor
    @Test
    void copyDtoToEntityShouldSetSectorIfExists() {
        Employee entity = new Employee();
        when(sectorRepository.findById(1L)).thenReturn(Optional.of(sector));

        service.copyDtoToEntity(dto, entity);

        assertNotNull(entity.getSector());
        assertEquals("Produção", entity.getSector().getNameSector());
    }
}
