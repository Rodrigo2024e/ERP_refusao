package com.smartprocessrefusao.erprefusao.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.smartprocessrefusao.erprefusao.dto.SupplierReceiptDTO;
import com.smartprocessrefusao.erprefusao.dto.ReportSupplierReceiptDTO;
import com.smartprocessrefusao.erprefusao.entities.Input;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.SupplierReceipt;
import com.smartprocessrefusao.erprefusao.enumerados.TypeCosts;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionReceipt;
import com.smartprocessrefusao.erprefusao.projections.SupplierReceiptProjection;
import com.smartprocessrefusao.erprefusao.repositories.InputRepository;
import com.smartprocessrefusao.erprefusao.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.repositories.SupplierReceiptRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.SupplierReceiptFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class SupplierReceiptServiceTest {

	@InjectMocks
	private SupplierReceiptService service;

	@Mock
	private SupplierReceiptRepository supplierReceiptRepository;

	@Mock
	private InputRepository inputRepository;

	@Mock
	private PartnerRepository partnerRepository;

	private SupplierReceipt supplierReceipt;
	private SupplierReceiptDTO supplierReceiptDTO;
	private Partner partner;
	private Input input;

	private final Long existingId = 1L;
	private final Long nonExistingId = 1000L;

	@BeforeEach
	void setUp() {
		partner = new Partner();
		partner.setId(1L);

		input = new Input();
		input.setId(1L);

		supplierReceipt = new SupplierReceipt();
		supplierReceipt.setId(existingId);
		supplierReceipt.setPartner(partner);
		supplierReceipt.setInput(input);

		supplierReceipt = new SupplierReceipt();
		supplierReceipt.setId(existingId);
		supplierReceipt.setPartner(partner);
		supplierReceipt.setInput(input);
		supplierReceipt.setAmountSupplier(BigDecimal.TEN);
		supplierReceipt.setUnitValue(BigDecimal.ONE);
		supplierReceipt.setTransaction(TypeTransactionReceipt.BUY);
		supplierReceipt.setCosts(TypeCosts.DIRECT_COSTS);
		supplierReceipt.setDateReceipt(LocalDate.now());

	}

	@Test
	void reportSupplierReceipt_ShouldReturnPageOfDTO() {
		Pageable pageable = PageRequest.of(0, 10);
		SupplierReceiptProjection projection = Mockito.mock(SupplierReceiptProjection.class);
		Page<SupplierReceiptProjection> page = new PageImpl<>(List.of(projection));

		when(supplierReceiptRepository.searchSupplierReceiptByinputId(existingId, pageable)).thenReturn(page);

		Page<ReportSupplierReceiptDTO> result = service.reportSupplierReceipt(existingId, pageable);

		assertNotNull(result);
		assertEquals(1, result.getContent().size());
		verify(supplierReceiptRepository, times(1)).searchSupplierReceiptByinputId(existingId, pageable);
	}

	@Test
	void findById_ShouldReturnDTO_WhenIdExists() {
		when(supplierReceiptRepository.findById(existingId)).thenReturn(Optional.of(supplierReceipt));

		SupplierReceiptDTO result = service.findById(existingId);

		assertNotNull(result);
		assertEquals(supplierReceipt.getId(), result.getId());
	}

	@Test
	void findById_ShouldThrowResourceNotFound_WhenIdDoesNotExist() {
		when(supplierReceiptRepository.findById(nonExistingId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> service.findById(nonExistingId));
	}

	@Test
	void insert_ShouldPersistAndReturnDTO() {
		supplierReceiptDTO = SupplierReceiptFactory.createDTO();
		when(supplierReceiptRepository.save(any())).thenReturn(supplierReceipt);
		when(partnerRepository.findById(anyLong())).thenReturn(Optional.of(partner));
		when(inputRepository.findById(anyLong())).thenReturn(Optional.of(input));

		SupplierReceiptDTO result = service.insert(supplierReceiptDTO);

		assertNotNull(result);
		verify(supplierReceiptRepository, times(1)).save(any());
	}

	@Test
	void update_ShouldUpdateAndReturnDTO_WhenIdExists() {
		supplierReceiptDTO = SupplierReceiptFactory.createDTO();
		
		when(supplierReceiptRepository.getReferenceById(existingId)).thenReturn(supplierReceipt);
		when(supplierReceiptRepository.save(any())).thenReturn(supplierReceipt);
		when(partnerRepository.findById(anyLong())).thenReturn(Optional.of(partner));
		when(inputRepository.findById(anyLong())).thenReturn(Optional.of(input));

		SupplierReceiptDTO result = service.update(existingId, supplierReceiptDTO);

		assertNotNull(result);
		verify(supplierReceiptRepository, times(1)).save(any());
	}

	@Test
	void update_ShouldThrowResourceNotFound_WhenIdDoesNotExist() {
		when(supplierReceiptRepository.getReferenceById(nonExistingId)).thenThrow(new EntityNotFoundException());

		assertThrows(ResourceNotFoundException.class, () -> service.update(nonExistingId, supplierReceiptDTO));
	}

	@Test
	void delete_ShouldDoNothing_WhenIdExists() {
		when(supplierReceiptRepository.existsById(existingId)).thenReturn(true);

		service.delete(existingId);

		verify(supplierReceiptRepository, times(1)).deleteById(existingId);
	}

	@Test
	void delete_ShouldThrowResourceNotFound_WhenIdDoesNotExist() {
		when(supplierReceiptRepository.existsById(nonExistingId)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> service.delete(nonExistingId));
	}

	@Test
	void delete_ShouldThrowDatabaseException_WhenIntegrityViolation() {
		when(supplierReceiptRepository.existsById(existingId)).thenReturn(true);
		doThrow(DataIntegrityViolationException.class).when(supplierReceiptRepository).deleteById(existingId);

		assertThrows(DatabaseException.class, () -> service.delete(existingId));
	}

	@Test
	void copyDtoToEntity_ShouldCopyAllFieldsCorrectly() {
		supplierReceiptDTO = SupplierReceiptFactory.createDTO();
		
		when(partnerRepository.findById(anyLong())).thenReturn(Optional.of(partner));
		when(inputRepository.findById(anyLong())).thenReturn(Optional.of(input));

		SupplierReceipt entity = new SupplierReceipt();
		service.copyDtoToEntity(supplierReceiptDTO, entity);

		assertEquals(supplierReceiptDTO.getAmountSupplier().multiply(supplierReceiptDTO.getUnitValue()),
				entity.getTotalValue());
		assertNotNull(entity.getTransaction());
		assertNotNull(entity.getCosts());
	}

	@Test
	void copyDtoToEntity_ShouldThrow_WhenTransactionInvalid() {
		SupplierReceiptDTO invalidDTO = SupplierReceiptFactory.createSupplierReceiptInvalidTransactionDTO(); // ❌ tipo
																												// inválido
		SupplierReceipt entity = new SupplierReceipt();

		assertThrows(ResourceNotFoundException.class, () -> service.copyDtoToEntity(invalidDTO, entity));
	}

	@Test
	void copyDtoToEntity_ShouldThrow_WhenCostsInvalid() {
		SupplierReceiptDTO invalidDTO = SupplierReceiptFactory.createSupplierReceiptInvalidCostsDTO(); // ❌ custo
																										// inválido
		SupplierReceipt entity = new SupplierReceipt();

		assertThrows(ResourceNotFoundException.class, () -> service.copyDtoToEntity(invalidDTO, entity));
	}

	@Test
	void copyDtoToEntity_ShouldThrow_WhenPartnerNotFound() {
		when(partnerRepository.findById(anyLong())).thenReturn(Optional.empty());
		SupplierReceiptDTO dto = SupplierReceiptFactory.createSupplierReceiptInValidPartnerDTO();
		SupplierReceipt entity = new SupplierReceipt();

		assertThrows(ResourceNotFoundException.class, () -> service.copyDtoToEntity(dto, entity));
	}

	@Test
	void copyDtoToEntity_ShouldThrow_WhenInputNotFound() {
		when(partnerRepository.findById(anyLong())).thenReturn(Optional.of(partner));
		when(inputRepository.findById(anyLong())).thenReturn(Optional.empty());

		SupplierReceiptDTO dto = SupplierReceiptFactory.createSupplierReceiptInvalidInputDTO();
		SupplierReceipt entity = new SupplierReceipt();

		assertThrows(ResourceNotFoundException.class, () -> service.copyDtoToEntity(dto, entity));
	}
}
