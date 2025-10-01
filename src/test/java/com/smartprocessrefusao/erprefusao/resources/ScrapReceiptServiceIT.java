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

import com.smartprocessrefusao.erprefusao.dto.ReportScrapReceiptDTO;
import com.smartprocessrefusao.erprefusao.dto.ScrapReceiptDTO;
import com.smartprocessrefusao.erprefusao.entities.ScrapReceipt;
import com.smartprocessrefusao.erprefusao.entities.Ticket;
import com.smartprocessrefusao.erprefusao.repositories.ScrapReceiptRepository;
import com.smartprocessrefusao.erprefusao.repositories.TicketRepository;
import com.smartprocessrefusao.erprefusao.services.ScrapReceiptService;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.ScrapReceiptFactory;
import com.smartprocessrefusao.erprefusao.tests.TicketFactory;

@SpringBootTest
@Transactional
public class ScrapReceiptServiceIT {

	@Autowired
	private ScrapReceiptService service;

	@Autowired
	private ScrapReceiptRepository scrapReceiptRepository;

	@Autowired
	private TicketRepository ticketRepository;

	private Ticket ticket;
	private ScrapReceipt scrapReceipt;
	private ScrapReceiptDTO scrapReceiptDTO;
	private Long existingId;
	private Long nonExistingId, nonExistingPartnerId, nonExistingInputId;

	@BeforeEach
	void setUp() {
		ticket = ticketRepository.save(TicketFactory.createTicket());
		scrapReceipt = scrapReceiptRepository.save(ScrapReceiptFactory.createScrapReceipt());
		existingId = 1L;
		nonExistingId = 999L;
		nonExistingPartnerId = 999L;
		nonExistingInputId = 999L;

	}

	@Test
	public void reportMovement_ShouldReturnPagedResult() {
		PageRequest pageable = PageRequest.of(0, 10);
		Page<ReportScrapReceiptDTO> result = service.reportMovement(ticket.getNumTicket(), pageable);

		assertFalse(result.isEmpty());
		assertEquals(scrapReceipt.getId(), result.getContent().get(0).getId());
	}

	@Test
	public void findById_ShouldReturnScrapReceiptDTO_WhenIdExists() {
		ScrapReceiptDTO dto = service.findById(existingId);

		assertNotNull(dto);
		assertEquals(existingId, dto.getId());
	}

	@Test
	public void findById_ShouldThrowException_WhenIdDoesNotExist() {
		assertThrows(ResourceNotFoundException.class, () -> service.findById(nonExistingId));
	}

	@Test
	public void insert_ShouldPersistNewScrapReceipt() {
		scrapReceiptDTO = ScrapReceiptFactory.createScrapReceiptDTO();

		ScrapReceiptDTO saved = service.insert(scrapReceiptDTO);

		assertNotNull(saved.getId());
		assertEquals(BigDecimal.valueOf(100.00).setScale(2), saved.getAmountScrap());
		assertEquals(ticket.getNumTicket(), saved.getNumTicketId());
	}

	@Test
	public void insert_ShouldThrowException_WhenExceedsNetWeight() {
		Ticket smallTicket = ticketRepository.save(TicketFactory.createTicket());
		smallTicket.setNetWeight(BigDecimal.valueOf(100));
		ticketRepository.save(smallTicket);

		ScrapReceiptDTO dto = ScrapReceiptFactory.createScrapReceiptDTO();

		assertThrows(IllegalArgumentException.class, () -> service.insert(dto));
	}
	
    @Test
    public void insert_ShouldThrowResourceNotFoundException_WhenPartnerDoesNotExist() {
       scrapReceiptDTO = ScrapReceiptFactory.createScrapReceiptPartnerDoesNotExistsDTO();

        assertThrows(ResourceNotFoundException.class, () -> service.insert(scrapReceiptDTO));
    }
    
    @Test
    public void insert_ShouldThrowResourceNotFoundException_WhenInputDoesNotExist() {
    	scrapReceiptDTO = ScrapReceiptFactory.createScrapReceiptInputDoesNotExistsDTO();

        assertThrows(ResourceNotFoundException.class, () -> service.insert(scrapReceiptDTO));
    }
    
    @Test
    public void insert_ShouldThrowResourceNotFoundException_WhenInvalidCosts() {
    	scrapReceiptDTO = ScrapReceiptFactory.createScrapReceiptInvalidCostsDTO();

        assertThrows(ResourceNotFoundException.class, () -> service.insert(scrapReceiptDTO));
    }

    @Test
    public void insert_ShouldThrowResourceNotFoundException_WhenInvalidTransaction() {
    	scrapReceiptDTO = ScrapReceiptFactory.createScrapReceiptInvalidTranscationDTO();

        assertThrows(ResourceNotFoundException.class, () -> service.insert(scrapReceiptDTO));
    }
    
	@Test
	public void update_ShouldUpdateData_WhenIdExists() {

		ScrapReceiptDTO dto = ScrapReceiptFactory.createScrapReceiptDTO();

		ScrapReceiptDTO updated = service.update(existingId, dto);

		assertEquals(existingId, updated.getId());
		assertEquals(dto.getAmountScrap(), updated.getAmountScrap());
	}

	@Test
	public void update_ShouldThrowException_WhenIdDoesNotExist() {
		ScrapReceiptDTO dto = ScrapReceiptFactory.createScrapReceiptDTO();

		assertThrows(ResourceNotFoundException.class, () -> service.update(nonExistingId, dto));
	}
	
	@Test
	public void update_ShouldThrowException_WhenPartnerDoesNotExist() {
		ScrapReceiptDTO dto = ScrapReceiptFactory.createScrapReceiptPartnerDoesNotExistsDTO();

		assertThrows(ResourceNotFoundException.class, () -> service.update(nonExistingPartnerId, dto));
	}
	
	@Test
	public void update_ShouldThrowException_WhenInputDoesNotExist() {
		ScrapReceiptDTO dto = ScrapReceiptFactory.createScrapReceiptPartnerDoesNotExistsDTO();

		assertThrows(ResourceNotFoundException.class, () -> service.update(nonExistingInputId, dto));
	}
	
	  @Test
	    public void update_ShouldThrowResourceNotFoundException_WhenInvalidCosts() {
	    	scrapReceiptDTO = ScrapReceiptFactory.createScrapReceiptInvalidCostsDTO();

	        assertThrows(ResourceNotFoundException.class, () -> service.update(99L, scrapReceiptDTO));
	    }

	    @Test
	    public void update_ShouldThrowResourceNotFoundException_WhenInvalidTransaction() {
	    	scrapReceiptDTO = ScrapReceiptFactory.createScrapReceiptInvalidTranscationDTO();

	        assertThrows(ResourceNotFoundException.class, () -> service.update(99L, scrapReceiptDTO));
	    }

	@Test
	public void delete_ShouldDeleteScrapReceipt_WhenIdExists() {
		service.delete(existingId);

		Optional<ScrapReceipt> result = scrapReceiptRepository.findById(existingId);
		assertFalse(result.isPresent());
	}

	@Test
	public void delete_ShouldThrowException_WhenIdDoesNotExist() {
		assertThrows(ResourceNotFoundException.class, () -> service.delete(nonExistingId));
	}
}
