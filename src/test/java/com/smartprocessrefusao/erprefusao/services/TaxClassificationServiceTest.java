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

import com.smartprocessrefusao.erprefusao.dto.TaxClassificationDTO;
import com.smartprocessrefusao.erprefusao.entities.TaxClassification;
import com.smartprocessrefusao.erprefusao.repositories.TaxClassificationRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.TaxClassificationFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class TaxClassificationServiceTest {

	@InjectMocks
	private TaxClassificationService service;

	@Mock
	TaxClassificationRepository repository;

	private TaxClassification tax;
	private TaxClassificationDTO taxDTO;
	private Long exisitngId, nonExistingId, dependentId;

	@BeforeEach
	void setUpe() {

		exisitngId = 1L;
		nonExistingId = 999L;
		dependentId = 3L;

//		tax = TaxClassificationFactory.createTaxClass(exisitngId, "Sucata de alumínio", "7602000");
		tax = TaxClassificationFactory.createTaxClass();
		taxDTO = TaxClassificationFactory.createTaxDTO();
	}

	// 1 - FindAll Tax
	@Test
	public void findAllShouldReturnListTaxClassificationDTO() {
		when(repository.findAll()).thenReturn(List.of(tax));
		List<TaxClassificationDTO> result = service.findAll();
		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
		assertEquals("TARUGO DE ALUMÍNIO", result.get(0).getDescription());
	}

	// 2 - FindById
	@Test
	public void findByIdShouldReturnTaxClassificationDTOWhenIsExists() {
		when(repository.findById(exisitngId)).thenReturn(Optional.of(tax));
		TaxClassificationDTO result = service.findById(exisitngId);
		assertNotNull(result);
		assertEquals("TARUGO DE ALUMÍNIO", result.getDescription());
	}

	// 3 - FindById-EntityNotFoundException
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> service.findById(nonExistingId));
	}

	// 4 - Insert Tax
	@Test
	public void insertShouldReturnTaxClassificationDTOWhenCorrect() {
		when(repository.save(any())).thenReturn(tax);
		TaxClassificationDTO result = service.insert(taxDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), tax.getId());

	}

	// 5 - Update Tax
	@Test
	public void updateShouldReturnTaxClassificationDTOWhenIdExists() {
		when(repository.getReferenceById(exisitngId)).thenReturn(tax);
		when(repository.save(tax)).thenReturn(tax);

		TaxClassificationDTO result = service.update(1L, taxDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("TARUGO DE ALUMÍNIO", result.getDescription());
	}

	// 6 - Update tax Invalid
	@Test
	public void updateShouldReturnNotfoundExceptionWhenIdDoesNotExists() {
		when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
		assertThrows(ResourceNotFoundException.class, () -> service.update(nonExistingId, taxDTO));
	}

	// 7 - Delete Id exists
	@Test
	public void deleteShouldReturnDoNothingWhenIdExists() {
		when(repository.existsById(exisitngId)).thenReturn(true);
		assertDoesNotThrow(() -> service.delete(exisitngId));
		verify(repository).deleteById(exisitngId);
	}

	// 8 - Delete Id Does Not exists
	@Test
	public void deleteShouldThrowNotFoundExceptionWhenIdDoesNotExists() {
		when(repository.existsById(nonExistingId)).thenReturn(false);
		assertThrows(ResourceNotFoundException.class, () -> service.delete(nonExistingId));
	}

	// 9 - Delete Id Dependent
	@Test
	public void deleteShouldThrowDataBaseExceptionWhenIntegrityViolationOccurs() {
		when(repository.existsById(dependentId)).thenReturn(true);

		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
		assertThrows(DatabaseException.class, () -> service.delete(dependentId));
	}
}
