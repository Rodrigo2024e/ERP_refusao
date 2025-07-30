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

import com.smartprocessrefusao.erprefusao.dto.MaterialDTO;
import com.smartprocessrefusao.erprefusao.dto.ReportMaterialDTO;
import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.entities.ProductGroup;
import com.smartprocessrefusao.erprefusao.entities.TaxClassification;
import com.smartprocessrefusao.erprefusao.entities.Unit;
import com.smartprocessrefusao.erprefusao.projections.ReportMaterialProjection;
import com.smartprocessrefusao.erprefusao.repositories.MaterialRepository;
import com.smartprocessrefusao.erprefusao.repositories.ProductGroupRepository;
import com.smartprocessrefusao.erprefusao.repositories.TaxClassificationRepository;
import com.smartprocessrefusao.erprefusao.repositories.UnitRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.MaterialFactory;
import com.smartprocessrefusao.erprefusao.tests.ProductGroupFactory;
import com.smartprocessrefusao.erprefusao.tests.TaxClassificationFactory;
import com.smartprocessrefusao.erprefusao.tests.UnitFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class MaterialServiceTest {

	@InjectMocks
	private MaterialService service;

	@Mock
	private MaterialRepository MaterialRepository;

	@Mock
	private UnitRepository unitRepository;

	@Mock
	private TaxClassificationRepository taxRepository;

	@Mock
	private ProductGroupRepository groupRepository;

	private Material Material;
	private MaterialDTO MaterialDTO;
	private Unit unit;
	private TaxClassification tax;
	private ProductGroup group;
	private Long MaterialExistingId, MaterialNonExistingId, dependentId;

	@BeforeEach
	void setUp() {
		MaterialExistingId = 1L;
		MaterialNonExistingId = 999L;
		dependentId = 3L;

		unit = UnitFactory.createUnit();
		tax = TaxClassificationFactory.createTaxClass();
		group = ProductGroupFactory.createGroup();
		MaterialDTO = MaterialFactory.createMaterialDTO();
		Material = MaterialFactory.createMaterial();

	}

	// 1 - Report Material
	@Test
	void reportMaterialShouldReturnPagedReportDTO() {
		ReportMaterialProjection projection = Mockito.mock(ReportMaterialProjection.class);
		Page<ReportMaterialProjection> page = new PageImpl<>(List.of(projection));
		when(MaterialRepository.searchMaterialByNameOrId(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(page);

		Page<ReportMaterialDTO> result = service.reportMaterial("Tarugo de alumínio", 1L, PageRequest.of(0, 10));

		Assertions.assertNotNull(result);
	}

	// 2 - FindById
	@Test
	public void findByIdShouldReturnMaterialDTOWhenIdExists() {

		when(MaterialRepository.findById(MaterialExistingId)).thenReturn(Optional.of(Material));

		MaterialDTO result = service.findById(MaterialExistingId);
		assertNotNull(result);
		assertEquals("Sucata de alumínio", result.getDescription());
		assertEquals("kg", result.getAcronym());
		assertEquals("Sucata de alumínio", result.getDescription());
		assertEquals(7602000, result.getNumber());
		assertEquals("Sucata de alumínio", result.getDescription_prodGroup());
	}

	// 3 - FindById-EntityNotFoundException
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		assertThrows(ResourceNotFoundException.class, () -> service.findById(MaterialNonExistingId));
	}

	// 4 - Insert Material
	@Test
	public void insertShouldReturnMaterialDTO() {

		when(unitRepository.findById(Mockito.any())).thenReturn(Optional.of(unit));
		when(taxRepository.findById(Mockito.any())).thenReturn(Optional.of(tax));
		when(groupRepository.findById(Mockito.any())).thenReturn(Optional.of(group));
		when(MaterialRepository.save(any())).thenReturn(Material);

		MaterialDTO result = service.insert(MaterialDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("Sucata de alumínio", result.getDescription());
		verify(MaterialRepository).save(Mockito.any());
	}

	// 5 - Insert Unit Invalid
	@Test
	void copyDtoToEntityShoulNotFoundExceptionWhenUnitDTOInvalid() {
		when(MaterialRepository.getReferenceById(1L)).thenReturn(Material);
		when(MaterialRepository.save(Material)).thenReturn(Material);
		when(unitRepository.findById(999L)).thenReturn(Optional.empty());
		when(taxRepository.findById(Mockito.any())).thenReturn(Optional.of(tax));
		when(groupRepository.findById(Mockito.any())).thenReturn(Optional.of(group));

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.insert(MaterialDTO);
		});
	}

	// 6 - Insert TaxClassification Invalid
	@Test
	void copyDtoToEntityShoulNotFoundExceptionWhenTaxClassificationDTOInvalid() {
		when(MaterialRepository.getReferenceById(1L)).thenReturn(Material);
		when(MaterialRepository.save(Material)).thenReturn(Material);
		when(unitRepository.findById(Mockito.any())).thenReturn(Optional.of(unit));
		when(taxRepository.findById(999L)).thenReturn(Optional.empty());
		when(groupRepository.findById(Mockito.any())).thenReturn(Optional.of(group));

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.insert(MaterialDTO);
		});
	}

	// 7 - Insert Material Group Invalid
	@Test
	void copyDtoToEntityShoulNotFoundExceptionWhenMaterialGroupDTOInvalid() {
		when(MaterialRepository.getReferenceById(1L)).thenReturn(Material);
		when(MaterialRepository.save(Material)).thenReturn(Material);
		when(unitRepository.findById(Mockito.any())).thenReturn(Optional.of(unit));
		when(taxRepository.findById(Mockito.any())).thenReturn(Optional.of(tax));
		when(groupRepository.findById(999L)).thenReturn(Optional.empty());

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.insert(MaterialDTO);
		});
	}

	// 8 - Update Material
	@Test
	public void updateShouldReturnMaterialDTOWhenIdExists() {
		when(MaterialRepository.getReferenceById(1L)).thenReturn(Material);
		when(MaterialRepository.save(Material)).thenReturn(Material);
		when(unitRepository.findById(Mockito.any())).thenReturn(Optional.of(unit));
		when(taxRepository.findById(Mockito.any())).thenReturn(Optional.of(tax));
		when(groupRepository.findById(Mockito.any())).thenReturn(Optional.of(group));

		MaterialDTO result = service.update(1L, MaterialDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("Sucata de alumínio", result.getDescription());
	}

	// 9 - Update Material Invalid
	@Test
	public void updateShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist() {
		when(MaterialRepository.getReferenceById(MaterialNonExistingId)).thenThrow(EntityNotFoundException.class);

		assertThrows(ResourceNotFoundException.class, () -> service.update(MaterialNonExistingId, MaterialDTO));
	}

	// 10 - Delete Id exists
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		when(MaterialRepository.existsById(MaterialExistingId)).thenReturn(true);

		assertDoesNotThrow(() -> service.delete(MaterialExistingId));
		verify(MaterialRepository).deleteById(MaterialExistingId);
	}

	// 11 - Delete Id Does Not exists
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		when(MaterialRepository.existsById(MaterialNonExistingId)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> service.delete(MaterialNonExistingId));
	}

	// 12 - Delete Id Dependent
	@Test
	public void deleteShouldThrowDataBaseExceptionWhenIdDependent() {
		when(MaterialRepository.existsById(dependentId)).thenReturn(true);

		doThrow(DataIntegrityViolationException.class).when(MaterialRepository).deleteById(dependentId);
		assertThrows(DatabaseException.class, () -> service.delete(dependentId));
	}
}
