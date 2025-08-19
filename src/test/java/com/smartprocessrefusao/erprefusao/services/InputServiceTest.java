package com.smartprocessrefusao.erprefusao.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.smartprocessrefusao.erprefusao.dto.InputDTO;
import com.smartprocessrefusao.erprefusao.dto.ReportInputDTO;
import com.smartprocessrefusao.erprefusao.entities.Input;
import com.smartprocessrefusao.erprefusao.entities.MaterialGroup;
import com.smartprocessrefusao.erprefusao.entities.TaxClassification;
import com.smartprocessrefusao.erprefusao.entities.Unit;
import com.smartprocessrefusao.erprefusao.projections.ReportInputProjection;
import com.smartprocessrefusao.erprefusao.repositories.InputRepository;
import com.smartprocessrefusao.erprefusao.repositories.MaterialGroupRepository;
import com.smartprocessrefusao.erprefusao.repositories.TaxClassificationRepository;
import com.smartprocessrefusao.erprefusao.repositories.UnitRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.InputFactory;
import com.smartprocessrefusao.erprefusao.tests.MatGroupFactory;
import com.smartprocessrefusao.erprefusao.tests.TaxClassificationFactory;
import com.smartprocessrefusao.erprefusao.tests.UnitFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class InputServiceTest {

	@InjectMocks
	private InputService service;

	@Mock
	private InputRepository MaterialRepository;

	@Mock
	private UnitRepository unitRepository;

	@Mock
	private TaxClassificationRepository taxRepository;

	@Mock
	private MaterialGroupRepository groupRepository;

	private Input input;
	private InputDTO inputDTO;
	private Unit unit;
	private TaxClassification tax;
	private MaterialGroup group;
	private Long inputExistingId, inputNonExistingId, dependentId;

	@BeforeEach
	void setUp() {
		inputExistingId = 1L;
		inputNonExistingId = 999L;
		dependentId = 3L;

		unit = UnitFactory.createUnit();
		tax = TaxClassificationFactory.createTaxClass();
		group = MatGroupFactory.createGroup();
		inputDTO = InputFactory.createInputDTO();
		input = InputFactory.createInput();

	}

	// 1 - Report Input
	@Test
	void reportMaterialShouldReturnPagedReportDTO() {
		ReportInputProjection projection = Mockito.mock(ReportInputProjection.class);
		Page<ReportInputProjection> page = new PageImpl<>(List.of(projection));
		when(MaterialRepository.searchMaterialByNameOrGroup(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(page);

		Page<ReportInputDTO> result = service.reportInput("Perfil de processo", 1L, PageRequest.of(0, 10));

		Assertions.assertNotNull(result);
	}

	// 2 - FindById
	@Test
	public void findByIdShouldReturnMaterialDTOWhenIdExists() {

		when(MaterialRepository.findById(inputExistingId)).thenReturn(Optional.of(input));

		InputDTO result = service.findById(inputExistingId);
		assertNotNull(result);
		assertEquals("SCRAP", result.getTypeMaterial());
		assertEquals("PERFIL DE PROCESSO", result.getDescription());
		assertEquals("kg", result.getAcronym());
		assertEquals("SUCATA DE ALUMINIO", result.getDescription_taxclass());
		assertEquals(7602000, result.getNumber());
		assertEquals("SUCATA DE ALUMINIO", result.getDescription_matGroup());
	}

	// 3 - FindById-EntityNotFoundException
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		assertThrows(ResourceNotFoundException.class, () -> service.findById(inputNonExistingId));
	}

	// 4 - Insert Material
	@Test
	public void insertShouldReturnMaterialDTO() {

		when(unitRepository.findById(Mockito.any())).thenReturn(Optional.of(unit));
		when(taxRepository.findById(Mockito.any())).thenReturn(Optional.of(tax));
		when(groupRepository.findById(Mockito.any())).thenReturn(Optional.of(group));
		when(MaterialRepository.save(any())).thenReturn(input);

		InputDTO result = service.insert(inputDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("PERFIL DE PROCESSO", result.getDescription());
		verify(MaterialRepository).save(Mockito.any());
	}

	// 5 - Insert Type Material Invalid
	@Test
	void insertShouldThrowWhenTypeMaterialIsInvalid() {
		InputDTO dto = InputFactory.createTypeMaterialInvalid();

		assertThrows(ResourceNotFoundException.class, () -> {
			service.insert(dto);
		});
	}

	// 6 - Insert Unit Invalid
	@Test
	void copyDtoToEntityShoulNotFoundExceptionWhenUnitDTOInvalid() {
		when(MaterialRepository.getReferenceById(1L)).thenReturn(input);
		when(MaterialRepository.save(input)).thenReturn(input);
		when(unitRepository.findById(999L)).thenReturn(Optional.empty());
		when(taxRepository.findById(Mockito.any())).thenReturn(Optional.of(tax));
		when(groupRepository.findById(Mockito.any())).thenReturn(Optional.of(group));

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.insert(inputDTO);
		});
	}

	// 7 - Insert TaxClassification Invalid
	@Test
	void copyDtoToEntityShoulNotFoundExceptionWhenTaxClassificationDTOInvalid() {
		when(MaterialRepository.getReferenceById(1L)).thenReturn(input);
		when(MaterialRepository.save(input)).thenReturn(input);
		when(unitRepository.findById(Mockito.any())).thenReturn(Optional.of(unit));
		when(taxRepository.findById(999L)).thenReturn(Optional.empty());
		when(groupRepository.findById(Mockito.any())).thenReturn(Optional.of(group));

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.insert(inputDTO);
		});
	}

	// 8 - Insert Material Group Invalid
	@Test
	void copyDtoToEntityShoulNotFoundExceptionWhenMaterialGroupDTOInvalid() {
		when(MaterialRepository.getReferenceById(1L)).thenReturn(input);
		when(MaterialRepository.save(input)).thenReturn(input);
		when(unitRepository.findById(Mockito.any())).thenReturn(Optional.of(unit));
		when(taxRepository.findById(Mockito.any())).thenReturn(Optional.of(tax));
		when(groupRepository.findById(999L)).thenReturn(Optional.empty());

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.insert(inputDTO);
		});
	}

	// 9 - Update Material
	@Test
	public void updateShouldReturnMaterialDTOWhenIdExists() {
		when(MaterialRepository.getReferenceById(1L)).thenReturn(input);
		when(MaterialRepository.save(input)).thenReturn(input);
		when(unitRepository.findById(Mockito.any())).thenReturn(Optional.of(unit));
		when(taxRepository.findById(Mockito.any())).thenReturn(Optional.of(tax));
		when(groupRepository.findById(Mockito.any())).thenReturn(Optional.of(group));

		InputDTO result = service.update(1L, inputDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("PERFIL DE PROCESSO", result.getDescription());
	}

	// 10 - Update Material Invalid
	@Test
	public void updateShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist() {
		when(MaterialRepository.getReferenceById(inputNonExistingId)).thenThrow(EntityNotFoundException.class);

		assertThrows(ResourceNotFoundException.class, () -> service.update(inputNonExistingId, inputDTO));
	}

	// 11 - Delete Id exists
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		when(MaterialRepository.existsById(inputExistingId)).thenReturn(true);

		assertDoesNotThrow(() -> service.delete(inputExistingId));
		verify(MaterialRepository).deleteById(inputExistingId);
	}

	// 12 - Delete Id Does Not exists
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		when(MaterialRepository.existsById(inputNonExistingId)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> service.delete(inputNonExistingId));
	}

	// 13 - Delete Id Dependent
	@Test
	public void deleteShouldThrowDataBaseExceptionWhenIdDependent() {
		when(MaterialRepository.existsById(dependentId)).thenReturn(true);

		doThrow(DataIntegrityViolationException.class).when(MaterialRepository).deleteById(dependentId);
		assertThrows(DatabaseException.class, () -> service.delete(dependentId));
	}
}
