package com.smartprocessrefusao.erprefusao.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.DispatchDTO;
import com.smartprocessrefusao.erprefusao.dto.ReportDispatchDTO;

import com.smartprocessrefusao.erprefusao.entities.Dispatch;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.Ticket;
import com.smartprocessrefusao.erprefusao.repositories.DispatchRepository;
import com.smartprocessrefusao.erprefusao.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.repositories.TicketRepository;
import com.smartprocessrefusao.erprefusao.services.DispatchService;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;


/*
@SpringBootTest
@Transactional
public class DispatchServiceIT {

	@Autowired
	private DispatchService service;

	@Autowired
	private DispatchRepository productDispatchRepository;

	@Autowired
	private TicketRepository ticketRepository;

	private Dispatch productDispatch;
	private DispatchDTO productDispatchDTO;
	private Ticket ticket;
	private TicketDTO ticketDTO;

	private Long existingId;

	private Long nonExistingPartnerId, nonExistingProductId;

	@BeforeEach
	void setUp() {
		ticket = ticketRepository.save(TicketFactory.createTicketProductDispatch());
		existingId = 1L;

		nonExistingPartnerId = 999L;
		nonExistingProductId = 999L;

	}

	@Test
	public void reportMovement_ShouldReturnPagedResult() {
		PageRequest pageable = PageRequest.of(0, 10);
		productDispatch = DispatchFactory.createProductDispatch();
		Page<ReportDispatchDTO> result = service.reportDispatch(ticket.getTicket(), pageable);

		assertFalse(result.isEmpty());
		assertEquals(productDispatch.getId(), result.getContent().get(0).getId());

	}

	@Test
	public void findById_ShouldReturnDTO_WhenIdExists() {
		DispatchDTO dto = DispatchFactory.createDispatchDTO();

		DispatchDTO result = service.findById(dto.getId());

		assertEquals(dto.getId(), result.getId());
	}

	@Test
	public void findById_ShouldThrowException_WhenIdDoesNotExist() {
		assertThrows(ResourceNotFoundException.class, () -> service.findById(999L));
	}

	@Test
	public void insert_ShouldPersist_WhenValidData() {
		DispatchDTO dto = DispatchFactory.createDispatchDTO();

		DispatchDTO result = service.insert(dto);

		assertNotNull(result.getId());
		assertEquals(dto.getAmount(), result.getAmount());
		assertEquals(dto.getUnitValue(), result.getUnitValue());
	}

	@Test
	public void insert_ShouldThrowException_WhenExceedsNetWeight() {
		Ticket smallTicket = ticketRepository.save(TicketFactory.createTicket());
		smallTicket.setNetWeight(BigDecimal.valueOf(100));
		ticketRepository.save(smallTicket);

		DispatchDTO dto = DispatchFactory.createProductDispatchAmountExceedsTicketWeightDTO();

		assertThrows(IllegalArgumentException.class, () -> service.insert(dto));
	}

	@Test
	public void insert_ShouldThrowResourceNotFoundException_WhenPartnerDoesNotExist() {
		productDispatchDTO = DispatchFactory.createProductDispatchInvalidPartnerDTO();

		assertThrows(ResourceNotFoundException.class, () -> service.insert(productDispatchDTO));
	}

	@Test
	public void insert_ShouldThrowResourceNotFoundException_WhenProductDoesNotExist() {
		productDispatchDTO = DispatchFactory.createProductDispatchInvalidProductDTO();

		assertThrows(ResourceNotFoundException.class, () -> service.insert(productDispatchDTO));
	}

	@Test
	public void insert_ShouldThrowResourceNotFoundException_WhenInvalidTransaction() {
		productDispatchDTO = DispatchFactory.createProductDispatchInvalidTransactionDTO();

		assertThrows(ResourceNotFoundException.class, () -> service.insert(productDispatchDTO));
	}

	@Test
	public void update_ShouldUpdateData_WhenIdExists() {

		DispatchDTO dto = DispatchFactory.createDispatchDTO();

		DispatchDTO updated = service.update(existingId, dto);

		assertEquals(existingId, updated.getId());
		assertEquals(dto.getAmount(), updated.getAmount());
	}

	@Test
	public void update_ShouldThrowException_WhenExceedsNetWeight() {
		ticketDTO = DispatchFactory.createNewTicketDTO();
		productDispatchDTO = DispatchFactory.createProductDispatchAmountExceedsTicketWeightDTO();

		assertThrows(IllegalArgumentException.class, () -> service.update(existingId, productDispatchDTO));
	}

	@Test
	public void update_ShouldThrowException_WhenPartnerDoesNotExist() {
		productDispatchDTO = DispatchFactory.createProductDispatchInvalidPartnerDTO();

		assertThrows(ResourceNotFoundException.class, () -> service.update(nonExistingPartnerId, productDispatchDTO));
	}

	@Test
	public void update_ShouldThrowException_WhenProductDoesNotExist() {
		productDispatchDTO = DispatchFactory.createProductDispatchInvalidProductDTO();

		assertThrows(ResourceNotFoundException.class, () -> service.update(nonExistingProductId, productDispatchDTO));
	}

	@Test
	public void update_ShouldThrowResourceNotFoundException_WhenInvalidTransaction() {
		productDispatchDTO = DispatchFactory.createProductDispatchInvalidTransactionDTO();

		assertThrows(ResourceNotFoundException.class, () -> service.update(999L, productDispatchDTO));
	}

	@Test
	public void delete_ShouldDeleteProductDispatch_WhenIdExists() {
		service.delete(existingId);

		Optional<Dispatch> result = productDispatchRepository.findById(existingId);
		assertFalse(result.isPresent());
	}

	@Test
	public void delete_ShouldThrowException_WhenIdDoesNotExist() {
		assertThrows(ResourceNotFoundException.class, () -> service.delete(999L));
	}

}
*/
