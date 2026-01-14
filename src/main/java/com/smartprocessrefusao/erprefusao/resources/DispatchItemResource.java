package com.smartprocessrefusao.erprefusao.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

// O path base reflete a hierarquia: Item de um Recibo
@RestController
@RequestMapping(value = "/api/dispatches-items")
public class DispatchItemResource {

    @Autowired
    private DispatchItemService itemService;


    // --- BUSCA POR CHAVE COMPOSTA ---

    /**
     * Busca um Item de Recibo pela sua Chave Composta (DispatchId, PartnerId, MaterialId).
     * GET /dispatchs/{dispatchId}/items/{partnerId}/{productId}
     */
    @GetMapping(value = "/{partnerId}/{productCode}")
    public ResponseEntity<DispatchItemDTO> findById(
            @PathVariable Long dispatchId,
            @PathVariable Long partnerId,
            @PathVariable Long productCode) {
        
        // Chamada direta, pois os IDs do Path são passados ao Service.
        DispatchItemDTO dto = itemService.findById(dispatchId, partnerId, productCode);
        return ResponseEntity.ok(dto);
    }

    // --- INSERÇÃO (Adaptado para DTO Imutável) ---

    /**
     * Insere um novo Item de Recibo. O Service deve garantir a consistência do dispatchId.
     * POST /dispatchs/{dispatchId}/items
     */
    @PostMapping(value = "/dispatchId")
    public ResponseEntity<DispatchItemDTO> insert(@PathVariable Long dispatchId, 
                                                 @Valid @RequestBody DispatchItemDTO dto) {
        
        // Passamos o ID do Path explicitamente junto com o DTO do corpo.
        // O Service (itemService) é responsável por criar um NOVO DTO internamente 
        // ou usar o 'dispatchId' do Path para a PK, ignorando o ID que pode estar no corpo.
        DispatchItemDTO newDto = itemService.insert(dispatchId, dto); 

        // Retorna 201 Created com a URI completa da chave composta.
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{partnerId}/{productCode}")
                .buildAndExpand(newDto.getPartnerId(), newDto.getProductCode())
                .toUri();

        return ResponseEntity.created(uri).body(newDto);
    }

    // --- ATUALIZAÇÃO (Adaptado para DTO Imutável) ---

    /**
     * Atualiza um Item de Recibo existente pela sua Chave Composta.
     * PUT /dispatchs/{dispatchId}/items/{partnerId}/{productId}
     */
    @PutMapping(value = "/{partnerId}/{productCode}")
    public ResponseEntity<DispatchItemDTO> update(
            @PathVariable Long dispatchId,
            @PathVariable Long partnerId,
            @PathVariable Long productCode,
            @Valid @RequestBody DispatchItemDTO dto) {
        
        // Passamos todos os componentes da PK e o corpo (o Service se encarrega de mapear os dados)
        DispatchItemDTO updatedDto = itemService.update(dispatchId, partnerId, productCode, dto);
        return ResponseEntity.ok(updatedDto);
    }

    // --- DELEÇÃO ---

    /**
     * Deleta um Item de Recibo pela sua Chave Composta.
     * DELETE /dispatchs/{dispatchId}/items/{partnerId}/{productId}
     */
    @DeleteMapping(value = "/{partnerId}/{productCode}")
    public ResponseEntity<Void> delete(
            @PathVariable Long dispatchId,
            @PathVariable Long partnerId,
            @PathVariable Long productCode) {
        
        itemService.delete(dispatchId, partnerId, productCode);
        return ResponseEntity.noContent().build();
    }
}