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

import com.smartprocessrefusao.erprefusao.dto.ProductDispatchDTO;
import com.smartprocessrefusao.erprefusao.dto.ProductDispatchReportDTO;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.Product;
import com.smartprocessrefusao.erprefusao.entities.ProductDispatch;
import com.smartprocessrefusao.erprefusao.entities.Ticket;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionOutGoing;
import com.smartprocessrefusao.erprefusao.projections.ProductDispatchReportProjection;
import com.smartprocessrefusao.erprefusao.repositories.InputRepository;
import com.smartprocessrefusao.erprefusao.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.repositories.ProductDispatchRepository;
import com.smartprocessrefusao.erprefusao.repositories.ProductRepository;
import com.smartprocessrefusao.erprefusao.repositories.TicketRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.ProductDispatchFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class ProductDispatchServiceTest {

	@InjectMocks
	private ProductDispatchService service;

	@Mock
	private ProductDispatchRepository productDispatchRepository;

	@Mock
	private TicketRepository ticketRepository;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private InputRepository inputRepository;

	@Mock
	private PartnerRepository partnerRepository;

	private ProductDispatch productDispatch;
	private ProductDispatchDTO productDispatchDTO;
	private Ticket ticket;
	private Partner partner;
	private Product product;
	private Long existingId;
	private Integer existingTicketId, nonExistingTicketId;

	@BeforeEach
	void setUp() {
		existingId = 1L;
		nonExistingTicketId = 9999;
		existingTicketId = 35000;
		ticket = ProductDispatchFactory.createTicket();
		partner = ProductDispatchFactory.createPartner();
		product = ProductDispatchFactory.createProduct();
		productDispatch = ProductDispatchFactory.createEntity(ticket, partner, product);
		productDispatchDTO = ProductDispatchFactory.createDTO();
	}

	@Test
	void reportDispatch_ShouldReturnPageOfDTO() {
		Pageable pageable = PageRequest.of(0, 10);
		ProductDispatchReportProjection projection = ProductDispatchFactory.createProjection();
		Page<ProductDispatchReportProjection> page = new PageImpl<>(List.of(projection));

		when(productDispatchRepository.searchProductDispatchByNumberTicket(1, pageable)).thenReturn(page);

		Page<ProductDispatchReportDTO> result = service.reportDispatch(1, pageable);

		assertEquals(1, result.getTotalElements());
		assertEquals(projection.getPartnerName(), result.getContent().get(0).getPartnerName());
	}

	@Test
	void findById_ShouldReturnDTO_WhenEntityExists() {
		when(productDispatchRepository.findById(1L)).thenReturn(Optional.of(productDispatch));

		ProductDispatchDTO result = service.findById(1L);

		assertEquals(productDispatch.getAmountProduct(), result.getAmountProduct());
	}

	@Test
	void findById_ShouldThrowResourceNotFoundException_WhenEntityDoesNotExist() {
		when(productDispatchRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
	}

	@Test
	void insert_ShouldSaveEntity_WhenValidData() {
		when(ticketRepository.findByNumTicket(ticket.getNumTicket())).thenReturn(Optional.of(ticket));
		when(productDispatchRepository.sumAmountProductByNumTicket(ticket.getNumTicket())).thenReturn(BigDecimal.ZERO);
		when(productDispatchRepository.save(any(ProductDispatch.class))).thenReturn(productDispatch);
		when(partnerRepository.findById(anyLong())).thenReturn(Optional.of(partner));
		when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

		ProductDispatchDTO result = service.insert(productDispatchDTO);

		assertNotNull(result);
		assertEquals(productDispatchDTO.getAmountProduct(), result.getAmountProduct());
		assertEquals(TypeTransactionOutGoing.SALE.getDescription(), result.getTransactionDescription());
		verify(productDispatchRepository, times(1)).save(any(ProductDispatch.class));

	}

	@Test
	void insert_ShouldThrowException_WhenWeightExceeded() {
		when(ticketRepository.findByNumTicket(ticket.getNumTicket())).thenReturn(Optional.of(ticket));
		when(productDispatchRepository.sumAmountProductByNumTicket(ticket.getNumTicket()))
				.thenReturn(ticket.getNetWeight());

		assertThrows(IllegalArgumentException.class, () -> service.insert(productDispatchDTO));
	}

	@Test
	public void insert_ShouldHandleNullSumAndPersist_WhenNoPreviousProductExists() {

		when(productDispatchRepository.sumAmountProductByNumTicket(35000)).thenReturn(null);

		when(ticketRepository.findByNumTicket(35000)).thenReturn(Optional.of(ticket));
		when(partnerRepository.findById(anyLong())).thenReturn(Optional.of(partner));
		when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
		when(productDispatchRepository.save(any(ProductDispatch.class))).thenReturn(productDispatch);

		ProductDispatchDTO result = service.insert(productDispatchDTO);

		assertNotNull(result);
		assertEquals(productDispatch.getId(), result.getId());
		verify(productDispatchRepository, times(1)).sumAmountProductByNumTicket(productDispatchDTO.getNumTicketId());
		verify(productDispatchRepository, times(1)).save(any(ProductDispatch.class));
	}

	@Test
	public void insert_ShouldThrowResourceNotFoundException_WhenTicketDoesNotExist() {
		productDispatchDTO = ProductDispatchFactory.createInvalidTicketDTO();

		when(ticketRepository.findByNumTicket(9999)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> service.insert(productDispatchDTO));
		verify(ticketRepository, times(1)).findByNumTicket(9999);
		verify(productDispatchRepository, never()).sumAmountProductByNumTicket(anyInt());
		verify(productDispatchRepository, never()).save(any());
	}

	@Test
	void update_ShouldSaveEntity_WhenValidData() {
		when(productDispatchRepository.getReferenceById(1L)).thenReturn(productDispatch);
		when(ticketRepository.findByNumTicket(ticket.getNumTicket())).thenReturn(Optional.of(ticket));
		when(productDispatchRepository.sumAmountProductByNumTicketExcludingId(ticket.getNumTicket(), 1L))
				.thenReturn(BigDecimal.ZERO);
		when(productDispatchRepository.save(any(ProductDispatch.class))).thenReturn(productDispatch);
		when(partnerRepository.findById(anyLong())).thenReturn(Optional.of(partner));
		when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

		ProductDispatchDTO result = service.update(1L, productDispatchDTO);

		assertEquals(productDispatchDTO.getAmountProduct(), result.getAmountProduct());
	}

	/////////
	// --- update --- id found
	@Test
	public void update_ShouldPersistAndReturnUpdatedScrapReceiptDTO_WhenDataIsValid() {
		when(productDispatchRepository.getReferenceById(existingId)).thenReturn(productDispatch);
		when(productDispatchRepository.save(any(ProductDispatch.class))).thenReturn(productDispatch);
		when(ticketRepository.findByNumTicket(existingTicketId)).thenReturn(Optional.of(ticket));
		when(partnerRepository.findById(anyLong())).thenReturn(Optional.of(partner));
		when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
		when(productDispatchRepository.sumAmountProductByNumTicketExcludingId(anyInt(), anyLong()))
				.thenReturn(new BigDecimal("50.00"));

		ProductDispatchDTO updatedDto = new ProductDispatchDTO(productDispatchDTO.getId(),
				productDispatchDTO.getNumTicketId(), productDispatchDTO.getPartnerId(),
				productDispatchDTO.getPartnerName(), productDispatchDTO.getTransactionDescription(),
				productDispatchDTO.getProductId(), productDispatchDTO.getProductDescription(),
				productDispatchDTO.getAlloy(), productDispatchDTO.getBilletDiameter(),
				productDispatchDTO.getBilletLength(), new BigDecimal("80.00"), // Novo
				productDispatchDTO.getUnitValue(), null);

		ProductDispatchDTO result = service.update(existingId, updatedDto);

		assertNotNull(result);
		assertEquals(existingId, result.getId());
		verify(productDispatchRepository, times(1)).getReferenceById(existingId);
		verify(productDispatchRepository, times(1)).sumAmountProductByNumTicketExcludingId(updatedDto.getNumTicketId(),
				existingId);
		verify(productDispatchRepository, times(1)).save(any(ProductDispatch.class));
	}

	// Peso de ticket excedido
	@Test
	public void update_ShouldThrowIllegalArgumentException_WhenAmountExceedsTicketWeight() {
		productDispatchDTO = ProductDispatchFactory.createAmountExceedsTicketWeightDTO();

		when(productDispatchRepository.getReferenceById(existingId)).thenReturn(productDispatch);
		when(productDispatchRepository.sumAmountProductByNumTicketExcludingId(anyInt(), anyLong()))
				.thenReturn(new BigDecimal("50.00"));
		when(ticketRepository.findByNumTicket(anyInt())).thenReturn(Optional.of(ticket));

		assertThrows(IllegalArgumentException.class, () -> service.update(existingId, productDispatchDTO));
		verify(productDispatchRepository, never()).save(any());
	}

	// WhenTicketDoesNotExist
	@Test
	public void update_ShouldThrowResourceNotFoundException_WhenTicketDoesNotExist() {
		productDispatchDTO = ProductDispatchFactory.createInvalidTicketDTO();

		when(ticketRepository.findByNumTicket(nonExistingTicketId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> service.update(existingId, productDispatchDTO));
		verify(ticketRepository, times(1)).findByNumTicket(nonExistingTicketId);
		verify(productDispatchRepository, never()).sumAmountProductByNumTicket(anyInt());
		verify(productDispatchRepository, never()).save(any());
	}

	@Test
	void update_ShouldThrowResourceNotFound_WhenEntityDoesNotExist() {
		when(productDispatchRepository.getReferenceById(1L)).thenThrow(EntityNotFoundException.class);

		assertThrows(ResourceNotFoundException.class, () -> service.update(1L, productDispatchDTO));
	}

	@Test
	void delete_ShouldRemoveEntity_WhenExists() {
		when(productDispatchRepository.existsById(1L)).thenReturn(true);

		service.delete(1L);

		verify(productDispatchRepository).deleteById(1L);
	}

	@Test
	void delete_ShouldThrowResourceNotFound_WhenIdDoesNotExist() {
		when(productDispatchRepository.existsById(1L)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> service.delete(1L));
	}

	@Test
	void delete_ShouldThrowDatabaseException_WhenIntegrityViolation() {
		when(productDispatchRepository.existsById(1L)).thenReturn(true);
		doThrow(DataIntegrityViolationException.class).when(productDispatchRepository).deleteById(1L);

		assertThrows(DatabaseException.class, () -> service.delete(1L));
	}

	@Test
	void copyDtoToEntity_ShouldCopyAllFields() {
		when(ticketRepository.findByNumTicket(ticket.getNumTicket())).thenReturn(Optional.of(ticket));
		when(partnerRepository.findById(partner.getId())).thenReturn(Optional.of(partner));
		when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

		ProductDispatch entity = new ProductDispatch();
		service.copyDtoToEntity(productDispatchDTO, entity);

		assertEquals(productDispatchDTO.getAmountProduct(), entity.getAmountProduct());
		assertEquals(productDispatchDTO.getUnitValue(), entity.getUnitValue());
		assertNotNull(entity.getTotalValue());
		assertEquals(ticket, entity.getNumTicket());
		assertEquals(partner, entity.getPartner());
		assertEquals(product, entity.getProduct());
	}

	@Test
	void copyDtoToEntity_ShouldThrowResourceNotFoundException_WhenTransactionEnumInvalid() {
		ProductDispatchDTO dto = ProductDispatchFactory.createInvalidTransactionDTO();

		when(ticketRepository.findByNumTicket(anyInt())).thenReturn(Optional.of(new Ticket()));
		when(partnerRepository.findById(anyLong())).thenReturn(Optional.of(new Partner()));
		when(productRepository.findById(anyLong())).thenReturn(Optional.of(new Product()));

		ProductDispatch entity = new ProductDispatch();

		assertThrows(ResourceNotFoundException.class, () -> service.copyDtoToEntity(dto, entity));
	}

	@Test
	public void copyDtoToEntity_ShouldThrowResourceNotFoundException_WhenPartnerDoesNotExist() {
		productDispatchDTO = ProductDispatchFactory.createInvalidPartnerDTO();

		when(ticketRepository.findByNumTicket(35000)).thenReturn(Optional.of(new Ticket()));
		when(partnerRepository.findById(999L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class,
				() -> service.copyDtoToEntity(productDispatchDTO, productDispatch));
		verify(partnerRepository, times(1)).findById(999L);
		verify(productDispatchRepository, never()).sumAmountProductByNumTicket(anyInt());
		verify(productDispatchRepository, never()).save(any());
	}

	@Test
	public void copyDtoToEntity_ShouldThrowResourceNotFoundException_WhenProductDoesNotExist() {
		productDispatchDTO = ProductDispatchFactory.createInvalidProductDTO();

		when(ticketRepository.findByNumTicket(35000)).thenReturn(Optional.of(new Ticket()));
		when(partnerRepository.findById(1L)).thenReturn(Optional.of(partner));
		when(productRepository.findById(999L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class,
				() -> service.copyDtoToEntity(productDispatchDTO, productDispatch));
		verify(productRepository, times(1)).findById(999L);
		verify(productDispatchRepository, never()).sumAmountProductByNumTicket(anyInt());
		verify(productDispatchRepository, never()).save(any());
	}
	
	// WhenTicketDoesNotExist
	@Test
	public void copyDtoToEntity_ShouldThrowResourceNotFoundException_WhenTicketDoesNotExist() {
		productDispatchDTO = ProductDispatchFactory.createInvalidTicketDTO();
		
		// Ticket não existe
		when(ticketRepository.findByNumTicket(nonExistingTicketId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> service.copyDtoToEntity(productDispatchDTO, productDispatch));
		verify(ticketRepository, times(1)).findByNumTicket(nonExistingTicketId);
		verify(productDispatchRepository, never()).sumAmountProductByNumTicket(anyInt());
		verify(productDispatchRepository, never()).save(any());
	}
}
