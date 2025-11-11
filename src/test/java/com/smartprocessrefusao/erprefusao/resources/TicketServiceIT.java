package com.smartprocessrefusao.erprefusao.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.entities.Ticket;
import com.smartprocessrefusao.erprefusao.repositories.TicketRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
/*

@SpringBootTest
@Transactional
public class TicketServiceIT {

	@Autowired
	private TicketService service;

	@Autowired
	private TicketRepository repository;

	private Ticket ticket;
	private TicketDTO ticketDTO;
	private Integer existingTicketId, nonExistingTicketId;

	@BeforeEach
	void setUp() {
		ticket = TicketFactory.createTicket();
		repository.save(ticket);
		existingTicketId = 34950;
		nonExistingTicketId = 9999;

	}

	@Test
	public void findAllPaged_ShouldReturnPagedResult() {
		PageRequest pageRequest = PageRequest.of(0, 10);
		Page<TicketDTO> result = service.findAllPaged(pageRequest);

		assertFalse(result.isEmpty());
		assertEquals(ticket.getTicket(), result.getContent().get(0).getTicket());
	}

	@Test
	public void findById_ShouldReturnTicketDTO_WhenTicketExists() {
		ticketDTO = TicketFactory.createTicketDTO();

		assertNotNull(ticketDTO);
		assertEquals(existingTicketId, ticketDTO.getTicket());
	}

	@Test
	public void findById_ShouldThrowException_WhenIdDoesNotExist() {
		assertThrows(ResourceNotFoundException.class, () -> service.findById(nonExistingTicketId));
	}

	@Test
	public void insert_ShouldPersistNewTicket() {
		ticketDTO = TicketFactory.createNewTicketDTO();

		TicketDTO saved = service.insert(ticketDTO);

		assertNotNull(saved);
		assertEquals(34951, saved.getTicket());
		assertEquals(ticketDTO.getNumberPlate(), saved.getNumberPlate());
	}

	@Test
	public void insert_ShouldThrowDatabaseException_WhenTicketAlreadyExists() {
		TicketDTO dto = new TicketDTO(ticket);

		assertThrows(DatabaseException.class, () -> service.insert(dto));
	}

	@Test
	public void update_ShouldUpdateData_WhenTicketExists() {
		ticketDTO = TicketFactory.createTicketDTO();

		TicketDTO updated = service.update(existingTicketId, ticketDTO);

		assertEquals(existingTicketId, updated.getTicket());
		assertEquals(ticketDTO.getNumberPlate(), updated.getNumberPlate());
	}

	@Test
	public void update_ShouldThrowException_WhenTicketDoesNotExist() {
		ticketDTO = TicketFactory.createTicketNoExistis();

		assertThrows(ResourceNotFoundException.class, () -> service.update(nonExistingTicketId, ticketDTO));
	}

	@Test
	public void delete_ShouldDeleteTicket_WhenTicketExists() {
		service.delete(existingTicketId);

		Optional<Ticket> result = repository.findById(existingTicketId);
		assertFalse(result.isPresent());
	}

	@Test
	public void delete_ShouldThrowException_WhenTicketDoesNotExist() {
		assertThrows(ResourceNotFoundException.class, () -> service.delete(nonExistingTicketId));
	}

	@Test
	public void delete_ShouldThrowDatabaseException_WhenIntegrityViolation() {

		assertThrows(DataIntegrityViolationException.class, () -> {
			throw new DataIntegrityViolationException("Integrity violation");
		});
	}
}
*/
