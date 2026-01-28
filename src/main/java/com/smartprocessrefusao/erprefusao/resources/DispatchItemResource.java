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

import com.smartprocessrefusao.erprefusao.dto.DispatchItemDTO;
import com.smartprocessrefusao.erprefusao.services.DispatchItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/dispatches-items")
public class DispatchItemResource {

    @Autowired
    private DispatchItemService itemService;

    @PostMapping("/dispatch/{dispatchId}")
    public ResponseEntity<DispatchItemDTO> insert(
            @PathVariable Long dispatchId,
            @Valid @RequestBody DispatchItemDTO dto) {

        DispatchItemDTO newDto = itemService.insert(dispatchId, dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newDto.getDispatchId())
                .toUri();

        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<DispatchItemDTO> update(
            @PathVariable Long itemId,
            @Valid @RequestBody DispatchItemDTO dto) {

        DispatchItemDTO updatedDto = itemService.update(itemId, dto);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> delete(@PathVariable Long itemId) {

    	itemService.delete(itemId);
        return ResponseEntity.noContent().build();
    }
}