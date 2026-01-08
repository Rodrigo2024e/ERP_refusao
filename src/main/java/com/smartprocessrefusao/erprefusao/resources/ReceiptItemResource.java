package com.smartprocessrefusao.erprefusao.resources;

import java.net.URI;
import java.util.List;

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

import com.smartprocessrefusao.erprefusao.dto.ReceiptItemDTO;
import com.smartprocessrefusao.erprefusao.services.ReceiptItemService;

import jakarta.validation.Valid;

// O path base reflete a hierarquia: Item de um Recibo
@RestController
@RequestMapping(value = "/api/receipts-items")
public class ReceiptItemResource {

    @Autowired
    private ReceiptItemService itemService;

    // --- BUSCA GERAL (mantido global, mas idealmente seria findByReceiptId) ---
    @GetMapping
    public ResponseEntity<List<ReceiptItemDTO>> findAll() {
        List<ReceiptItemDTO> list = itemService.findAll();
        return ResponseEntity.ok(list);
    }
    

    // --- BUSCA POR CHAVE COMPOSTA ---

    /**
     * Busca um Item de Recibo pela sua Chave Composta (ReceiptId, PartnerId, MaterialId).
     * GET /receipts/{receiptId}/items/{partnerId}/{materialId}
     */
    @GetMapping(value = "/{partnerId}/{materialCode}")
    public ResponseEntity<ReceiptItemDTO> findById(
            @PathVariable Long receiptId,
            @PathVariable Long partnerId,
            @PathVariable Long materialCode) {
        
        // Chamada direta, pois os IDs do Path são passados ao Service.
        ReceiptItemDTO dto = itemService.findById(receiptId, partnerId, materialCode);
        return ResponseEntity.ok(dto);
    }

    // --- INSERÇÃO (Adaptado para DTO Imutável) ---

    /**
     * Insere um novo Item de Recibo. O Service deve garantir a consistência do receiptId.
     * POST /receipts/{receiptId}/items
     */
    @PostMapping(value = "/receiptId")
    public ResponseEntity<ReceiptItemDTO> insert(@PathVariable Long receiptId, 
                                                 @Valid @RequestBody ReceiptItemDTO dto) {
        
        // Passamos o ID do Path explicitamente junto com o DTO do corpo.
        // O Service (itemService) é responsável por criar um NOVO DTO internamente 
        // ou usar o 'receiptId' do Path para a PK, ignorando o ID que pode estar no corpo.
        ReceiptItemDTO newDto = itemService.insert(receiptId, dto); 

        // Retorna 201 Created com a URI completa da chave composta.
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{partnerId}/{materialCode}")
                .buildAndExpand(newDto.getPartnerId(), newDto.getMaterialCode())
                .toUri();

        return ResponseEntity.created(uri).body(newDto);
    }

    // --- ATUALIZAÇÃO (Adaptado para DTO Imutável) ---

    /**
     * Atualiza um Item de Recibo existente pela sua Chave Composta.
     * PUT /receipts/{receiptId}/items/{partnerId}/{materialId}
     */
    @PutMapping(value = "/{partnerId}/{materialCode}")
    public ResponseEntity<ReceiptItemDTO> update(
            @PathVariable Long receiptId,
            @PathVariable Long partnerId,
            @PathVariable Long materialCode,
            @Valid @RequestBody ReceiptItemDTO dto) {
        
        // Passamos todos os componentes da PK e o corpo (o Service se encarrega de mapear os dados)
        ReceiptItemDTO updatedDto = itemService.update(receiptId, partnerId, materialCode, dto);
        return ResponseEntity.ok(updatedDto);
    }

    // --- DELEÇÃO ---

    /**
     * Deleta um Item de Recibo pela sua Chave Composta.
     * DELETE /receipts/{receiptId}/items/{partnerId}/{materialId}
     */
    @DeleteMapping(value = "/{partnerId}/{materialCode}")
    public ResponseEntity<Void> delete(
            @PathVariable Long receiptId,
            @PathVariable Long partnerId,
            @PathVariable Long materialCode) {
        
        itemService.delete(receiptId, partnerId, materialCode);
        return ResponseEntity.noContent().build();
    }
}