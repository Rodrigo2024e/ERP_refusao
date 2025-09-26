package com.smartprocessrefusao.erprefusao.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.smartprocessrefusao.erprefusao.dto.ScrapReceiptDTO;
import com.smartprocessrefusao.erprefusao.entities.Input;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.ScrapReceipt;
import com.smartprocessrefusao.erprefusao.entities.Ticket;
import com.smartprocessrefusao.erprefusao.enumerados.TypeCosts;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionReceipt;
import com.smartprocessrefusao.erprefusao.repositories.InputRepository;
import com.smartprocessrefusao.erprefusao.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.repositories.ScrapReceiptRepository;
import com.smartprocessrefusao.erprefusao.repositories.TicketRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.ScrapReceiptFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ScrapReceiptServiceTest {

	@InjectMocks
	private ScrapReceiptService service;

	@Mock
	private ScrapReceiptRepository scrapReceiptRepository;
	@Mock
	private TicketRepository ticketRepository;
	@Mock
	private InputRepository inputRepository;
	@Mock
	private PartnerRepository partnerRepository;

	private long existingId;
	private long nonExistingId;
	private int existingTicketId;
	private int nonExistingTicketId;
	private Long existingPartnerId;
	private Long nonExistingPartnerId;
	private Long nonExistingInputId;
	private ScrapReceipt scrapReceipt;
	private ScrapReceiptDTO scrapReceiptDTO;
	private Ticket ticket;
	private Partner partner;
	private Input input;

	@BeforeEach
	void setUp() {
		existingId = 1L;
		nonExistingId = 999L;
		existingTicketId = 12345;
		nonExistingTicketId = 98765;
		existingPartnerId = 3L;
		nonExistingPartnerId = 999L;
		nonExistingInputId = 999L;

		scrapReceipt = ScrapReceiptFactory.createScrapReceipt();
		scrapReceiptDTO = ScrapReceiptFactory.createScrapReceiptDTO();
		ticket = ScrapReceiptFactory.createTicket();
		partner = ScrapReceiptFactory.createPartner();
		input = ScrapReceiptFactory.createInput();

	}

	// --- findById --- Id found
	@Test
	public void findById_ShouldReturnScrapReceiptDTO_WhenIdExists() {
		when(scrapReceiptRepository.findById(existingId)).thenReturn(Optional.of(scrapReceipt));

		ScrapReceiptDTO result = service.findById(existingId);

		assertNotNull(result);
		assertEquals(existingId, result.getId());
		verify(scrapReceiptRepository, times(1)).findById(existingId);
	}

	// --- findById --- Id not found
	@Test
	public void findById_ShouldThrowResourceNotFoundException_WhenIdDoesNotExist() {
		when(scrapReceiptRepository.findById(nonExistingId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> service.findById(nonExistingId));
		verify(scrapReceiptRepository, times(1)).findById(nonExistingId);
	}

	// --- projection ---
	@Test
	public void reportMovement_ShouldReturnPageOfScrapReceiptDTO_WhenTicketExists() {
		when(scrapReceiptRepository.searchScrapReceiptByNumberTicket(anyInt(), any(Pageable.class)))
				.thenReturn(new PageImpl<>(java.util.List.of(new ScrapReceiptFactory.ScrapReceiptProjectionImpl(1L,
						existingTicketId, 100L, "Partner", "RECEBIMENTO", "FRETE", 200L, "Input",
						new BigDecimal("100.00"), new BigDecimal("5.00"), new BigDecimal("500.00"),
						new BigDecimal("0.80"), new BigDecimal("80.00"), new BigDecimal("20.00")))));

		Pageable pageable = PageRequest.of(0, 10);
		Page<ScrapReceiptDTO> result = service.reportMovement(existingTicketId, pageable);

		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(1, result.getContent().size());
		verify(scrapReceiptRepository, times(1)).searchScrapReceiptByNumberTicket(existingTicketId, pageable);
	}

	// --- insert --- ticket found
	@Test
	public void insert_ShouldPersistAndReturnScrapReceiptDTO_WhenDataIsValid() {
		when(scrapReceiptRepository.save(any(ScrapReceipt.class))).thenReturn(scrapReceipt);
		when(ticketRepository.findByNumTicket(existingTicketId)).thenReturn(Optional.of(ticket));
		when(partnerRepository.findById(anyLong())).thenReturn(Optional.of(partner));
		when(inputRepository.findById(anyLong())).thenReturn(Optional.of(input));
		when(scrapReceiptRepository.sumAmountScrapByNumTicket(existingTicketId)).thenReturn(new BigDecimal("50.00"));

		ScrapReceiptDTO result = service.insert(scrapReceiptDTO);

		assertNotNull(result);
		assertEquals(scrapReceipt.getId(), result.getId());
		assertEquals(scrapReceipt.getAmountScrap(), result.getAmountScrap());
		verify(scrapReceiptRepository, times(1)).sumAmountScrapByNumTicket(scrapReceiptDTO.getNumTicketId());
		verify(scrapReceiptRepository, times(1)).save(any(ScrapReceipt.class));
	}

	// --- insert --- Somar amountScrap existentes do ticket (se tiver)
	@Test
	public void insert_ShouldHandleNullSumAndPersist_WhenNoPreviousScrapExists() {
		// Mocking para o cenário onde não há registros anteriores (retorna null)
		when(scrapReceiptRepository.sumAmountScrapByNumTicket(existingTicketId)).thenReturn(null);

		// Mocks necessários para o fluxo continuar
		when(ticketRepository.findByNumTicket(existingTicketId)).thenReturn(Optional.of(ticket));
		when(partnerRepository.findById(anyLong())).thenReturn(Optional.of(partner));
		when(inputRepository.findById(anyLong())).thenReturn(Optional.of(input));
		when(scrapReceiptRepository.save(any(ScrapReceipt.class))).thenReturn(scrapReceipt);

		ScrapReceiptDTO result = service.insert(scrapReceiptDTO);

		// Verificações para confirmar se o comportamento esperado ocorreu
		assertNotNull(result);
		assertEquals(scrapReceipt.getId(), result.getId());
		verify(scrapReceiptRepository, times(1)).sumAmountScrapByNumTicket(scrapReceiptDTO.getNumTicketId());
		verify(scrapReceiptRepository, times(1)).save(any(ScrapReceipt.class));
	}

	// ResourceNotFoundException_WhenTicketDoesNotExist
	@Test
	public void insert_ShouldThrowResourceNotFoundException_WhenTicketDoesNotExist() {
		ScrapReceiptDTO dto = new ScrapReceiptDTO(scrapReceiptDTO.getId(), nonExistingTicketId,
				scrapReceiptDTO.getPartnerId(), scrapReceiptDTO.getPartnerName(),
				scrapReceiptDTO.getTransactionDescription(), scrapReceiptDTO.getCosts(), scrapReceiptDTO.getInputId(),
				scrapReceiptDTO.getInputDescription(), scrapReceiptDTO.getAmountScrap(), scrapReceiptDTO.getUnitValue(),
				scrapReceiptDTO.getTotalValue(), scrapReceiptDTO.getMetalYield(), scrapReceiptDTO.getMetalWeight(),
				scrapReceiptDTO.getSlag());

		when(ticketRepository.findByNumTicket(nonExistingTicketId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> service.insert(dto));
		verify(ticketRepository, times(1)).findByNumTicket(nonExistingTicketId);
		verify(scrapReceiptRepository, never()).sumAmountScrapByNumTicket(anyInt());
		verify(scrapReceiptRepository, never()).save(any());
	}

	// Peso de ticket excedido!
	@Test
	public void insert_ShouldThrowIllegalArgumentException_WhenAmountExceedsTicketWeight() {
		when(ticketRepository.findByNumTicket(existingTicketId)).thenReturn(Optional.of(ticket));
		when(scrapReceiptRepository.sumAmountScrapByNumTicket(scrapReceiptDTO.getNumTicketId()))
				.thenReturn(ticket.getNetWeight().add(new BigDecimal("10.00")));

		assertThrows(IllegalArgumentException.class, () -> service.insert(scrapReceiptDTO));
		verify(scrapReceiptRepository, never()).save(any());
	}

	// --- update --- id found
	@Test
	public void update_ShouldPersistAndReturnUpdatedScrapReceiptDTO_WhenDataIsValid() {
		when(scrapReceiptRepository.getReferenceById(existingId)).thenReturn(scrapReceipt);
		when(scrapReceiptRepository.save(any(ScrapReceipt.class))).thenReturn(scrapReceipt);
		when(ticketRepository.findByNumTicket(existingTicketId)).thenReturn(Optional.of(ticket));
		when(partnerRepository.findById(anyLong())).thenReturn(Optional.of(partner));
		when(inputRepository.findById(anyLong())).thenReturn(Optional.of(input));
		when(scrapReceiptRepository.sumAmountScrapByNumTicketExcludingId(anyInt(), anyLong()))
				.thenReturn(new BigDecimal("50.00"));

		ScrapReceiptDTO updatedDto = new ScrapReceiptDTO(scrapReceiptDTO.getId(), scrapReceiptDTO.getNumTicketId(),
				scrapReceiptDTO.getPartnerId(), scrapReceiptDTO.getPartnerName(),
				scrapReceiptDTO.getTransactionDescription(), scrapReceiptDTO.getCosts(), scrapReceiptDTO.getInputId(),
				scrapReceiptDTO.getInputDescription(), new BigDecimal("80.00"), // Novo valor
				scrapReceiptDTO.getUnitValue(), null, scrapReceiptDTO.getMetalYield(), null, null);

		ScrapReceiptDTO result = service.update(existingId, updatedDto);

		assertNotNull(result);
		assertEquals(existingId, result.getId());
		verify(scrapReceiptRepository, times(1)).getReferenceById(existingId);
		verify(scrapReceiptRepository, times(1)).sumAmountScrapByNumTicketExcludingId(updatedDto.getNumTicketId(),
				existingId);
		verify(scrapReceiptRepository, times(1)).save(any(ScrapReceipt.class));
	}

	// --- update --- id not found
	@Test
	public void update_ShouldThrowResourceNotFoundException_WhenIdDoesNotExist() {
		when(scrapReceiptRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

		assertThrows(ResourceNotFoundException.class, () -> service.update(nonExistingId, scrapReceiptDTO));
		verify(scrapReceiptRepository, times(1)).getReferenceById(nonExistingId);
		verify(scrapReceiptRepository, never()).save(any());
	}

	// Peso de ticket excedido
	@Test
	public void update_ShouldThrowIllegalArgumentException_WhenAmountExceedsTicketWeight() {
		when(scrapReceiptRepository.getReferenceById(existingId)).thenReturn(scrapReceipt);
		when(scrapReceiptRepository.sumAmountScrapByNumTicketExcludingId(anyInt(), anyLong()))
				.thenReturn(new BigDecimal("50.00"));
		when(ticketRepository.findByNumTicket(anyInt())).thenReturn(Optional.of(ticket));

		ScrapReceiptDTO updatedDto = new ScrapReceiptDTO(scrapReceiptDTO.getId(), scrapReceiptDTO.getNumTicketId(),
				scrapReceiptDTO.getPartnerId(), scrapReceiptDTO.getPartnerName(),
				scrapReceiptDTO.getTransactionDescription(), scrapReceiptDTO.getCosts(), scrapReceiptDTO.getInputId(),
				scrapReceiptDTO.getInputDescription(), new BigDecimal("160.00"), // Novo valor que causará a falha
				scrapReceiptDTO.getUnitValue(), null, scrapReceiptDTO.getMetalYield(), null, null);

		assertThrows(IllegalArgumentException.class, () -> service.update(existingId, updatedDto));
		verify(scrapReceiptRepository, never()).save(any());
	}

	// WhenTicketDoesNotExist
	@Test
	public void update_ShouldThrowResourceNotFoundException_WhenTicketDoesNotExist() {
		ScrapReceiptDTO dto = new ScrapReceiptDTO(scrapReceiptDTO.getId(), nonExistingTicketId,
				scrapReceiptDTO.getPartnerId(), scrapReceiptDTO.getPartnerName(),
				scrapReceiptDTO.getTransactionDescription(), scrapReceiptDTO.getCosts(), scrapReceiptDTO.getInputId(),
				scrapReceiptDTO.getInputDescription(), scrapReceiptDTO.getAmountScrap(), scrapReceiptDTO.getUnitValue(),
				scrapReceiptDTO.getTotalValue(), scrapReceiptDTO.getMetalYield(), scrapReceiptDTO.getMetalWeight(),
				scrapReceiptDTO.getSlag());

		when(ticketRepository.findByNumTicket(nonExistingTicketId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> service.update(existingId, dto));
		verify(ticketRepository, times(1)).findByNumTicket(nonExistingTicketId);
		verify(scrapReceiptRepository, never()).sumAmountScrapByNumTicket(anyInt());
		verify(scrapReceiptRepository, never()).save(any());
	}

	// --- delete ---

	@Test
	public void delete_ShouldDeleteObject_WhenIdExists() {
		when(scrapReceiptRepository.existsById(existingId)).thenReturn(true);
		doNothing().when(scrapReceiptRepository).deleteById(existingId);

		assertDoesNotThrow(() -> service.delete(existingId));
		verify(scrapReceiptRepository, times(1)).existsById(existingId);
		verify(scrapReceiptRepository, times(1)).deleteById(existingId);
	}

	// WhenIdDoesNotExist
	@Test
	public void delete_ShouldThrowResourceNotFoundException_WhenIdDoesNotExist() {
		when(scrapReceiptRepository.existsById(nonExistingId)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> service.delete(nonExistingId));
		verify(scrapReceiptRepository, times(1)).existsById(nonExistingId);
		verify(scrapReceiptRepository, never()).deleteById(nonExistingId);
	}

	// WhenIntegrityViolationOccurs
	@Test
	public void delete_ShouldThrowDatabaseException_WhenIntegrityViolationOccurs() {
		when(scrapReceiptRepository.existsById(existingId)).thenReturn(true);
		doThrow(DataIntegrityViolationException.class).when(scrapReceiptRepository).deleteById(existingId);

		assertThrows(DatabaseException.class, () -> service.delete(existingId));
		verify(scrapReceiptRepository, times(1)).deleteById(existingId);
	}

	// WhenInputDoesNotExist
	@Test
	public void copyDtoToEntity_ShouldThrowResourceNotFoundException_WhenInputDoesNotExist() {
		ScrapReceiptDTO dto = new ScrapReceiptDTO(scrapReceiptDTO.getId(), scrapReceiptDTO.getNumTicketId(),
				scrapReceiptDTO.getPartnerId(), scrapReceiptDTO.getPartnerName(),
				scrapReceiptDTO.getTransactionDescription(), scrapReceiptDTO.getCosts(), nonExistingInputId,
				scrapReceiptDTO.getInputDescription(), scrapReceiptDTO.getAmountScrap(), scrapReceiptDTO.getUnitValue(),
				scrapReceiptDTO.getTotalValue(), scrapReceiptDTO.getMetalYield(), scrapReceiptDTO.getMetalWeight(),
				scrapReceiptDTO.getSlag());

		// Ticket existe
		when(ticketRepository.findByNumTicket(existingTicketId)).thenReturn(Optional.of(new Ticket()));

		// Partner existe
		when(partnerRepository.findById(existingPartnerId)).thenReturn(Optional.of(partner));

		// Input não existe
		when(inputRepository.findById(nonExistingInputId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> service.copyDtoToEntity(dto, scrapReceipt));
		verify(inputRepository, times(1)).findById(nonExistingInputId);
		verify(scrapReceiptRepository, never()).sumAmountScrapByNumTicket(anyInt());
		verify(scrapReceiptRepository, never()).save(any());
	}

	// WhenPartnerDoesNotExist
	@Test
	public void copyDtoToEntity_ShouldThrowResourceNotFoundException_WhenPartnerDoesNotExist() {
		ScrapReceiptDTO dto = new ScrapReceiptDTO(scrapReceiptDTO.getId(), scrapReceiptDTO.getNumTicketId(),
				nonExistingPartnerId, scrapReceiptDTO.getPartnerName(), scrapReceiptDTO.getTransactionDescription(),
				scrapReceiptDTO.getCosts(), nonExistingInputId, scrapReceiptDTO.getInputDescription(),
				scrapReceiptDTO.getAmountScrap(), scrapReceiptDTO.getUnitValue(), scrapReceiptDTO.getTotalValue(),
				scrapReceiptDTO.getMetalYield(), scrapReceiptDTO.getMetalWeight(), scrapReceiptDTO.getSlag());

		// Ticket existe
		when(ticketRepository.findByNumTicket(existingTicketId)).thenReturn(Optional.of(new Ticket()));

		// Partner existe
		when(partnerRepository.findById(nonExistingPartnerId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> service.copyDtoToEntity(dto, scrapReceipt));
		verify(partnerRepository, times(1)).findById(nonExistingPartnerId);
		verify(scrapReceiptRepository, never()).sumAmountScrapByNumTicket(anyInt());
		verify(scrapReceiptRepository, never()).save(any());
	}

	// WhenTicketDoesNotExist
	@Test
	public void copyDtoToEntity_ShouldThrowResourceNotFoundException_WhenTicketDoesNotExist() {
		ScrapReceiptDTO dto = new ScrapReceiptDTO(scrapReceiptDTO.getId(), nonExistingTicketId, nonExistingPartnerId,
				scrapReceiptDTO.getPartnerName(), scrapReceiptDTO.getTransactionDescription(),
				scrapReceiptDTO.getCosts(), nonExistingInputId, scrapReceiptDTO.getInputDescription(),
				scrapReceiptDTO.getAmountScrap(), scrapReceiptDTO.getUnitValue(), scrapReceiptDTO.getTotalValue(),
				scrapReceiptDTO.getMetalYield(), scrapReceiptDTO.getMetalWeight(), scrapReceiptDTO.getSlag());

		// Ticket existe
		when(ticketRepository.findByNumTicket(nonExistingTicketId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> service.copyDtoToEntity(dto, scrapReceipt));
		verify(ticketRepository, times(1)).findByNumTicket(nonExistingTicketId);
		verify(scrapReceiptRepository, never()).sumAmountScrapByNumTicket(anyInt());
		verify(scrapReceiptRepository, never()).save(any());
	}

	// WhenCostsEnumExists
	@Test
	void copyDtoToEntity_ShouldSetValidCosts_WhenCostsEnumExists() {
		ScrapReceiptDTO dto = ScrapReceiptFactory.createScrapReceiptDTO();

		when(ticketRepository.findByNumTicket(anyInt())).thenReturn(Optional.of(new Ticket()));
		when(partnerRepository.findById(anyLong())).thenReturn(Optional.of(new Partner()));
		when(inputRepository.findById(anyLong())).thenReturn(Optional.of(new Input()));

		ScrapReceipt entity = new ScrapReceipt();

		service.copyDtoToEntity(dto, entity);

		assertEquals(TypeCosts.DIRECT_COSTS, entity.getCosts());
	}

	// WhenCostsEnumInvalid
	@Test
	void copyDtoToEntity_ShouldThrowResourceNotFoundException_WhenCostsEnumInvalid() {
		ScrapReceiptDTO dto = ScrapReceiptFactory.createScrapReceiptInvalidCostsDTO();

		when(ticketRepository.findByNumTicket(anyInt())).thenReturn(Optional.of(new Ticket()));

		ScrapReceipt entity = new ScrapReceipt();

		assertThrows(ResourceNotFoundException.class, () -> service.copyDtoToEntity(dto, entity));
	}

	// WhenEnumExists
	@Test
	void copyDtoToEntity_ShouldSetValidTransaction_WhenEnumExists() {
		ScrapReceiptDTO dto = ScrapReceiptFactory.createScrapReceiptDTO();

		when(ticketRepository.findByNumTicket(anyInt())).thenReturn(Optional.of(new Ticket()));
		when(partnerRepository.findById(anyLong())).thenReturn(Optional.of(new Partner()));
		when(inputRepository.findById(anyLong())).thenReturn(Optional.of(new Input()));

		ScrapReceipt entity = new ScrapReceipt();

		service.copyDtoToEntity(dto, entity);

		assertEquals(TypeTransactionReceipt.BUY, entity.getTransaction());
	}

	// WhenTransactionEnumInvalid
	@Test
	void copyDtoToEntity_ShouldThrowResourceNotFoundException_WhenTransactionEnumInvalid(){
		ScrapReceiptDTO dto = ScrapReceiptFactory.createScrapReceiptInvalidTranscationDTO();

		when(ticketRepository.findByNumTicket(anyInt())).thenReturn(Optional.of(new Ticket()));
		when(partnerRepository.findById(anyLong())).thenReturn(Optional.of(new Partner()));
		when(inputRepository.findById(anyLong())).thenReturn(Optional.of(new Input()));

		ScrapReceipt entity = new ScrapReceipt();

		assertThrows(ResourceNotFoundException.class, () -> service.copyDtoToEntity(dto, entity));
	}

}
