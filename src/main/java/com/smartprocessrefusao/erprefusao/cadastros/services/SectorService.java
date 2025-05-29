package com.smartprocessrefusao.erprefusao.cadastros.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.cadastros.dto.SectorDTO;
import com.smartprocessrefusao.erprefusao.cadastros.entities.Sector;
import com.smartprocessrefusao.erprefusao.cadastros.repositories.SectorRepository;
import com.smartprocessrefusao.erprefusao.cadastros.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.cadastros.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SectorService {
	
	@Autowired
	private SectorRepository setorRepository;
	
	@Transactional(readOnly = true)
	public List<SectorDTO> findAll() {
		List<Sector> list = setorRepository.findAll();
		return list.stream().map(x -> new SectorDTO(x)).toList();
	}

	@Transactional(readOnly = true)
	public SectorDTO findById(Long id) {
		try {
		Optional<Sector> obj = setorRepository.findById(id);
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
		entity = setorRepository.save(entity);
		return new SectorDTO(entity);
	}
	
	@Transactional
	public SectorDTO update(Long id, SectorDTO dto) {
		try {
			Sector entity = setorRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = setorRepository.save(entity);
			return new SectorDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!setorRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			setorRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	public void copyDtoToEntity(SectorDTO dto, Sector entity) {
	    entity.setNameSector(dto.getNameSector());
	    entity.setProcess(dto.getProcess());

	}
	
}
