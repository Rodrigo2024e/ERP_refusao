package com.smartprocessrefusao.erprefusao.services;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.smartprocessrefusao.erprefusao.dto.AddressDTO;
import com.smartprocessrefusao.erprefusao.entities.Address;
import com.smartprocessrefusao.erprefusao.entities.City;
import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.projections.AddressProjection;
import com.smartprocessrefusao.erprefusao.repositories.AddressRepository;
import com.smartprocessrefusao.erprefusao.repositories.CityRepository;
import com.smartprocessrefusao.erprefusao.repositories.PeopleRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.AddressFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @InjectMocks
    private AddressService service;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private PeopleRepository peopleRepository;

    private AddressDTO dto;
    private Address entity;
    private City city;
    private Employee employee;

    @BeforeEach
    void setup() {
        dto = AddressFactory.createAddressDTO();
        entity = AddressFactory.createAddress();
        city = AddressFactory.createCity();
        employee = AddressFactory.createPeople();
    }

    @Test
    void insertShouldReturnAddressDTOWhenSuccess() {
        when(cityRepository.findById(dto.getCityId())).thenReturn(Optional.of(city));
        when(peopleRepository.findById(dto.getPeople_id())).thenReturn(Optional.of(employee));
        when(addressRepository.save(any())).thenReturn(entity);

        AddressDTO result = service.insert(dto);

        assertNotNull(result);
        verify(addressRepository).save(any());
    }

    @Test
    void insertShouldThrowResourceNotFoundExceptionWhenCityNotFound() {
        when(cityRepository.findById(dto.getCityId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.insert(dto));
    }

    @Test
    void insertShouldThrowResourceNotFoundExceptionWhenPeopleNotFound() {
        when(cityRepository.findById(dto.getCityId())).thenReturn(Optional.of(city));
        when(peopleRepository.findById(dto.getPeople_id())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.insert(dto));
    }

    @Test
    void updateShouldReturnAddressDTOWhenSuccess() {
        when(addressRepository.getReferenceById(1L)).thenReturn(entity);
        when(cityRepository.findById(dto.getCityId())).thenReturn(Optional.of(city));
        when(peopleRepository.findById(dto.getPeople_id())).thenReturn(Optional.of(employee));
        when(addressRepository.save(any())).thenReturn(entity);

        AddressDTO result = service.update(1L, dto);

        assertNotNull(result);
        verify(addressRepository).save(any());
    }

    @Test
    void updateShouldThrowResourceNotFoundExceptionWhenEntityNotFound() {
        when(addressRepository.getReferenceById(1L)).thenThrow(EntityNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> service.update(1L, dto));
    }

    @Test
    void insertShouldSkipCityWhenCityIdIsNull() {
        AddressDTO dtoWithoutCity = AddressFactory.createAddressDTO();
        dtoWithoutCity.setCityId(null); // cityId nulo
        dtoWithoutCity.setPeople_id(10L);

        when(peopleRepository.findById(10L)).thenReturn(Optional.of(AddressFactory.createPeople()));
        when(addressRepository.save(any())).thenReturn(AddressFactory.createAddress());

        AddressDTO result = service.insert(dtoWithoutCity);

        assertNotNull(result);
        verify(cityRepository, never()).findById(any()); // cityRepository NÃO deve ser chamado
        verify(peopleRepository).findById(10L);
    }

    @Test
    void insertShouldSkipPeopleWhenPeopleIdIsNull() {
        AddressDTO dtoWithoutPeople = AddressFactory.createAddressDTO();
        dtoWithoutPeople.setPeople_id(null); // people_id nulo
        dtoWithoutPeople.setCityId(1L);

        when(cityRepository.findById(1L)).thenReturn(Optional.of(AddressFactory.createCity()));
        when(addressRepository.save(any())).thenReturn(AddressFactory.createAddress());

        AddressDTO result = service.insert(dtoWithoutPeople);

        assertNotNull(result);
        verify(peopleRepository, never()).findById(any()); // peopleRepository NÃO deve ser chamado
        verify(cityRepository).findById(1L);
    }
    
    
    @Test
    void deleteShouldDoNothingWhenIdExists() {
        when(addressRepository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(addressRepository).deleteById(1L);
    }

    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenIdNotExists() {
        when(addressRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.delete(1L));
    }

    @Test
    void deleteShouldThrowDatabaseExceptionWhenIntegrityViolation() {
        when(addressRepository.existsById(1L)).thenReturn(true);
        doThrow(DataIntegrityViolationException.class).when(addressRepository).deleteById(1L);

        assertThrows(DatabaseException.class, () -> service.delete(1L));
    }

    @Test
    void findAllShouldReturnPagedDTOList() {
        AddressProjection projection = mock(AddressProjection.class);
        Page<AddressProjection> page = new PageImpl<>(List.of(projection));

        when(addressRepository.findAllProjections(any())).thenReturn(page);

        Pageable pageable = PageRequest.of(0, 10);
        Page<AddressDTO> result = service.findAll(pageable);

        assertNotNull(result);
        verify(addressRepository).findAllProjections(pageable);
    }

    @Test
    void searchAddressesShouldReturnPagedDTOList() {
        AddressProjection projection = mock(AddressProjection.class);
        Page<AddressProjection> page = new PageImpl<>(List.of(projection));

        when(addressRepository.searchAddressesByCityNameOrId(anyString(), anyLong(), any())).thenReturn(page);

        Pageable pageable = PageRequest.of(0, 10);
        Page<AddressDTO> result = service.searchAddresses("São Paulo", 100L, pageable);

        assertNotNull(result);
        verify(addressRepository).searchAddressesByCityNameOrId("São Paulo", 100L, pageable);
    }
}
