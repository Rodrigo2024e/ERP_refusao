package com.smartprocessrefusao.erprefusao.services;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.smartprocessrefusao.erprefusao.dto.AddressDTO;
import com.smartprocessrefusao.erprefusao.entities.Address;
import com.smartprocessrefusao.erprefusao.entities.City;
import com.smartprocessrefusao.erprefusao.entities.People;
import com.smartprocessrefusao.erprefusao.projections.AddressReportProjection;
import com.smartprocessrefusao.erprefusao.repositories.AddressRepository;
import com.smartprocessrefusao.erprefusao.repositories.CityRepository;
import com.smartprocessrefusao.erprefusao.repositories.EmployeeRepository;
import com.smartprocessrefusao.erprefusao.repositories.PeopleRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.AddressFactory;
import com.smartprocessrefusao.erprefusao.tests.CityFactory;
import com.smartprocessrefusao.erprefusao.tests.PeopleFactory;

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
	    
	    @Mock
	    private EmployeeRepository employeeRepository;
	    

	    private Address address;
	    private AddressDTO addressDTO;
	    private City city;
	    private People people;
	

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	        address = AddressFactory.createAddress();
	        addressDTO = AddressFactory.createAddressDTO();
	        city = CityFactory.createCity();
	        people = PeopleFactory.createPeople();
	    }
	     
	    //1 - Pagead Address
	    @Test
	    void searchAddressesShouldReturnPageOfAddressDTO() {
	        Pageable pageable = AddressFactory.createPageable();
	        AddressReportProjection projection = mock(AddressReportProjection.class);
	        when(projection.getStreet()).thenReturn("Rua A");

	        Page<AddressReportProjection> page = new PageImpl<>(List.of(projection));
	        when(addressRepository.searchAddressesByCityNameOrId(anyString(), anyLong(), any()))
	                .thenReturn(page);

	        Page<AddressDTO> result = service.searchAddresses("São Paulo", 1L, pageable);

	        assertNotNull(result);
	        assertEquals(1, result.getContent().size());
	    }
	    
	  //2 - Insert Product
		@Test
		void insertShouldSaveAddressAndReturnDTO() {
			when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
			when(peopleRepository.findById(1L)).thenReturn(Optional.of(people));
			when(addressRepository.save(Mockito.any())).thenReturn(address);

			AddressDTO result = service.insert(addressDTO);

			Assertions.assertNotNull(result);
			Assertions.assertEquals("RUA A", result.getStreet());
		}

		//3 - Insert City Invalid 
		  @Test
		    void copyDtoToEntityShoulNotFoundExceptionWhenCityDTOInvalid() {
			  	 when(cityRepository.findById(1L)).thenReturn(Optional.empty());

				 Assertions.assertThrows(ResourceNotFoundException.class, () -> {
				        service.insert(addressDTO);
				    });
		    }

		//4 - Insert People Invalid 
		  @Test
		    void copyDtoToEntityShoulNotFoundExceptionWhenTaxClassificationDTOInvalid() {
			  	when(cityRepository.findById(Mockito.any())).thenReturn(Optional.of(city));
			  	 when(peopleRepository.findById(1L)).thenReturn(Optional.empty());
		
				 Assertions.assertThrows(ResourceNotFoundException.class, () -> {
				        service.insert(addressDTO);
				    });
		    }
		  		
		//5 - Update Address
		@Test
		void updateShouldUpdateAddressDTOWhenIdExists() {
		    Long id = 1L;
		    Address address = AddressFactory.createAddress();
		    AddressDTO dto = AddressFactory.createAddressDTO();
		    City city = address.getCity();
		    People people = address.getPeople();

		    when(addressRepository.getReferenceById(id)).thenReturn(address);
		    when(cityRepository.findById(dto.getCityId())).thenReturn(Optional.of(city));
		    when(peopleRepository.findById(dto.getPeopleId())).thenReturn(Optional.of(people));
		    when(addressRepository.save(Mockito.any())).thenReturn(address);

		    AddressDTO result = service.update(id, dto);

		    Assertions.assertNotNull(result);
		    Assertions.assertEquals(dto.getStreet(), result.getStreet());
		}

		//6 - Update Address Invalid		    
	    @Test
	    void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
	        when(addressRepository.getReferenceById(999L)).thenThrow(EntityNotFoundException.class);

	        assertThrows(ResourceNotFoundException.class, () -> {
	            service.update(999L, addressDTO);
	        });
	    }

	  //7 - Delete Id exists
	    @Test
	    void deleteShouldDoNothingWhenIdExists() {
	        when(addressRepository.existsById(1L)).thenReturn(true);
	        doNothing().when(addressRepository).deleteById(1L);

	        assertDoesNotThrow(() -> service.delete(1L));
	    }

	  //8 - Delete Id Does Not exists
	    @Test
	    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
	        when(addressRepository.existsById(1L)).thenReturn(false);

	        assertThrows(ResourceNotFoundException.class, () -> {
	            service.delete(1L);
	        });
	    }

	  //9 - Delete Id Dependent
	    @Test
	    void deleteShouldThrowDatabaseExceptionWhenIntegrityViolationOccurs() {
	        when(addressRepository.existsById(1L)).thenReturn(true);
	        doThrow(DataIntegrityViolationException.class).when(addressRepository).deleteById(1L);

	        assertThrows(DatabaseException.class, () -> {
	            service.delete(1L);
	        });
	    }

	 
	}
	
	