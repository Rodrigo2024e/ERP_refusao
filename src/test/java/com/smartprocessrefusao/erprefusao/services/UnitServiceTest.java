package com.smartprocessrefusao.erprefusao.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.smartprocessrefusao.erprefusao.dto.UnitDTO;
import com.smartprocessrefusao.erprefusao.entities.Unit;
import com.smartprocessrefusao.erprefusao.repositories.UnitRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.UnitFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class UnitServiceTest {

	@InjectMocks
	private UnitService service;
	
	@Mock
	private UnitRepository repository;
	
	private Unit unit;
	private UnitDTO unitDTO;
	private Long existingId;
	private Long nonExistingId;
	
	@BeforeEach
	void setUp() {
		existingId = 1L;
		nonExistingId = 999L;
				
		unit =  UnitFactory.createUnit(existingId, "Kilograma", "kg");
		unitDTO = new UnitDTO(unit);
	} 
	//1
	@Test
	public void findAllShouldReturnListUnitDTO() {
		
		when(repository.findAll()).thenReturn(List.of(unit));
		
		List<UnitDTO> result = service.findAll();
		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
		assertEquals("Kilograma", result.get(0).getDescription());
		
	}
	//2
	@Test
	public void findByIdShouldReturnUnitDTOWhenIdExists() {
		
		when(repository.findById(existingId)).thenReturn(Optional.of(unit));
		
		UnitDTO result = service.findById(existingId);
		assertNotNull(result);
		assertEquals("Kilograma", result.getDescription());
	}
	//3
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		assertThrows(ResourceNotFoundException.class, () -> service.findById(nonExistingId));
	}
	//4
	@Test
	public void insertShouldReturnUnitDTO() {
		
		when(repository.save(any())).thenReturn(unit);
		
		UnitDTO result = service.insert(unitDTO);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), unit.getId());
	}
	//5
	@Test
	public void updateShouldReturnUnitDTOWhenIdExists() {
		  when(repository.getReferenceById(1L)).thenReturn(unit);
	      when(repository.save(unit)).thenReturn(unit);

	      UnitDTO result = service.update(1L, unitDTO);

	        Assertions.assertNotNull(result);
	        Assertions.assertEquals("KILOGRAMA", result.getDescription());
	}
	//6
	@Test
	public void updateShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
		
		assertThrows(ResourceNotFoundException.class, () -> service.update(nonExistingId, unitDTO));
	}
	//7
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		
		when(repository.existsById(existingId)).thenReturn(true);
		
		assertDoesNotThrow(() -> service.delete(existingId));
		verify(repository).deleteById(existingId);
	}
	//8
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		when(repository.existsById(nonExistingId)).thenReturn(false);
		
		assertThrows(ResourceNotFoundException.class, () -> service.delete(nonExistingId));
	}
	
	//9
	@Test
	public void deleteShouldThrowDataBaseExceptionWhenIdDependent() {
		
		when(repository.existsById(existingId)).thenReturn(true);
		
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(existingId);
		assertThrows(DatabaseException.class, () -> service.delete(existingId));
	}
	
}
