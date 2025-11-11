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
import com.smartprocessrefusao.erprefusao.entities.MaterialGroup;
import com.smartprocessrefusao.erprefusao.entities.TaxClassification;
import com.smartprocessrefusao.erprefusao.entities.Unit;
import com.smartprocessrefusao.erprefusao.projections.MaterialReportProjection;
import com.smartprocessrefusao.erprefusao.repositories.MaterialGroupRepository;
import com.smartprocessrefusao.erprefusao.repositories.MaterialRepository;
import com.smartprocessrefusao.erprefusao.repositories.TaxClassificationRepository;
import com.smartprocessrefusao.erprefusao.repositories.UnitRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.MatGroupFactory;
import com.smartprocessrefusao.erprefusao.tests.MaterialFactory;
import com.smartprocessrefusao.erprefusao.tests.TaxClassificationFactory;
import com.smartprocessrefusao.erprefusao.tests.UnitFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class MaterialServiceTest {

	@InjectMocks
	private MaterialService materialService;

	@Mock
	private MaterialRepository materialRepository;

	@Mock
	private UnitRepository unitRepository;

	@Mock
	private TaxClassificationRepository taxRepository;

	@Mock
	private MaterialGroupRepository materialGroupRepository;

	private Material material;
	private MaterialDTO materialDTO;
	private Unit unit;
	private TaxClassification tax;
	private MaterialGroup group;
	private Long  materialExistingId, materialNonExistingId, dependentId;
	private Long materialExistingCode, materialNonExistingCode;

	@BeforeEach
	void setUp() {
		materialExistingId = 1L;
		materialNonExistingId = 99L;
		materialExistingCode = 1111L;
		materialNonExistingCode= 9999L;
		dependentId = 3L;

		unit = UnitFactory.createUnit();
		tax = TaxClassificationFactory.createTaxClass();
		group = MatGroupFactory.createGroup();
		materialDTO = MaterialFactory.createMaterialDTO();
		material = MaterialFactory.createMaterial();

	}

	// 1 - Report Input
	@Test
	void reportMaterialShouldReturnPagedReportDTO() {
		MaterialReportProjection projection = Mockito.mock(MaterialReportProjection.class);
		Page<MaterialReportProjection> page = new PageImpl<>(List.of(projection));
		when(materialRepository.searchMaterialByNameOrGroup(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(page);

		Page<ReportMaterialDTO> result = materialService.reportMaterial("SCRAP", 1111L, "Perfil de processo", 1L, PageRequest.of(0, 10));

		Assertions.assertNotNull(result);
	}

	// 2 - FindById
	@Test
	public void findByIdShouldReturnMaterialDTOWhenIdExists() {

		when(materialRepository.findByCode(materialExistingCode)).thenReturn(Optional.of(material));

		MaterialDTO result = materialService.findByCode(materialExistingCode);
		assertNotNull(result);
		assertEquals("SCRAP", result.getType());
		assertEquals("PERFIL DE PROCESSO", result.getDescription());
		assertEquals("kg", result.getAcronym());
		assertEquals("SUCATA DE ALUMINIO", result.getDescription_taxclass());
		assertEquals(7602000, result.getNcmCode());
		assertEquals("SUCATA DE ALUMINIO", result.getDescription_matGroup());
	}

	// 3 - FindById-EntityNotFoundException
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		assertThrows(ResourceNotFoundException.class, () -> materialService.findByCode(materialNonExistingCode));
	}

	// 4 - Insert Input
	@Test
	public void insertShouldReturnInputDTO() {

		when(unitRepository.findById(Mockito.any())).thenReturn(Optional.of(unit));
		when(taxRepository.findById(Mockito.any())).thenReturn(Optional.of(tax));
		when(materialGroupRepository.findById(Mockito.any())).thenReturn(Optional.of(group));
		when(materialRepository.save(any())).thenReturn(material);

		MaterialDTO result = materialService.insert(materialDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("PERFIL DE PROCESSO", result.getDescription());
		verify(materialRepository).save(Mockito.any());
	}

	// 5 - Insert Type Input Invalid
	@Test
	void insertShouldThrowWhenTypeInputIsInvalid() {
		MaterialDTO dto = MaterialFactory.createTypeMaterialInvalid();

		assertThrows(ResourceNotFoundException.class, () -> {
			materialService.insert(dto);
		});
	}

	// 6 - Insert Unit Invalid
	@Test
	void copyDtoToEntityShoulNotFoundExceptionWhenUnitDTOInvalid() {
		when(materialRepository.getReferenceById(1L)).thenReturn(material);
		when(materialRepository.save(material)).thenReturn(material);
		when(unitRepository.findById(999L)).thenReturn(Optional.empty());
		when(taxRepository.findById(Mockito.any())).thenReturn(Optional.of(tax));
		when(materialGroupRepository.findById(Mockito.any())).thenReturn(Optional.of(group));

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			materialService.insert(materialDTO);
		});
	}

	// 7 - Insert TaxClassification Invalid
	@Test
	void copyDtoToEntityShoulNotFoundExceptionWhenTaxClassificationDTOInvalid() {
		when(materialRepository.getReferenceById(1L)).thenReturn(material);
		when(materialRepository.save(material)).thenReturn(material);
		when(unitRepository.findById(Mockito.any())).thenReturn(Optional.of(unit));
		when(taxRepository.findById(999L)).thenReturn(Optional.empty());
		when(materialGroupRepository.findById(Mockito.any())).thenReturn(Optional.of(group));

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			materialService.insert(materialDTO);
		});
	}

	// 8 - Insert Input Group Invalid
	@Test
	void copyDtoToEntityShoulNotFoundExceptionWhenMaterialGroupDTOInvalid() {
		when(materialRepository.getReferenceById(1L)).thenReturn(material);
		when(unitRepository.findById(Mockito.any())).thenReturn(Optional.of(unit));
		when(taxRepository.findById(Mockito.any())).thenReturn(Optional.of(tax));
		when(materialGroupRepository.findById(999L)).thenReturn(Optional.empty());
		when(materialRepository.save(material)).thenReturn(material);
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			materialService.insert(materialDTO);
		});
	}

	// 9 - Update Input
	@Test
	public void updateShouldReturnMaterialDTOWhenIdExists() {
		when(materialRepository.getReferenceById(1L)).thenReturn(material);
		when(materialRepository.save(material)).thenReturn(material);
		when(unitRepository.findById(Mockito.any())).thenReturn(Optional.of(unit));
		when(taxRepository.findById(Mockito.any())).thenReturn(Optional.of(tax));
		when(materialGroupRepository.findById(Mockito.any())).thenReturn(Optional.of(group));

		MaterialDTO result = materialService.update(1111L, materialDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("PERFIL DE PROCESSO", result.getDescription());
	}

	// 10 - Update Input Invalid
	@Test
	public void updateShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist() {
		when(materialRepository.getReferenceById(materialNonExistingId)).thenThrow(EntityNotFoundException.class);

		assertThrows(ResourceNotFoundException.class, () -> materialService.update(materialNonExistingCode, materialDTO));
	}

	// 11 - Delete Id exists
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		when(materialRepository.existsById(materialExistingId)).thenReturn(true);

		assertDoesNotThrow(() -> materialService.delete(materialExistingId));
		verify(materialRepository).deleteById(materialExistingId);
	}

	// 12 - Delete Id Does Not exists
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		when(materialRepository.existsById(materialNonExistingId)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> materialService.delete(materialNonExistingId));
	}

	// 13 - Delete Id Dependent
	@Test
	public void deleteShouldThrowDataBaseExceptionWhenIdDependent() {
		when(materialRepository.existsById(dependentId)).thenReturn(true);

		doThrow(DataIntegrityViolationException.class).when(materialRepository).deleteById(dependentId);
		assertThrows(DatabaseException.class, () -> materialService.delete(dependentId));
	}

}
