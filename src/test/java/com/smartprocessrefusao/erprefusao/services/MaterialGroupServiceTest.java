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

import com.smartprocessrefusao.erprefusao.dto.MaterialGroupDTO;
import com.smartprocessrefusao.erprefusao.entities.MaterialGroup;
import com.smartprocessrefusao.erprefusao.repositories.MaterialGroupRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.MatGroupFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class MaterialGroupServiceTest {

	@InjectMocks
	private MaterialGroupService service;

	@Mock
	private MaterialGroupRepository repository;

	private MaterialGroup group;
	private MaterialGroupDTO dto;
	private Long existingId;
	private Long nonExistingId;

	@BeforeEach
	void setUp() {
		existingId = 1L;
		nonExistingId = 999L;

		group = MatGroupFactory.createGroup();
		dto = new MaterialGroupDTO(group);
	}

	// 1 - FindAll ProductGroup
	@Test
	public void findAllShouldReturnListProductGroupDTO() {

		when(repository.findAll()).thenReturn(List.of(group));

		List<MaterialGroupDTO> result = service.findAll();
		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
		assertEquals("PRODUTO ACABADO", result.get(0).getDescription());
	}

	// 2 - FindById
	@Test
	public void findByIdShouldReturnProductGroupDTOWhenIdExists() {

		when(repository.findById(existingId)).thenReturn(Optional.of(group));

		MaterialGroupDTO result = service.findById(existingId);
		assertNotNull(result);
		assertEquals("PRODUTO ACABADO", result.getDescription());

	}

	// 3 - FindById-EntityNotFoundException
	@Test
	public void findByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExists() {

		assertThrows(ResourceNotFoundException.class, () -> service.findById(nonExistingId));
	}

	// 4 - Insert Product
	@Test
	public void insertShouldReturnProductGroupDTO() {

		when(repository.save(any())).thenReturn(group);

		MaterialGroupDTO result = service.insert(dto);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), group.getId());
	}

	// 5 - Update ProductGroup
	@Test
	public void updateShouldReturnProductGroupDTOWhenIdExists() {

		when(repository.getReferenceById(1L)).thenReturn(group);
		when(repository.save(group)).thenReturn(group);

		MaterialGroupDTO result = service.update(1L, dto);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("PRODUTO ACABADO", result.getDescription());
	}

	// 6 - Update ProductGroup Invalid
	@Test
	public void updateShouldReturnNotFoundExceptionWhenIdDoesNotExists() {

		when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

		assertThrows(ResourceNotFoundException.class, () -> service.update(nonExistingId, dto));

	}

	// 7 - Delete Id exists
	@Test
	public void deleteShouldDoNothingWhenIdExists() {

		when(repository.existsById(existingId)).thenReturn(true);

		assertDoesNotThrow(() -> service.delete(existingId));
		verify(repository).deleteById(existingId);
	}

	// 8 - Delete Id Does Not exists
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {

		when(repository.existsById(nonExistingId)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> service.delete(nonExistingId));
	}

	// 9 - Delete Id Dependent
	@Test
	public void deleteShouldThrowDataBaseExceptionWhenIdDependent() {

		when(repository.existsById(existingId)).thenReturn(true);

		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(existingId);
		assertThrows(DatabaseException.class, () -> service.delete(existingId));
	}

}
