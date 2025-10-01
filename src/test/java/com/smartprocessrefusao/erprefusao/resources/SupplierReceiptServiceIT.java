package com.smartprocessrefusao.erprefusao.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.smartprocessrefusao.erprefusao.dto.SupplierReceiptDTO;
import com.smartprocessrefusao.erprefusao.entities.SupplierReceipt;
import com.smartprocessrefusao.erprefusao.repositories.SupplierReceiptRepository;
import com.smartprocessrefusao.erprefusao.services.SupplierReceiptService;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;
import com.smartprocessrefusao.erprefusao.tests.SupplierReceiptFactory;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class SupplierReceiptServiceIT {

    @Autowired
    private SupplierReceiptService service;

    @Autowired
    private SupplierReceiptRepository supplierReceiptRepository;


    private SupplierReceiptDTO supplierReceiptDTO;

    @BeforeEach
    void setUp() {

    }

    @Test
    public void insert_ShouldPersistSupplierReceipt() {
        supplierReceiptDTO  = SupplierReceiptFactory.createSupplierReceiptValidPartnerDTO();

        SupplierReceiptDTO result = service.insert(supplierReceiptDTO);

        assertNotNull(result.getId());
        assertEquals(supplierReceiptDTO.getAmountSupplier(), result.getAmountSupplier());
        assertEquals(supplierReceiptDTO.getUnitValue(), result.getUnitValue());
        assertEquals(supplierReceiptDTO.getTransactionDescription(), result.getTransactionDescription());
        assertEquals(supplierReceiptDTO.getCosts(), result.getCosts());
    }
    

    @Test
    public void insert_ShouldThrowResourceNotFoundException_WhenPartnerDoesNotExist() {
        supplierReceiptDTO = SupplierReceiptFactory.createSupplierReceiptInValidPartnerDTO();

        assertThrows(ResourceNotFoundException.class, () -> service.insert(supplierReceiptDTO));
    }
    
    @Test
    public void insert_ShouldThrowResourceNotFoundException_WhenInputDoesNotExist() {
    	 supplierReceiptDTO = SupplierReceiptFactory.createSupplierReceiptInvalidInputDTO();

        assertThrows(ResourceNotFoundException.class, () -> service.insert(supplierReceiptDTO));
    }
    
    @Test
    public void insert_ShouldThrowResourceNotFoundException_WhenInvalidCosts() {
    	supplierReceiptDTO = SupplierReceiptFactory.createSupplierReceiptInvalidCostsDTO();

        assertThrows(ResourceNotFoundException.class, () -> service.insert(supplierReceiptDTO));
    }

    @Test
    public void findById_ShouldReturnSupplierReceipt_WhenIdExists() {
        SupplierReceipt receipt = supplierReceiptRepository.save(
                SupplierReceiptFactory.createEntity());

        SupplierReceiptDTO result = service.findById(receipt.getId());

        assertNotNull(result);
        assertEquals(receipt.getId(), result.getId());
    }

    @Test
    public void findById_ShouldThrowResourceNotFoundException_WhenIdDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> service.findById(99999L));
    }

    @Test
    public void update_ShouldUpdateSupplierReceipt_WhenIdExists() {
        SupplierReceipt receipt = supplierReceiptRepository.save(
                SupplierReceiptFactory.createEntity());

        SupplierReceiptDTO dto = SupplierReceiptFactory.createSupplierReceiptValidPartnerDTO();

        SupplierReceiptDTO result = service.update(receipt.getId(), dto);

        assertEquals(dto.getAmountSupplier(), result.getAmountSupplier());
        assertEquals(dto.getUnitValue(), result.getUnitValue());
    }

    @Test
    public void update_ShouldThrowResourceNotFoundException_WhenIdDoesNotExist() {
        SupplierReceiptDTO dto = SupplierReceiptFactory.createDTO();

        assertThrows(ResourceNotFoundException.class, () -> service.update(99L, dto));
    }
    
    @Test
    public void update_ShouldThrowResourceNotFoundException_WhenPartnerDoesNotExist() {
        SupplierReceiptDTO dto = SupplierReceiptFactory.createSupplierReceiptInValidPartnerDTO();

        assertThrows(ResourceNotFoundException.class, () -> service.update(99L, dto));
    }
    
    @Test
    public void update_ShouldThrowResourceNotFoundException_WhenInputDoesNotExist() {
        SupplierReceiptDTO dto = SupplierReceiptFactory.createSupplierReceiptInvalidInputDTO();

        assertThrows(ResourceNotFoundException.class, () -> service.update(99L, dto));
    }
    
    @Test
    public void update_ShouldThrowResourceNotFoundException_WhenInvalidCosts() {
        SupplierReceiptDTO dto = SupplierReceiptFactory.createSupplierReceiptInvalidCostsDTO();

        assertThrows(ResourceNotFoundException.class, () -> service.update(99L, dto));
    }

    @Test
    public void delete_ShouldDeleteSupplierReceipt_WhenIdExists() {
        SupplierReceipt receipt = supplierReceiptRepository.save(
                SupplierReceiptFactory.createEntity());

        service.delete(receipt.getId());

        assertFalse(supplierReceiptRepository.existsById(receipt.getId()));
    }

    @Test
    public void delete_ShouldThrowResourceNotFoundException_WhenIdDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> service.delete(99999L));
    }
}
