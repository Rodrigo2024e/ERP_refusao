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

import com.smartprocessrefusao.erprefusao.dto.ProductDispatchDTO;
import com.smartprocessrefusao.erprefusao.dto.ReportProductDispatchDTO;
import com.smartprocessrefusao.erprefusao.dto.TicketDTO;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.Product;
import com.smartprocessrefusao.erprefusao.entities.ProductDispatch;
import com.smartprocessrefusao.erprefusao.entities.Ticket;
import com.smartprocessrefusao.erprefusao.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.repositories.ProductDispatchRepository;
import com.smartprocessrefusao.erprefusao.repositories.ProductRepository;
import com.smartprocessrefusao.erprefusao.repositories.TicketRepository;
import com.smartprocessrefusao.erprefusao.services.ProductDispatchService;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.ProductDispatchFactory;
import com.smartprocessrefusao.erprefusao.tests.TicketFactory;

@SpringBootTest
@Transactional
public class ProductDispatchServiceIT {

	@Autowired
	private ProductDispatchService service;

	@Autowired
	private ProductDispatchRepository productDispatchRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private PartnerRepository partnerRepository;

	@Autowired
	private ProductRepository productRepository;

	private ProductDispatch productDispatch;
	private ProductDispatchDTO productDispatchDTO;
	private Ticket ticket;
	private TicketDTO ticketDTO;
	private Partner partner;
	private Product product;
	private Long existingId, existingPartnerId;
	private Integer existingTicketId;
	private Long nonExistingId, nonExistingPartnerId, nonExistingProductId;

	@BeforeEach
	void setUp() {
		ticket = ticketRepository.save(TicketFactory.createTicketProductDispatch());
		existingId = 1L;
		existingPartnerId = 4L;
		nonExistingId = 999L;
		existingTicketId = 35280;
		nonExistingPartnerId = 999L;
		nonExistingProductId = 999L;

	}

	@Test
	public void reportMovement_ShouldReturnPagedResult() {
		PageRequest pageable = PageRequest.of(0, 10);
		productDispatch = ProductDispatchFactory.createProductDispatch();
		Page<ReportProductDispatchDTO> result = service.reportDispatch(ticket.getNumTicket(), pageable);

		assertFalse(result.isEmpty());
		assertEquals(productDispatch.getId(), result.getContent().get(0).getId());
		
	}

	@Test
	public void findById_ShouldReturnDTO_WhenIdExists() {
		ProductDispatchDTO dto = ProductDispatchFactory.createProductDispatchDTO();

		ProductDispatchDTO result = service.findById(dto.getId());

		assertEquals(dto.getId(), result.getId());
	}

	@Test
	public void findById_ShouldThrowException_WhenIdDoesNotExist() {
		assertThrows(ResourceNotFoundException.class, () -> service.findById(999L));
	}

	@Test
	public void insert_ShouldPersist_WhenValidData() {
		ProductDispatchDTO dto = ProductDispatchFactory.createProductDispatchDTO();

		ProductDispatchDTO result = service.insert(dto);

		assertNotNull(result.getId());
		assertEquals(dto.getAmountProduct(), result.getAmountProduct());
		assertEquals(dto.getUnitValue(), result.getUnitValue());
	}

	@Test
	public void insert_ShouldThrowException_WhenExceedsNetWeight() {
		Ticket smallTicket = ticketRepository.save(TicketFactory.createTicket());
		smallTicket.setNetWeight(BigDecimal.valueOf(100));
		ticketRepository.save(smallTicket);

		ProductDispatchDTO dto = ProductDispatchFactory.createProductDispatchAmountExceedsTicketWeightDTO();

		assertThrows(IllegalArgumentException.class, () -> service.insert(dto));
	}

	@Test
	public void insert_ShouldThrowResourceNotFoundException_WhenPartnerDoesNotExist() {
		productDispatchDTO = ProductDispatchFactory.createProductDispatchInvalidPartnerDTO();

		assertThrows(ResourceNotFoundException.class, () -> service.insert(productDispatchDTO));
	}

	@Test
	public void insert_ShouldThrowResourceNotFoundException_WhenProductDoesNotExist() {
		productDispatchDTO = ProductDispatchFactory.createProductDispatchInvalidProductDTO();

		assertThrows(ResourceNotFoundException.class, () -> service.insert(productDispatchDTO));
	}

	@Test
	public void insert_ShouldThrowResourceNotFoundException_WhenInvalidTransaction() {
		productDispatchDTO = ProductDispatchFactory.createProductDispatchInvalidTransactionDTO();

		assertThrows(ResourceNotFoundException.class, () -> service.insert(productDispatchDTO));
	}

	@Test
	public void update_ShouldUpdateData_WhenIdExists() {

		ProductDispatchDTO dto = ProductDispatchFactory.createProductDispatchDTO();

		ProductDispatchDTO updated = service.update(existingId, dto);

		assertEquals(existingId, updated.getId());
		assertEquals(dto.getAmountProduct(), updated.getAmountProduct());
	}

	@Test
	public void update_ShouldThrowException_WhenExceedsNetWeight() {
		ticketDTO = ProductDispatchFactory.createNewTicketDTO();
		productDispatchDTO = ProductDispatchFactory.createProductDispatchAmountExceedsTicketWeightDTO();

		assertThrows(IllegalArgumentException.class, () -> service.update(existingId, productDispatchDTO));
	}

	@Test
	public void update_ShouldThrowException_WhenPartnerDoesNotExist() {
		productDispatchDTO = ProductDispatchFactory.createProductDispatchInvalidPartnerDTO();

		assertThrows(ResourceNotFoundException.class, () -> service.update(nonExistingPartnerId, productDispatchDTO));
	}

	@Test
	public void update_ShouldThrowException_WhenProductDoesNotExist() {
		productDispatchDTO = ProductDispatchFactory.createProductDispatchInvalidProductDTO();

		assertThrows(ResourceNotFoundException.class, () -> service.update(nonExistingProductId, productDispatchDTO));
	}

	@Test
	public void update_ShouldThrowResourceNotFoundException_WhenInvalidTransaction() {
		productDispatchDTO = ProductDispatchFactory.createProductDispatchInvalidTransactionDTO();

		assertThrows(ResourceNotFoundException.class, () -> service.update(999L, productDispatchDTO));
	}

	@Test
	public void delete_ShouldDeleteProductDispatch_WhenIdExists() {
		service.delete(existingId);

		Optional<ProductDispatch> result = productDispatchRepository.findById(existingId);
		assertFalse(result.isPresent());
	}

	@Test
	public void delete_ShouldThrowException_WhenIdDoesNotExist() {
		assertThrows(ResourceNotFoundException.class, () -> service.delete(999L));
	}

}
