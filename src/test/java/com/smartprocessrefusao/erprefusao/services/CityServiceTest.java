package com.smartprocessrefusao.erprefusao.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.smartprocessrefusao.erprefusao.dto.CityDTO;
import com.smartprocessrefusao.erprefusao.entities.City;
import com.smartprocessrefusao.erprefusao.enumerados.StateBrazil;
import com.smartprocessrefusao.erprefusao.repositories.CityRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.CityFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class CityServiceTest {
	
	@InjectMocks
	private CityService service;

	@Mock
	private CityRepository cityRepository;

	private Long existingId;
	private Long nonExistingId;
	private Long dependentId; // For DataIntegrityViolationException
	private City cityA; // For ordered list test
	private City cityB; // For ordered list test
	private CityDTO cityADTO; // For ordered list test
	private CityDTO cityBDTO; // For ordered list test
	private City city;
	private CityDTO cityDTO;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 2L;
		dependentId = 3L;

		// Setup for findAllByOrderByNameCityAsc
		cityA = CityFactory.createCity(10L, "Aracaju", StateBrazil.SE);
		cityB = CityFactory.createCity(11L, "Belém", StateBrazil.PA);
		cityADTO = new CityDTO(cityA);
		cityBDTO = new CityDTO(cityB);

		// Setup for general CRUD operations
		city = CityFactory.createCity(existingId, "São Paulo", StateBrazil.SP); // Ensure ID matches existingId
		cityDTO = new CityDTO(city); // Create DTO from the entity

		// Mock findAllByOrderByNameCityAsc
		Mockito.when(cityRepository.findAllByOrderByNameCityAsc()).thenReturn(Arrays.asList(cityA, cityB));

		// Mock findById
		Mockito.when(cityRepository.findById(existingId)).thenReturn(Optional.of(city));
		Mockito.when(cityRepository.findById(nonExistingId)).thenReturn(Optional.empty());

		// Mock save
		Mockito.when(cityRepository.save(any(City.class))).thenReturn(city);

		// Mock getReferenceById
		Mockito.when(cityRepository.getReferenceById(existingId)).thenReturn(city);
		Mockito.doThrow(EntityNotFoundException.class).when(cityRepository).getReferenceById(nonExistingId);

		// Mock existsById for delete
		Mockito.when(cityRepository.existsById(existingId)).thenReturn(true);
		Mockito.when(cityRepository.existsById(nonExistingId)).thenReturn(false);
		Mockito.when(cityRepository.existsById(dependentId)).thenReturn(true);

		// Mock deleteById
		Mockito.doNothing().when(cityRepository).deleteById(existingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(cityRepository).deleteById(dependentId);
	}

	// -----------------------------------------------------------
	// findAll() tests
	// -----------------------------------------------------------

	@Test
	public void findAllShouldReturnListOfCityDTO() {
		List<CityDTO> result = service.findAll();

		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(2, result.size());
		verify(cityRepository, times(1)).findAllByOrderByNameCityAsc();
	}

	@Test
	public void findAllShouldReturnEmptyListWhenNoCities() {
		when(cityRepository.findAllByOrderByNameCityAsc()).thenReturn(Arrays.asList());

		List<CityDTO> result = service.findAll();

		Assertions.assertNotNull(result);
		Assertions.assertTrue(result.isEmpty());
		Assertions.assertEquals(0, result.size());
		verify(cityRepository, times(1)).findAllByOrderByNameCityAsc();
	}

	@Test
	public void findAllShouldReturnOrderedListByNameCityAsc() {
		List<CityDTO> result = service.findAll();

		Assertions.assertNotNull(result);
		Assertions.assertEquals(2, result.size());
		Assertions.assertEquals(cityADTO.getNameCity(), result.get(0).getNameCity()); // Aracaju comes first
		Assertions.assertEquals(cityBDTO.getNameCity(), result.get(1).getNameCity()); // Belém comes second
	}

	// -----------------------------------------------------------
	// findById(Long id) tests
	// -----------------------------------------------------------

	@Test
	public void findByIdShouldReturnCityDTOWhenIdExists() {
		CityDTO result = service.findById(existingId);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(existingId, result.getId());
		Assertions.assertEquals(city.getNameCity(), result.getNameCity());
		Assertions.assertEquals(city.getUfState().name(), result.getUfState());
		verify(cityRepository, times(1)).findById(existingId);
	}

	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});
		verify(cityRepository, times(1)).findById(nonExistingId);
	}

	// -----------------------------------------------------------
	// insert(CityDTO dto) tests
	// -----------------------------------------------------------

	@Test
	public void insertShouldReturnCityDTOWhenSuccessful() {
		// Prepare a DTO for insertion (ID should be null or not set)
		CityDTO newCityDTO = CityFactory.createCityDTO(null, "Manaus", StateBrazil.AM.name());

		// Mock the save operation. The service creates a new entity and saves it.
		// We need to ensure the saved entity has an ID assigned by the DB, so we return a new City instance with ID.
		City savedCity = CityFactory.createCity(100L, newCityDTO.getNameCity(), StateBrazil.AM);
		when(cityRepository.save(any(City.class))).thenReturn(savedCity);

		CityDTO result = service.insert(newCityDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(savedCity.getId(), result.getId()); // Check if ID is assigned
		Assertions.assertEquals(newCityDTO.getNameCity(), result.getNameCity());
		Assertions.assertEquals(newCityDTO.getUfState(), result.getUfState());
		verify(cityRepository, times(1)).save(any(City.class));
	}

	@Test
	public void insertShouldThrowResourceNotFoundExceptionWhenInvalidUf() {
		CityDTO invalidUfDTO = CityFactory.createCityDTO(null, "Invalid City", "XX"); // Invalid UF

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.insert(invalidUfDTO);
		});
		verify(cityRepository, never()).save(any(City.class)); // Should not save
	}

	@Test
	public void insertShouldThrowResourceNotFoundExceptionWhenEmptyUf() {
		CityDTO emptyUfDTO = CityFactory.createCityDTO(null, "Empty UF City", ""); // Empty UF

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.insert(emptyUfDTO);
		});
		verify(cityRepository, never()).save(any(City.class)); // Should not save
	}

	// -----------------------------------------------------------
	// update(Long id, CityDTO dto) tests
	// -----------------------------------------------------------

	@Test
	public void updateShouldReturnCityDTOWhenIdExists() {
		CityDTO updatedCityDTO = CityFactory.createCityDTO(existingId, "Campinas Atualizada", StateBrazil.SP.name());
		
		// Ensure getReferenceById returns the entity to be updated
		when(cityRepository.getReferenceById(existingId)).thenReturn(city); // 'city' is the original entity
		// Ensure save returns the updated entity
		when(cityRepository.save(any(City.class))).thenReturn(city); // 'city' is now updated by copyDtoToEntity

		CityDTO result = service.update(existingId, updatedCityDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(existingId, result.getId());
		Assertions.assertEquals(updatedCityDTO.getNameCity(), result.getNameCity());
		Assertions.assertEquals(updatedCityDTO.getUfState(), result.getUfState());
		verify(cityRepository, times(1)).getReferenceById(existingId);
		verify(cityRepository, times(1)).save(any(City.class));
	}

	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		CityDTO dummyDTO = CityFactory.createCityDTO(nonExistingId, "Dummy", StateBrazil.RJ.name());

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.update(nonExistingId, dummyDTO);
		});
		verify(cityRepository, times(1)).getReferenceById(nonExistingId);
		verify(cityRepository, never()).save(any(City.class));
	}

	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenInvalidUf() {
		CityDTO invalidUfDTO = CityFactory.createCityDTO(existingId, "Updated City", "YY"); // Invalid UF

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.update(existingId, invalidUfDTO);
		});
		verify(cityRepository, times(1)).getReferenceById(existingId); // getReferenceById is called before UF check
		verify(cityRepository, never()).save(any(City.class)); // Should not save
	}

	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenEmptyUf() {
		CityDTO emptyUfDTO = CityFactory.createCityDTO(existingId, "Updated City", ""); // Empty UF

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.update(existingId, emptyUfDTO);
		});
		verify(cityRepository, times(1)).getReferenceById(existingId); // getReferenceById is called before UF check
		verify(cityRepository, never()).save(any(City.class)); // Should not save
	}

	
	// -----------------------------------------------------------
	// delete(Long id) tests
	// -----------------------------------------------------------

	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
		verify(cityRepository, times(1)).existsById(existingId);
		verify(cityRepository, times(1)).deleteById(existingId);
	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
		verify(cityRepository, times(1)).existsById(nonExistingId);
		verify(cityRepository, never()).deleteById(anyLong()); // deleteById should not be called
	}

	@Test
	public void deleteShouldThrowDatabaseExceptionWhenIntegrityViolation() {
		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(dependentId);
		});
		verify(cityRepository, times(1)).existsById(dependentId); // existsById is checked first
		verify(cityRepository, times(1)).deleteById(dependentId); // Then deleteById is called
	}

}
