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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.smartprocessrefusao.erprefusao.dto.SectorDTO;
import com.smartprocessrefusao.erprefusao.entities.Sector;
import com.smartprocessrefusao.erprefusao.repositories.SectorRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class SectorServiceTest {

	
	    @InjectMocks
	    private SectorService service;

	    @Mock
	    private SectorRepository repository;

	    private Sector sector;
	    private SectorDTO sectorDTO;
	    private Long existingId;
	    private Long nonExistingId;

	    @BeforeEach
	    void setUp() {
	        existingId = 1L;
	        nonExistingId = 999L;

	        sector = new Sector();
	        sector.setId(existingId);
	        sector.setNameSector("Setor A");
	        sector.setProcess("Processo A");

	        sectorDTO = new SectorDTO();
	        sectorDTO.setNameSector("Setor Atualizado");
	        sectorDTO.setProcess("Processo Atualizado");

	        when(repository.findAllByOrderByNameSectorAsc()).thenReturn(List.of(sector));
	        when(repository.findById(existingId)).thenReturn(Optional.of(sector));
	        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
	        when(repository.getReferenceById(existingId)).thenReturn(sector);
	        when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
	        when(repository.save(any())).thenReturn(sector);
	        when(repository.existsById(existingId)).thenReturn(true);
	        when(repository.existsById(nonExistingId)).thenReturn(false);
	    }

	    @Test
	    public void findAllShouldReturnListOfSectorDTO() {
	        List<SectorDTO> result = service.findAll();
	        assertFalse(result.isEmpty());
	        assertEquals(1, result.size());
	        assertEquals("Setor A", result.get(0).getNameSector());
	    }

	    @Test
	    public void findByIdShouldReturnSectorDTOWhenIdExists() {
	        SectorDTO result = service.findById(existingId);
	        assertNotNull(result);
	        assertEquals("Setor A", result.getNameSector());
	    }

	    @Test
	    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
	        assertThrows(ResourceNotFoundException.class, () -> service.findById(nonExistingId));
	    }

	    @Test
	    public void insertShouldReturnSectorDTO() {
	        SectorDTO dto = new SectorDTO();
	        dto.setNameSector("Setor Atualizado"); // <- Isso precisa estar de acordo com o que você está testando
	        dto.setProcess("Processo 1");

	        when(repository.save(any(Sector.class)))
	            .thenAnswer(invocation -> {
	                Sector entity = invocation.getArgument(0);
	                entity.setId(1L); // Simula salvamento com ID
	                return entity;
	            });

	        SectorDTO result = service.insert(dto);

	        assertNotNull(result);
	        assertEquals("Setor Atualizado", result.getNameSector()); // Aqui foi onde falhou
	        assertEquals("Processo 1", result.getProcess());
	    }

	    @Test
	    public void updateShouldReturnSectorDTOWhenIdExists() {
	        SectorDTO result = service.update(existingId, sectorDTO);
	        assertNotNull(result);
	        assertEquals(sectorDTO.getNameSector(), result.getNameSector());
	    }

	    @Test
	    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
	        assertThrows(ResourceNotFoundException.class, () -> service.update(nonExistingId, sectorDTO));
	    }

	    @Test
	    public void deleteShouldDoNothingWhenIdExists() {
	        assertDoesNotThrow(() -> service.delete(existingId));
	        verify(repository).deleteById(existingId);
	    }

	    @Test
	    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
	        assertThrows(ResourceNotFoundException.class, () -> service.delete(nonExistingId));
	    }

	    @Test
	    public void deleteShouldThrowDatabaseExceptionWhenIntegrityViolationOccurs() {
	        doThrow(DataIntegrityViolationException.class).when(repository).deleteById(existingId);
	        assertThrows(DatabaseException.class, () -> service.delete(existingId));
	    }
}
