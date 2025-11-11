package com.smartprocessrefusao.erprefusao.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

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

import com.smartprocessrefusao.erprefusao.dto.ReportReceiptDTO;

import com.smartprocessrefusao.erprefusao.entities.Ticket;
import com.smartprocessrefusao.erprefusao.projections.ReportReceiptProjection;
import com.smartprocessrefusao.erprefusao.repositories.TicketRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

/*
@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

	@Mock
	private TicketRepository ticketRepository;

	@InjectMocks
	private TicketService ticketService;


	@Test
	void findAllPaged_ShouldReturnPageOfTickets() {
		Pageable pageable = PageRequest.of(0, 10);
		Ticket ticket = TicketFactory.createTicket();
		Page<Ticket> page = new PageImpl<>(List.of(ticket));

		when(ticketRepository.findAll(pageable)).thenReturn(page);

		Page<TicketDTO> result = ticketService.findAllPaged(pageable);

		assertThat(result).isNotEmpty();
		assertThat(result.getContent().get(0).getTicket()).isEqualTo(ticket.getTicket());
		verify(ticketRepository).findAll(pageable);
	}

	@Test
	void reportTicket_ShouldReturnPageOfTicketReportDTO() {
		Pageable pageable = PageRequest.of(0, 10);
		ReportReceiptTicketProjection projection = TicketFactory.createTicketProjection();
		Page<ReportReceiptTicketProjection> page = new PageImpl<>(List.of(projection));

		when(ticketRepository.searchTicketWithReceipt(eq(1), eq(pageable))).thenReturn(page);

		Page<ReportTicketDTO> result = ticketService.reportTicket(1, pageable);

		assertThat(result).isNotEmpty();
		assertThat(result.getContent().get(0).getDateTicket().isEqual(projection.getDateTicket()));
	}

	@Test
	void findById_WhenExists_ShouldReturnTicket() {
		Ticket ticket = TicketFactory.createTicket();
		when(ticketRepository.findByTicket(ticket.getTicket())).thenReturn(Optional.of(ticket));

		TicketDTO result = ticketService.findById(ticket.getTicket());

		assertThat(result.getTicket()).isEqualTo(ticket.getTicket());
	}

	@Test
	void findById_WhenNotExists_ShouldThrowResourceNotFound() {
		when(ticketRepository.findByTicket(99)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> ticketService.findById(99)).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void insert_WhenNewTicket_ShouldSaveAndReturnDTO() {
		TicketDTO dto = TicketFactory.createTicketDTO();
		Ticket entity = TicketFactory.createTicket();

		when(ticketRepository.findByTicket(dto.getTicket())).thenReturn(Optional.empty());
		when(ticketRepository.save(any(Ticket.class))).thenReturn(entity);

		TicketDTO result = ticketService.insert(dto);

		assertThat(result.getTicket()).isEqualTo(dto.getTicket());
		verify(ticketRepository).save(any(Ticket.class));
	}

	@Test
	void insert_WhenTicketExists_ShouldThrowDatabaseException() {
		TicketDTO dto = TicketFactory.createTicketDTO();
		Ticket entity = TicketFactory.createTicket();

		when(ticketRepository.findByTicket(dto.getTicket())).thenReturn(Optional.of(entity));

		assertThatThrownBy(() -> ticketService.insert(dto)).isInstanceOf(DatabaseException.class);
	}

	@Test
	void update_WhenExists_ShouldUpdateAndReturnDTO() {
		Ticket ticket = TicketFactory.createTicket();
		TicketDTO dto = TicketFactory.createTicketDTO();

		when(ticketRepository.findByTicket(ticket.getTicket())).thenReturn(Optional.of(ticket));
		when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

		TicketDTO result = ticketService.update(ticket.getTicket(), dto);

		assertThat(result.getTicket()).isEqualTo(ticket.getTicket());
	}

	@Test
	void update_WhenNotExists_ShouldThrowResourceNotFound() {
		TicketDTO dto = TicketFactory.createTicketDTO();
		when(ticketRepository.findByTicket(anyInt())).thenReturn(Optional.empty());
		assertThatThrownBy(() -> ticketService.update(999, dto)).isInstanceOf(ResourceNotFoundException.class);
	}
	
	@Test
	void delete_WhenExists_ShouldCallDeleteById() {
		when(ticketRepository.existsById(1)).thenReturn(true);
		doNothing().when(ticketRepository).deleteById(1);

		ticketService.delete(1);

		verify(ticketRepository).deleteById(1);
	}

	@Test
	void delete_WhenNotExists_ShouldThrowResourceNotFound() {
		when(ticketRepository.existsById(1)).thenReturn(false);

		assertThatThrownBy(() -> ticketService.delete(1)).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void delete_WhenIntegrityViolation_ShouldThrowDatabaseException() {
		when(ticketRepository.existsById(1)).thenReturn(true);
		doThrow(new DataIntegrityViolationException("violation")).when(ticketRepository).deleteById(1);

		assertThatThrownBy(() -> ticketService.delete(1)).isInstanceOf(DatabaseException.class);
	}
}
*/