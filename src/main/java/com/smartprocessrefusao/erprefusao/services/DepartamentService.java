package com.smartprocessrefusao.erprefusao.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.DepartamentDTO;
import com.smartprocessrefusao.erprefusao.entities.Departament;
import com.smartprocessrefusao.erprefusao.enumerados.EmployeePosition;
import com.smartprocessrefusao.erprefusao.repositories.DepartamentRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DepartamentService {

	@Autowired
	private DepartamentRepository sectorRepository;

	@Transactional(readOnly = true)
	public List<DepartamentDTO> findAll() {
		List<Departament> list = sectorRepository.findAllByOrderByNameAsc();
		return list.stream().map(DepartamentDTO::new).toList();
	}

	@Transactional(readOnly = true)
	public DepartamentDTO findById(Long id) {
		try {
			Optional<Departament> obj = sectorRepository.findById(id);
			Departament entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
			return new DepartamentDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}

	}

	@Transactional
	public DepartamentDTO insert(DepartamentDTO dto) {
		String name = dto.getName().toUpperCase();
		String process = dto.getProcess().toUpperCase();
		EmployeePosition position = dto.getPosition();

		if (sectorRepository.existsByNameAndProcessAndPosition(name, process, position)) {
			throw new DatabaseException("Já existe um departamento com este nome, processo e função.");
		}

		Departament entity = new Departament();
		entity.setName(name);
		entity.setProcess(process);
		entity.setPosition(position);

		entity = sectorRepository.save(entity);
		return new DepartamentDTO(entity);
	}

	@Transactional
	public DepartamentDTO update(Long id, DepartamentDTO dto) {
		try {
			Departament entity = sectorRepository.getReferenceById(id);

			String name = dto.getName().toUpperCase();
			String process = dto.getProcess().toUpperCase();
			EmployeePosition position = dto.getPosition();

			if (sectorRepository.existsByNameAndProcessAndPositionAndIdNot(name, process, position, id)) {
				throw new DatabaseException("Já existe outro departamento com este nome, processo e função.");
			}

			entity.setName(name);
			entity.setProcess(process);
			entity.setPosition(position);

			entity = sectorRepository.save(entity);
			return new DepartamentDTO(entity);

		} catch (EntityNotFoundException e) {
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
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	public void copyDtoToEntity(DepartamentDTO dto, Departament entity) {
		entity.setName(dto.getName().toUpperCase());
		entity.setProcess(dto.getProcess().toUpperCase());

		try {
			EmployeePosition role = (dto.getPosition());
			entity.setPosition(role);
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de função inválida: " + dto.getPosition());
		}

	}

}
