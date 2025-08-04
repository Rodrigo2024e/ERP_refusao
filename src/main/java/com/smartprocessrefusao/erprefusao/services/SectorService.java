package com.smartprocessrefusao.erprefusao.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.SectorDTO;
import com.smartprocessrefusao.erprefusao.entities.Sector;
import com.smartprocessrefusao.erprefusao.repositories.SectorRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SectorService {
	
	@Autowired
	private SectorRepository sectorRepository;
	
	@Transactional(readOnly = true)
	public List<SectorDTO> findAll() {
	    List<Sector> list = sectorRepository.findAllByOrderByNameSectorAsc();
	    return list.stream().map(SectorDTO::new).toList();
	}

	@Transactional(readOnly = true)
	public SectorDTO findById(Long id) {
		try {
		Optional<Sector> obj = sectorRepository.findById(id);
		Sector entity = obj.orElseThrow(()-> new EntityNotFoundException("Entity not found"));
		return new SectorDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}	
		
	}

	@Transactional
	public SectorDTO insert(SectorDTO dto) {
		Sector entity = new Sector();
		copyDtoToEntity(dto, entity);
		entity = sectorRepository.save(entity);
		return new SectorDTO(entity);
	}
	
	@Transactional
	public SectorDTO update(Long id, SectorDTO dto) {	
		try {
			Sector entity = sectorRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = sectorRepository.save(entity);
			return new SectorDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!sectorRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			sectorRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	public void copyDtoToEntity(SectorDTO dto, Sector entity) {
	    entity.setNameSector(dto.getNameSector().toUpperCase());
	    entity.setProcess(dto.getProcess().toUpperCase());

	}
	
}
