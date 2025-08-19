package com.smartprocessrefusao.erprefusao.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.TicketDTO;
import com.smartprocessrefusao.erprefusao.entities.Ticket;
import com.smartprocessrefusao.erprefusao.repositories.TicketRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Transactional(readOnly = true)
	public Page<TicketDTO> findAllPaged(Pageable pageable) {
		Page<Ticket> list = ticketRepository.findAll(pageable);
		return list.map(x -> new TicketDTO(x));
	}

	@Transactional(readOnly = true)
	public TicketDTO findById(Integer id) {
		try {

			Ticket entity = ticketRepository.findByNumTicket(id)
					.orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
			return new TicketDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Ticket not found " + id);
		}
	}

	@Transactional
	public TicketDTO insert(TicketDTO dto) {

		Optional<Ticket> existingTicket = ticketRepository.findByNumTicket(dto.getNumTicket());
		if (existingTicket.isPresent()) {
			throw new DatabaseException("Ticket number already exists: " + dto.getNumTicket());
		}

		Ticket entity = new Ticket();
		copyDtoToEntity(dto, entity);
		entity = ticketRepository.save(entity);
		return new TicketDTO(entity);
	}

	@Transactional
	public TicketDTO update(Integer id, TicketDTO dto) {
		try {

			Ticket entity = ticketRepository.findByNumTicket(id)
					.orElseThrow(() -> new ResourceNotFoundException("Ticket not found " + id));

			entity.setDateTicket(dto.getDateTicket());
			entity.setNumberPlate(dto.getNumberPlate());
			entity.setNetWeight(dto.getNetWeight());
			entity = ticketRepository.save(entity);

			return new TicketDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Ticket not found " + id);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Integer id) {
		if (!ticketRepository.existsById(id)) {
			throw new ResourceNotFoundException("Ticket not found " + id);
		}
		try {
			ticketRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	public void copyDtoToEntity(TicketDTO dto, Ticket entity) {
		entity.setNumTicket(dto.getNumTicket());
		entity.setDateTicket(dto.getDateTicket());
		entity.setNumberPlate(dto.getNumberPlate());
		entity.setNetWeight(dto.getNetWeight());
	}

}
