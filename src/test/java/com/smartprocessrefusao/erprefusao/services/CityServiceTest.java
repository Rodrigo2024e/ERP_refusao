package com.smartprocessrefusao.erprefusao.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.smartprocessrefusao.erprefusao.dto.CityDTO;
import com.smartprocessrefusao.erprefusao.entities.City;
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

	private City city;
	private CityDTO cityDTO;

	@BeforeEach
	void setUp() {
		city = CityFactory.createCity();
		cityDTO = CityFactory.createCityDTO();
	}

	// 1 - FindAll City
	@Test
	void findAllShouldReturnListOfDTOs() {
		List<City> cities = List.of(CityFactory.createCity(), CityFactory.createUpdatedCity());
		when(cityRepository.findAllByOrderByNameCityAsc()).thenReturn(cities);

		List<CityDTO> result = service.findAll();

		assertEquals(2, result.size());
		assertEquals("São Paulo", result.get(0).getNameCity());
	}

	// 2 - FindById
	@Test
	void findByIdShouldReturnDTOWhenIdExists() {
		when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

		CityDTO result = service.findById(1L);

		assertEquals("São Paulo", result.getNameCity());
		assertEquals("SP", result.getUfState());
	}

	// 3 - FindById-EntityNotFoundException
	@Test
	void findByIdShouldThrowWhenIdDoesNotExist() {
		when(cityRepository.findById(999L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> service.findById(999L));
	}

	// 4 - Insert City
	@Test
	void insertShouldSaveAndReturnDTO() {
		when(cityRepository.save(any())).thenReturn(city);

		CityDTO result = service.insert(cityDTO);

		assertEquals(cityDTO.getNameCity(), result.getNameCity());
		assertEquals(cityDTO.getUfState(), result.getUfState());
	}

	//5 - Insert Uf Invalid 
	@Test
	void insertShouldThrowWhenUfIsInvalid() {
		CityDTO dto = CityFactory.createInvalidUfDTO();

		assertThrows(ResourceNotFoundException.class, () -> {
			service.insert(dto);
		});
	}

	//6 - Update City
	@Test
	void updateShouldSaveAndReturnDTOWhenIdExists() {

		when(cityRepository.getReferenceById(1L)).thenReturn(city);
		when(cityRepository.save(city)).thenReturn(city);

		CityDTO result = service.update(1L, cityDTO);

		assertEquals(cityDTO.getNameCity(), result.getNameCity());
		assertEquals(cityDTO.getUfState(), result.getUfState());
	}

	//7 - Update City Invalid
	@Test
	void updateShouldThrowWhenIdDoesNotExist() {
		CityDTO dto = CityFactory.createUpdatedCityDTO();
		when(cityRepository.getReferenceById(999L)).thenThrow(EntityNotFoundException.class);

		assertThrows(ResourceNotFoundException.class, () -> service.update(999L, dto));
	}

	//8 - Delete Id exists
	@Test
	void deleteShouldDoNothingWhenIdExists() {
		when(cityRepository.existsById(1L)).thenReturn(true);
		doNothing().when(cityRepository).deleteById(1L);

		assertDoesNotThrow(() -> service.delete(1L));
	}

	//9 - Delete Id Does Not exists
	@Test
	void deleteShouldThrowWhenIdDoesNotExist() {
		when(cityRepository.existsById(999L)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> service.delete(999L));
	}

	//10 - Delete Id Dependent
	@Test
	void deleteShouldThrowDatabaseExceptionOnIntegrityViolation() {
		when(cityRepository.existsById(1L)).thenReturn(true);
		doThrow(DataIntegrityViolationException.class).when(cityRepository).deleteById(1L);

		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(1L);
		});
	}

}
