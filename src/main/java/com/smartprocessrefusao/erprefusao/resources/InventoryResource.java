package com.smartprocessrefusao.erprefusao.resources;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smartprocessrefusao.erprefusao.dto.InventoryDTO;
import com.smartprocessrefusao.erprefusao.dto.InventoryReportDTO;
import com.smartprocessrefusao.erprefusao.projections.InventoryReportProjection;
import com.smartprocessrefusao.erprefusao.services.InventoryService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/inventories")
public class InventoryResource {

    @Autowired
    private InventoryService inventoryService;

    /* ======================================================
       CRUD
       ====================================================== */

    @GetMapping
    public ResponseEntity<List<InventoryDTO>> findAll() {
        List<InventoryDTO> list = inventoryService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryDTO> findById(@PathVariable Long id) {
        InventoryDTO dto = inventoryService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<InventoryDTO> insert(@Valid @RequestBody InventoryDTO dto) {
        InventoryDTO newDto = inventoryService.insert(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody InventoryDTO dto) {

        InventoryDTO updatedDto = inventoryService.update(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        inventoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /* ======================================================
       RELATÓRIO DE INVENTÁRIO (HISTÓRICO)
       ====================================================== */

    @GetMapping("/report-range")
    public ResponseEntity<Page<InventoryReportDTO>> reportRange(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate,

            @RequestParam(required = false)
            Long materialCode,

            Pageable pageable) {

        Page<InventoryReportProjection> page =
                inventoryService.getReportRange(
                		startDate, 
                		endDate, 
                		materialCode, 
                		pageable);

        Page<InventoryReportDTO> dtoPage =
                page.map(InventoryReportDTO::new);

        return ResponseEntity.ok(dtoPage);
    }
}
