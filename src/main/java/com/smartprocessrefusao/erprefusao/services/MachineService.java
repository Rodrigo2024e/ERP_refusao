package com.smartprocessrefusao.erprefusao.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.MachineDTO;
import com.smartprocessrefusao.erprefusao.entities.Machine;
import com.smartprocessrefusao.erprefusao.repositories.MachineRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MachineService {

	@Autowired
	private MachineRepository machineRepository;

	@Transactional(readOnly = true)
	public List<MachineDTO> findAll() {
		List<Machine> list = machineRepository.findAll();
		return list.stream().map(MachineDTO::new).toList();
	}

	@Transactional(readOnly = true)
	public MachineDTO findById(Long id) {
		Optional<Machine> obj = machineRepository.findById(id);
		Machine entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
		return new MachineDTO(entity);
	}

	@Transactional
	public MachineDTO insert(MachineDTO dto) {
		Machine entity = new Machine();
		copyDtoToEntity(dto, entity);
		entity = machineRepository.save(entity);
		return new MachineDTO(entity);
	}

	@Transactional
	public MachineDTO update(Long id, MachineDTO dto) {
		try {
			Machine entity = machineRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = machineRepository.save(entity);
			return new MachineDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!machineRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}

		try {
			machineRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	public void copyDtoToEntity(MachineDTO dto, Machine entity) {
		entity.setDescription(dto.getDescription().toUpperCase());
		entity.setBrand(dto.getBrand().toUpperCase());
		entity.setModel(dto.getModel().toUpperCase());
		entity.setManufacturingYear(dto.getManufacturingYear());
		entity.setCapacity(dto.getCapacity());
	}

}
