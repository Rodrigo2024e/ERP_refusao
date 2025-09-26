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

import com.smartprocessrefusao.erprefusao.dto.ProductDTO;
import com.smartprocessrefusao.erprefusao.dto.ReportProductDTO;
import com.smartprocessrefusao.erprefusao.entities.MaterialGroup;
import com.smartprocessrefusao.erprefusao.entities.Product;
import com.smartprocessrefusao.erprefusao.entities.TaxClassification;
import com.smartprocessrefusao.erprefusao.entities.Unit;
import com.smartprocessrefusao.erprefusao.projections.ProductReportProjection;
import com.smartprocessrefusao.erprefusao.repositories.MaterialGroupRepository;
import com.smartprocessrefusao.erprefusao.repositories.ProductRepository;
import com.smartprocessrefusao.erprefusao.repositories.TaxClassificationRepository;
import com.smartprocessrefusao.erprefusao.repositories.UnitRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.MatGroupFactory;
import com.smartprocessrefusao.erprefusao.tests.ProductFactory;
import com.smartprocessrefusao.erprefusao.tests.TaxClassificationFactory;
import com.smartprocessrefusao.erprefusao.tests.UnitFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

	@InjectMocks
	private ProductService productService;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private UnitRepository unitRepository;

	@Mock
	private TaxClassificationRepository taxRepository;

	@Mock
	private MaterialGroupRepository materialGroupRepository;

	private Product product;
	private ProductDTO productDTO;
	private Long productExistingId, productNonExistingId;
	private Unit unit;
	private TaxClassification tax;
	private MaterialGroup group;

	@BeforeEach
	void setUp() {
		productExistingId = 1L;
		productNonExistingId = 999L;

		productDTO = ProductFactory.createProductDTO();
		product = ProductFactory.createProduct();
		unit = UnitFactory.createUnit();
		tax = TaxClassificationFactory.createTaxClass();
		group = MatGroupFactory.createGroup();

	}

	// 1 - Report Product
	@Test
	void reportProductShouldReturnPagedReportDTO() {
		ProductReportProjection projection = Mockito.mock(ProductReportProjection.class);
		Page<ProductReportProjection> page = new PageImpl<>(List.of(projection));
		when(productRepository.searchProductByNameOrId(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(page);

		Page<ReportProductDTO> result = productService.reportProduct(6060, 1L, PageRequest.of(0, 10));

		Assertions.assertNotNull(result);
	}

	// 2 - FindById
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() {

		when(productRepository.findById(productExistingId)).thenReturn(Optional.of(product));

		ProductDTO result = productService.findById(productExistingId);
		assertNotNull(result);

		assertEquals("FINISHED_PRODUCTS", result.getTypeMaterial());
		assertEquals("TARUGO DE ALUMÍNIO", result.getDescription());
		assertEquals(6060, result.getAlloy());
		assertEquals(4, result.getBilletDiameter());
		assertEquals(6.0, result.getBilletLength());
	}

	// 3 - FindById-EntityNotFoundException
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		assertThrows(ResourceNotFoundException.class, () -> productService.findById(productNonExistingId));
	}

	// 4 - Insert Product
	@Test
	public void insertShouldReturnProductDTO() {

		when(unitRepository.findById(Mockito.any())).thenReturn(Optional.of(unit));
		when(taxRepository.findById(Mockito.any())).thenReturn(Optional.of(tax));
		when(materialGroupRepository.findById(Mockito.any())).thenReturn(Optional.of(group));
		when(productRepository.save(any())).thenReturn(product);

		ProductDTO result = productService.insert(productDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(6060, result.getAlloy());
		verify(productRepository).save(Mockito.any());
	}

	// 5 - Insert Type Material Invalid
	@Test
	void insertShouldThrowWhenTypeMaterialIsInvalid() {
		ProductDTO dto = ProductFactory.createTypeMaterialInvalid();

		assertThrows(ResourceNotFoundException.class, () -> {
			productService.insert(dto);
		});
	}

	// 6 - Insert Tax Classification Invalid
	@Test
	void copyDtoToEntityShoulNotFoundExceptionWhenTaxClassificationInvalid() {
		when(productRepository.findById(Mockito.any())).thenReturn(Optional.of(product));
		when(unitRepository.findById(Mockito.any())).thenReturn(Optional.of(unit));
		when(materialGroupRepository.findById(Mockito.any())).thenReturn(Optional.of(group));
		when(taxRepository.findById(1L)).thenReturn(Optional.empty());

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			productService.insert(productDTO);
		});
	}

	// 7 - Insert Group Material Invalid
	@Test

	void copyDtoToEntityShoulNotFoundExceptionWhenGroupMaterialInvalid() {
		when(productRepository.findById(Mockito.any())).thenReturn(Optional.of(product));
		when(unitRepository.findById(Mockito.any())).thenReturn(Optional.of(unit));
		when(taxRepository.findById(Mockito.any())).thenReturn(Optional.of(tax));
		when(materialGroupRepository.findById(999L)).thenReturn(Optional.empty());

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			productService.insert(productDTO);
		});
	}

	// 8 - Insert Unit Invalid
	@Test
	void copyDtoToEntityShoulNotFoundExceptionWhenUnitDTOInvalid() {
		when(productRepository.getReferenceById(1L)).thenReturn(product);
		when(productRepository.save(product)).thenReturn(product);
		when(unitRepository.findById(999L)).thenReturn(Optional.empty());
		when(taxRepository.findById(Mockito.any())).thenReturn(Optional.of(tax));
		when(materialGroupRepository.findById(Mockito.any())).thenReturn(Optional.of(group));

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			productService.insert(productDTO);
		});
	}

	// 9 - Update Product
	@Test
	public void updateShouldReturnProductDTOWhenIdExists() {
		when(productRepository.getReferenceById(1L)).thenReturn(product);
		when(unitRepository.findById(Mockito.any())).thenReturn(Optional.of(unit));
		when(taxRepository.findById(Mockito.any())).thenReturn(Optional.of(tax));
		when(materialGroupRepository.findById(Mockito.any())).thenReturn(Optional.of(group));
		when(productRepository.save(product)).thenReturn(product);

		ProductDTO result = productService.update(1L, productDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(6060, result.getAlloy());
	}

	// 10 - Update Product Invalid
	@Test
	public void updateShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist() {
		when(productRepository.getReferenceById(productNonExistingId)).thenThrow(EntityNotFoundException.class);

		assertThrows(ResourceNotFoundException.class, () -> productService.update(productNonExistingId, productDTO));
	}

	// 11 - Delete Id exists
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		when(productRepository.existsById(productExistingId)).thenReturn(true);

		assertDoesNotThrow(() -> productService.delete(productExistingId));
		verify(productRepository).deleteById(productExistingId);
	}

	// 12 - Delete Id Does Not exists
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		when(productRepository.existsById(productNonExistingId)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> productService.delete(productNonExistingId));
	}

	// 13 - Delete Id Dependent
	@Test
	public void deleteShouldThrowDataBaseExceptionWhenIdDependent() {
		when(productRepository.existsById(productExistingId)).thenReturn(true);

		doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(productExistingId);
		assertThrows(DatabaseException.class, () -> productService.delete(productExistingId));
	}
}
