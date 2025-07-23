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
import com.smartprocessrefusao.erprefusao.entities.Product;
import com.smartprocessrefusao.erprefusao.entities.ProductGroup;
import com.smartprocessrefusao.erprefusao.entities.TaxClassification;
import com.smartprocessrefusao.erprefusao.entities.Unit;
import com.smartprocessrefusao.erprefusao.projections.ReportProductProjection;
import com.smartprocessrefusao.erprefusao.repositories.ProductGroupRepository;
import com.smartprocessrefusao.erprefusao.repositories.ProductRepository;
import com.smartprocessrefusao.erprefusao.repositories.TaxClassificationRepository;
import com.smartprocessrefusao.erprefusao.repositories.UnitRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.ProductFactory;
import com.smartprocessrefusao.erprefusao.tests.ProductGroupFactory;
import com.smartprocessrefusao.erprefusao.tests.TaxClassificationFactory;
import com.smartprocessrefusao.erprefusao.tests.UnitFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private UnitRepository unitRepository;

	@Mock
	private TaxClassificationRepository taxRepository;

	@Mock
	private ProductGroupRepository groupRepository;

	private Product product;
	private ProductDTO productDTO;
	private Unit unit;
	private TaxClassification tax;
	private ProductGroup group;
	private Long ProductExistingId, ProductNonExistingId;

	@BeforeEach
	void setUp() {
		ProductExistingId = 1L;
		ProductNonExistingId = 999L;

		unit = UnitFactory.createUnit();
		tax = TaxClassificationFactory.createTaxClass();
		group = ProductGroupFactory.createGroup();
		productDTO = ProductFactory.createProductDTO();
		product = ProductFactory.createProduct();

	}

	// 1 - Report Product
	@Test
	void reportProductShouldReturnPagedReportDTO() {
		ReportProductProjection projection = Mockito.mock(ReportProductProjection.class);
		Page<ReportProductProjection> page = new PageImpl<>(List.of(projection));
		when(productRepository.searchProductByNameOrId(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(page);

		Page<ReportProductDTO> result = service.reportProduct("Tarugo de alumínio", 1L, PageRequest.of(0, 10));

		Assertions.assertNotNull(result);
	}

	// 2 - FindById
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() {

		when(productRepository.findById(ProductExistingId)).thenReturn(Optional.of(product));

		ProductDTO result = service.findById(ProductExistingId);
		assertNotNull(result);
		assertEquals("Tarugo de alumínio", result.getDescription());
		assertEquals("kg", result.getAcronym());
		assertEquals("Tarugo de alumínio", result.getTaxClass());
		assertEquals("7604000", result.getNumber());
		assertEquals("Produto acabado", result.getProdGroup());
	}

	// 3 - FindById-EntityNotFoundException
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		assertThrows(ResourceNotFoundException.class, () -> service.findById(ProductNonExistingId));
	}

	// 4 - Insert Product
	@Test
	public void insertShouldReturnProductDTO() {

		when(unitRepository.findById(Mockito.any())).thenReturn(Optional.of(unit));
		when(taxRepository.findById(Mockito.any())).thenReturn(Optional.of(tax));
		when(groupRepository.findById(Mockito.any())).thenReturn(Optional.of(group));
		when(productRepository.save(any())).thenReturn(product);

		ProductDTO result = service.insert(productDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("Tarugo de alumínio", result.getDescription());
		verify(productRepository).save(Mockito.any());
	}

	// 5 - Insert Unit Invalid
	@Test
	void copyDtoToEntityShoulNotFoundExceptionWhenUnitDTOInvalid() {
		when(productRepository.getReferenceById(1L)).thenReturn(product);
		when(productRepository.save(product)).thenReturn(product);
		when(unitRepository.findById(999L)).thenReturn(Optional.empty());
		when(taxRepository.findById(Mockito.any())).thenReturn(Optional.of(tax));
		when(groupRepository.findById(Mockito.any())).thenReturn(Optional.of(group));

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.insert(productDTO);
		});
	}

	// 6 - Insert TaxClassification Invalid
	@Test
	void copyDtoToEntityShoulNotFoundExceptionWhenTaxClassificationDTOInvalid() {
		when(productRepository.getReferenceById(1L)).thenReturn(product);
		when(productRepository.save(product)).thenReturn(product);
		when(unitRepository.findById(Mockito.any())).thenReturn(Optional.of(unit));
		when(taxRepository.findById(999L)).thenReturn(Optional.empty());
		when(groupRepository.findById(Mockito.any())).thenReturn(Optional.of(group));

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.insert(productDTO);
		});
	}

	// 7 - Insert Product Group Invalid
	@Test
	void copyDtoToEntityShoulNotFoundExceptionWhenProductGroupDTOInvalid() {
		when(productRepository.getReferenceById(1L)).thenReturn(product);
		when(productRepository.save(product)).thenReturn(product);
		when(unitRepository.findById(Mockito.any())).thenReturn(Optional.of(unit));
		when(taxRepository.findById(Mockito.any())).thenReturn(Optional.of(tax));
		when(groupRepository.findById(999L)).thenReturn(Optional.empty());

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.insert(productDTO);
		});
	}

	// 8 - Update Product
	@Test
	public void updateShouldReturnProductDTOWhenIdExists() {
		when(productRepository.getReferenceById(1L)).thenReturn(product);
		when(productRepository.save(product)).thenReturn(product);
		when(unitRepository.findById(Mockito.any())).thenReturn(Optional.of(unit));
		when(taxRepository.findById(Mockito.any())).thenReturn(Optional.of(tax));
		when(groupRepository.findById(Mockito.any())).thenReturn(Optional.of(group));

		ProductDTO result = service.update(1L, productDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals("Tarugo de alumínio", result.getDescription());
	}

	// 9 - Update Product Invalid
	@Test
	public void updateShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist() {
		when(productRepository.getReferenceById(ProductNonExistingId)).thenThrow(EntityNotFoundException.class);

		assertThrows(ResourceNotFoundException.class, () -> service.update(ProductNonExistingId, productDTO));
	}

	// 10 - Delete Id exists
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		when(productRepository.existsById(ProductExistingId)).thenReturn(true);

		assertDoesNotThrow(() -> service.delete(ProductExistingId));
		verify(productRepository).deleteById(ProductExistingId);
	}

	// 11 - Delete Id Does Not exists
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		when(productRepository.existsById(ProductNonExistingId)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> service.delete(ProductNonExistingId));
	}

	// 12 - Delete Id Dependent
	@Test
	public void deleteShouldThrowDataBaseExceptionWhenIdDependent() {
		when(productRepository.existsById(ProductExistingId)).thenReturn(true);

		doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(ProductExistingId);
		assertThrows(DatabaseException.class, () -> service.delete(ProductExistingId));
	}
}
