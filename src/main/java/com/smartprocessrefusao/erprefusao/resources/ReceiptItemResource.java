package com.smartprocessrefusao.erprefusao.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smartprocessrefusao.erprefusao.dto.ReceiptItemDTO;
import com.smartprocessrefusao.erprefusao.services.ReceiptItemService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/receipt-items")
public class ReceiptItemResource {

    @Autowired
    private ReceiptItemService receiptItemService;

    @PostMapping("/receipt/{receiptId}")
    public ResponseEntity<ReceiptItemDTO> insert(
            @PathVariable Long receiptId,
            @Valid @RequestBody ReceiptItemDTO dto) {

        ReceiptItemDTO newDto = receiptItemService.insert(receiptId, dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newDto.getReceiptId())
                .toUri();

        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ReceiptItemDTO> update(
            @PathVariable Long itemId,
            @Valid @RequestBody ReceiptItemDTO dto) {

        ReceiptItemDTO updatedDto = receiptItemService.update(itemId, dto);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> delete(@PathVariable Long itemId) {

        receiptItemService.delete(itemId);
        return ResponseEntity.noContent().build();
    }
}
