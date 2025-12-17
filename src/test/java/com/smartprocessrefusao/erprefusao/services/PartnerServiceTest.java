package com.smartprocessrefusao.erprefusao.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.smartprocessrefusao.erprefusao.dto.PartnerDTO;
import com.smartprocessrefusao.erprefusao.dto.PartnerReportDTO;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.projections.PartnerReportProjection;
import com.smartprocessrefusao.erprefusao.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.PartnerFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
class PartnerServiceTest {

	@InjectMocks
	private PartnerService service;

	@Mock
	private PartnerRepository repository;

	private Partner partner;
	private PartnerDTO dto;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		partner = PartnerFactory.createPartner();
		dto = PartnerFactory.createPartnerDTO();
	}

	// 1 - FindAll City
	@Test
	void findAllShouldReturnListOfDTOs() {
		List<Partner> partnes = List.of(PartnerFactory.createPartner(), PartnerFactory.createPartner());
		when(repository.findAll()).thenReturn(partnes);

		List<PartnerDTO> result = service.findAll();

		assertEquals(2, result.size());
		assertEquals("ECOALUMI ALUMINIO S/A", result.get(0).getName());
	}

	// 2 - Report Partner
	@Test
	void reportPartnerShouldReturnPagedDTOs() {
		PartnerReportProjection projection = mock(PartnerReportProjection.class);
		when(projection.getName()).thenReturn("JOÃO CARLOS");

		Page<PartnerReportProjection> page = new PageImpl<>(List.of(projection));
		when(repository.searchPeopleNameByOrId(any(), any(), any())).thenReturn(page);

		Pageable pageable = PageRequest.of(0, 10);
		Page<PartnerReportDTO> result = service.reportPartner("JOÃO", 1L, pageable);

		assertNotNull(result);
		assertEquals(1, result.getTotalElements());
		assertEquals("JOÃO CARLOS", result.getContent().get(0).getName());
		verify(repository).searchPeopleNameByOrId("JOÃO", 1L, pageable);
	}

	// 3 - FindById
	@Test
	void findByIdShouldReturnDTOWhenIdExists() {
		when(repository.findById(4L)).thenReturn(Optional.of(partner));

		PartnerDTO result = service.findById(4L);

		assertEquals("ECOALUMI ALUMINIO S/A", result.getName());

	}
	
	// 4 - FindById-EntityNotFoundException
		@Test
		void findByIdShouldThrowWhenIdDoesNotExist() {
			when(repository.findById(999L)).thenReturn(Optional.empty());

			assertThrows(ResourceNotFoundException.class, () -> service.findById(999L));
		}

	// 5 - Insert Partner
	@Test
	void insertShouldSavePartner() {
		when(repository.save(any())).thenReturn(partner);

		PartnerDTO result = service.insert(dto);

		assertNotNull(result);
		assertEquals(dto.getName(), result.getName());
		verify(repository).save(any());
	}

	// 6 - Update Partner
	@Test
	void updateShouldReturnUpdatedDTOWhenIdExists() {
		when(repository.getReferenceById(1L)).thenReturn(partner);
		when(repository.save(any())).thenReturn(partner);

		PartnerDTO result = service.update(1L, dto);

		assertNotNull(result);
		assertEquals(dto.getEmail(), result.getEmail());
		verify(repository).getReferenceById(1L);
		verify(repository).save(any());
	}

	// 7 - Update Partner Invalid
	@Test
	void updateShouldThrowResourceNotFoundWhenIdNotExists() {
		when(repository.getReferenceById(99L)).thenThrow(EntityNotFoundException.class);

		assertThrows(ResourceNotFoundException.class, () -> service.update(99L, dto));
		verify(repository).getReferenceById(99L);
	}

	// 8 - Delete Id exists
	@Test
	void deleteShouldDoNothingWhenIdExists() {
		when(repository.existsById(1L)).thenReturn(true);
		doNothing().when(repository).deleteById(1L);

		assertDoesNotThrow(() -> service.delete(1L));
		verify(repository).deleteById(1L);
	}

	// 9 - Delete Id Does Not exists
	@Test
	void deleteShouldThrowResourceNotFoundWhenIdDoesNotExist() {
		when(repository.existsById(99L)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> service.delete(99L));
		verify(repository, never()).deleteById(any());
	}

	// 10 - Delete Id Dependent
	@Test
	void deleteShouldThrowDatabaseExceptionWhenIntegrityViolation() {
		when(repository.existsById(1L)).thenReturn(true);
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(1L);

		assertThrows(DatabaseException.class, () -> service.delete(1L));
		verify(repository).deleteById(1L);
	}

}
