package com.smartprocessrefusao.erprefusao.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
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

import com.smartprocessrefusao.erprefusao.dto.DispatchDTO;
import com.smartprocessrefusao.erprefusao.dto.DispatchReportDTO;
import com.smartprocessrefusao.erprefusao.entities.Dispatch;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.Ticket;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionOutGoing;

import com.smartprocessrefusao.erprefusao.repositories.DispatchRepository;
import com.smartprocessrefusao.erprefusao.repositories.MaterialRepository;
import com.smartprocessrefusao.erprefusao.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.repositories.TicketRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;


import jakarta.persistence.EntityNotFoundException;
/*
@ExtendWith(MockitoExtension.class)
class DispatchServiceTest {

	@InjectMocks
	private DispatchService service;

	@Mock
	private DispatchRepository dispatchRepository;

	@Mock
	private TicketRepository ticketRepository;

	@Mock
	private MaterialRepository materialRepository;

	@Mock
	private PartnerRepository partnerRepository;

	private Dispatch dispatch;
	private DispatchDTO dispatchDTO;
	private Ticket ticket;
	private Partner partner;
	private Long existingId;
	private Integer existingTicketId, nonExistingTicketId;

	@BeforeEach
	void setUp() {
		existingId = 1L;
		nonExistingTicketId = 9999;
		existingTicketId = 35280;
		ticket = DispatchFactory.createTicket();
		partner = DispatchFactory.createPartner();
		dispatch = DispatchFactory.createProductDispatch();
		dispatchDTO = DispatchFactory.createDispatchDTO();
	}

	@Test
	void reportDispatch_ShouldReturnPageOfDTO() {
		Pageable pageable = PageRequest.of(0, 10);
		DispatchProjection projection = DispatchFactory.createProjection();
		Page<DispatchProjection> page = new PageImpl<>(List.of(projection));

		when(dispatchRepository.searchProductDispatchByTicket(1, pageable)).thenReturn(page);

		Page<ReportDispatchDTO> result = service.reportDispatch(1, pageable);

		assertEquals(1, result.getTotalElements());
		assertEquals(projection.getPartnerName(), result.getContent().get(0).getPartnerName());
	}

	@Test
	void findById_ShouldThrowResourceNotFoundException_WhenEntityDoesNotExist() {
		when(dispatchRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
	}

	@Test
	void insert_ShouldSaveEntity_WhenValidData() {
		when(ticketRepository.findByTicket(ticket.getTicket())).thenReturn(Optional.of(ticket));
		when(dispatchRepository.sumAmountByTicket(ticket.getTicket())).thenReturn(BigDecimal.ZERO);
		when(dispatchRepository.save(any(Dispatch.class))).thenReturn(dispatch);
		when(partnerRepository.findById(anyLong())).thenReturn(Optional.of(partner));

		DispatchDTO result = service.insert(dispatchDTO);

		assertNotNull(result);
		assertEquals(dispatchDTO.getAmount(), result.getAmount());
		assertEquals(TypeTransactionOutGoing.SALE.getDescription(), result.getTransactionDescription());
		verify(dispatchRepository, times(1)).save(any(Dispatch.class));

	}

	@Test
	void insert_ShouldThrowException_WhenWeightExceeded() {
		when(ticketRepository.findByTicket(ticket.getTicket())).thenReturn(Optional.of(ticket));
		when(dispatchRepository.sumAmountByTicket(ticket.getTicket())).thenReturn(ticket.getNetWeight());

		assertThrows(IllegalArgumentException.class, () -> service.insert(dispatchDTO));
	}

	@Test
	public void insert_ShouldHandleNullSumAndPersist_WhenNoPreviousProductExists() {

		when(dispatchRepository.sumAmountByTicket(35280)).thenReturn(null);

		when(ticketRepository.findByTicket(35280)).thenReturn(Optional.of(ticket));
		when(partnerRepository.findById(anyLong())).thenReturn(Optional.of(partner));
		when(dispatchRepository.save(any(Dispatch.class))).thenReturn(dispatch);

		DispatchDTO result = service.insert(dispatchDTO);

		assertNotNull(result);
		assertEquals(dispatch.getId(), result.getId());
		verify(dispatchRepository, times(1)).sumAmountByTicket(dispatchDTO.getTicketId());
		verify(dispatchRepository, times(1)).save(any(Dispatch.class));
	}

	@Test
	public void insert_ShouldThrowResourceNotFoundException_WhenTicketDoesNotExist() {
		dispatchDTO = DispatchFactory.createProductDispatchInvalidTicketDTO();

		when(ticketRepository.findByTicket(9999)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> service.insert(dispatchDTO));
		verify(ticketRepository, times(1)).findByTicket(9999);
		verify(dispatchRepository, never()).sumAmountByTicket(anyInt());
		verify(dispatchRepository, never()).save(any());
	}

	@Test
	void update_ShouldSaveEntity_WhenValidData() {
		when(dispatchRepository.getReferenceById(1L)).thenReturn(dispatch);
		when(ticketRepository.findByTicket(ticket.getTicket())).thenReturn(Optional.of(ticket));
		when(dispatchRepository.sumAmountByTicketExcludingId(ticket.getTicket(), 1L)).thenReturn(BigDecimal.ZERO);
		when(dispatchRepository.save(any(Dispatch.class))).thenReturn(dispatch);
		when(partnerRepository.findById(anyLong())).thenReturn(Optional.of(partner));

		DispatchDTO result = service.update(1L, dispatchDTO);

		assertEquals(dispatchDTO.getAmount(), result.getAmount());
	}

	// --- update --- id found
	@Test
	public void update_ShouldPersistAndReturnUpdatedScrapReceiptDTO_WhenDataIsValid() {
		when(dispatchRepository.getReferenceById(existingId)).thenReturn(dispatch);
		when(dispatchRepository.save(any(Dispatch.class))).thenReturn(dispatch);
		when(ticketRepository.findByTicket(existingTicketId)).thenReturn(Optional.of(ticket));
		when(partnerRepository.findById(anyLong())).thenReturn(Optional.of(partner));
		when(dispatchRepository.sumAmountByTicketExcludingId(anyInt(), anyLong())).thenReturn(new BigDecimal("50.00"));

		DispatchDTO updatedDto = new DispatchDTO(dispatchDTO.getId(), dispatchDTO.getDate(), dispatchDTO.getTicketId(),
				dispatchDTO.getPartnerId(), dispatchDTO.getPartnerName(), dispatchDTO.getTransactionDescription(),
				dispatchDTO.getProductId(), dispatchDTO.getProductDescription(), new BigDecimal("80.00"), // Novo
				dispatchDTO.getUnitValue(), null);

		DispatchDTO result = service.update(existingId, updatedDto);

		assertNotNull(result);
		assertEquals(existingId, result.getId());
		verify(dispatchRepository, times(1)).getReferenceById(existingId);
		verify(dispatchRepository, times(1)).sumAmountByTicketExcludingId(updatedDto.getTicketId(), existingId);
		verify(dispatchRepository, times(1)).save(any(Dispatch.class));
	}

	// Peso de ticket excedido
	@Test
	public void update_ShouldThrowIllegalArgumentException_WhenAmountExceedsTicketWeight() {
		dispatchDTO = DispatchFactory.createProductDispatchAmountExceedsTicketWeightDTO();

		when(dispatchRepository.getReferenceById(existingId)).thenReturn(dispatch);
		when(dispatchRepository.sumAmountByTicketExcludingId(anyInt(), anyLong())).thenReturn(new BigDecimal("50.00"));
		when(ticketRepository.findByTicket(anyInt())).thenReturn(Optional.of(ticket));

		assertThrows(IllegalArgumentException.class, () -> service.update(existingId, dispatchDTO));
		verify(dispatchRepository, never()).save(any());
	}

	// WhenTicketDoesNotExist
	@Test
	public void update_ShouldThrowResourceNotFoundException_WhenTicketDoesNotExist() {
		dispatchDTO = DispatchFactory.createProductDispatchInvalidTicketDTO();

		when(ticketRepository.findByTicket(nonExistingTicketId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> service.update(existingId, dispatchDTO));
		verify(ticketRepository, times(1)).findByTicket(nonExistingTicketId);
		verify(dispatchRepository, never()).sumAmountByTicket(anyInt());
		verify(dispatchRepository, never()).save(any());
	}

	@Test
	void update_ShouldThrowResourceNotFound_WhenEntityDoesNotExist() {
		when(dispatchRepository.getReferenceById(1L)).thenThrow(EntityNotFoundException.class);

		assertThrows(ResourceNotFoundException.class, () -> service.update(1L, dispatchDTO));
	}

	@Test
	void delete_ShouldRemoveEntity_WhenExists() {
		when(dispatchRepository.existsById(1L)).thenReturn(true);

		service.delete(1L);

		verify(dispatchRepository).deleteById(1L);
	}

	@Test
	void delete_ShouldThrowResourceNotFound_WhenIdDoesNotExist() {
		when(dispatchRepository.existsById(1L)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> service.delete(1L));
	}

	@Test
	void delete_ShouldThrowDatabaseException_WhenIntegrityViolation() {
		when(dispatchRepository.existsById(1L)).thenReturn(true);
		doThrow(DataIntegrityViolationException.class).when(dispatchRepository).deleteById(1L);

		assertThrows(DatabaseException.class, () -> service.delete(1L));
	}

	@Test
	void copyDtoToEntity_ShouldCopyAllFields() {
		when(ticketRepository.findByTicket(ticket.getTicket())).thenReturn(Optional.of(ticket));
		when(partnerRepository.findById(partner.getId())).thenReturn(Optional.of(partner));

		Dispatch entity = new Dispatch();
		service.copyDtoToEntity(dispatchDTO, entity);

		assertEquals(dispatchDTO.getAmount(), entity.getAmount());
		assertEquals(dispatchDTO.getUnitValue(), entity.getUnitValue());
		assertNotNull(entity.getTotalValue());
		assertEquals(ticket, entity.getTicket());
		assertEquals(partner, entity.getPartner());
	}

	@Test
	void copyDtoToEntity_ShouldThrowResourceNotFoundException_WhenTransactionEnumInvalid() {
		DispatchDTO dto = DispatchFactory.createProductDispatchInvalidTransactionDTO();

		when(ticketRepository.findByTicket(anyInt())).thenReturn(Optional.of(new Ticket()));
		when(partnerRepository.findById(anyLong())).thenReturn(Optional.of(new Partner()));

		Dispatch entity = new Dispatch();

		assertThrows(ResourceNotFoundException.class, () -> service.copyDtoToEntity(dto, entity));
	}

	@Test
	public void copyDtoToEntity_ShouldThrowResourceNotFoundException_WhenPartnerDoesNotExist() {
		dispatchDTO = DispatchFactory.createProductDispatchInvalidPartnerDTO();

		when(ticketRepository.findByTicket(35280)).thenReturn(Optional.of(new Ticket()));
		when(partnerRepository.findById(999L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> service.copyDtoToEntity(dispatchDTO, dispatch));
		verify(partnerRepository, times(1)).findById(999L);
		verify(dispatchRepository, never()).sumAmountByTicket(anyInt());
		verify(dispatchRepository, never()).save(any());
	}

	@Test
	public void copyDtoToEntity_ShouldThrowResourceNotFoundException_WhenProductDoesNotExist() {
		dispatchDTO = DispatchFactory.createProductDispatchInvalidProductDTO();

		when(ticketRepository.findByTicket(35280)).thenReturn(Optional.of(new Ticket()));
		when(partnerRepository.findById(4L)).thenReturn(Optional.of(partner));

		assertThrows(ResourceNotFoundException.class, () -> service.copyDtoToEntity(dispatchDTO, dispatch));
		verify(dispatchRepository, never()).sumAmountByTicket(anyInt());
		verify(dispatchRepository, never()).save(any());
	}

	// WhenTicketDoesNotExist
	@Test
	public void copyDtoToEntity_ShouldThrowResourceNotFoundException_WhenTicketDoesNotExist() {
		dispatchDTO = DispatchFactory.createProductDispatchInvalidTicketDTO();

		// Ticket não existe
		when(ticketRepository.findByTicket(nonExistingTicketId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> service.copyDtoToEntity(dispatchDTO, dispatch));
		verify(ticketRepository, times(1)).findByTicket(nonExistingTicketId);
		verify(dispatchRepository, never()).sumAmountByTicket(anyInt());
		verify(dispatchRepository, never()).save(any());
	}
}
*/