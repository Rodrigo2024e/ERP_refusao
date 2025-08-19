package com.smartprocessrefusao.erprefusao.services;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.smartprocessrefusao.erprefusao.dto.SectorDTO;
import com.smartprocessrefusao.erprefusao.entities.Sector;
import com.smartprocessrefusao.erprefusao.repositories.SectorRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.SectorFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class SectorServiceTest {

	@InjectMocks
	private SectorService service;

	@Mock
	private SectorRepository sectorRepository;

	private Sector sector;
	private SectorDTO sectorDTO;

	@BeforeEach
	void setUp() {
		sector = SectorFactory.createSector();
		sectorDTO = SectorFactory.createSectorDTO();
	}

	// 1 - FindAll Sector
	@Test
	void findAllShouldReturnSortedList() {
		List<Sector> list = List.of(sector);
		when(sectorRepository.findAllByOrderByNameSectorAsc()).thenReturn(list);

		List<SectorDTO> result = service.findAll();

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals("PRODUÇÃO", result.get(0).getNameSector());
	}

	// 2 - FindById
	@Test
	void findByIdShouldReturnSectorDTOWhenIdExists() {
		when(sectorRepository.findById(1L)).thenReturn(Optional.of(sector));

		SectorDTO result = service.findById(1L);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("PRODUÇÃO", result.getNameSector());
	}

	// 3 - FindById-EntityNotFoundException
	@Test
	void findByIdShouldThrowResourceNotFoundWhenIdDoesNotExist() {
		when(sectorRepository.findById(99L)).thenReturn(Optional.empty());

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(99L);
		});
	}

	// 4 - Insert Sector
	@Test
	void insertShouldSaveSectorAndReturnDTO() {
		when(sectorRepository.save(Mockito.any())).thenReturn(sector);

		SectorDTO result = service.insert(sectorDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("PRODUÇÃO", result.getNameSector());
	}

	// 5 - Update Product
	@Test
	void updateShouldUpdateWhenIdExists() {
		when(sectorRepository.getReferenceById(1L)).thenReturn(sector);
		when(sectorRepository.save(sector)).thenReturn(sector);

		SectorDTO result = service.update(1L, sectorDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("PRODUÇÃO", result.getNameSector());
	}

	// 6 - Update Product Invalid
	@Test
	void updateShouldThrowResourceNotFoundWhenIdDoesNotExist() {
		when(sectorRepository.getReferenceById(99L)).thenThrow(EntityNotFoundException.class);

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.update(99L, sectorDTO);
		});
	}

	// 7 - Delete Id exists
	@Test
	void deleteShouldDoNothingWhenIdExists() {
		when(sectorRepository.existsById(1L)).thenReturn(true);
		doNothing().when(sectorRepository).deleteById(1L);

		Assertions.assertDoesNotThrow(() -> service.delete(1L));
	}

	// 8 - Delete Id Does Not exists
	@Test
	void deleteShouldThrowResourceNotFoundWhenIdDoesNotExist() {
		when(sectorRepository.existsById(99L)).thenReturn(false);

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(99L);
		});
	}

	// 9 - Delete Id Dependent
	@Test
	void deleteShouldThrowDatabaseExceptionOnIntegrityViolation() {
		when(sectorRepository.existsById(1L)).thenReturn(true);
		doThrow(DataIntegrityViolationException.class).when(sectorRepository).deleteById(1L);

		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(1L);
		});
	}

}
