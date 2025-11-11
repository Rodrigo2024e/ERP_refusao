package com.smartprocessrefusao.erprefusao.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.DispatchItemDTO;
import com.smartprocessrefusao.erprefusao.entities.Dispatch;
import com.smartprocessrefusao.erprefusao.entities.DispatchItem;
import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.PK.DispatchItemPK;
import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloy;
import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloyFootage;
import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloyPol;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionOutGoing;
import com.smartprocessrefusao.erprefusao.repositories.DispatchItemRepository;
import com.smartprocessrefusao.erprefusao.repositories.DispatchRepository;
import com.smartprocessrefusao.erprefusao.repositories.MaterialRepository;
import com.smartprocessrefusao.erprefusao.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

@Service
public class DispatchItemService {

    @Autowired
    private DispatchItemRepository dispatchItemRepository;

    @Autowired
    private DispatchRepository dispatchRepository;

    @Autowired
    private PartnerRepository partnerRepository; 

    @Autowired
    private MaterialRepository materialRepository;

    // --- MÉTODOS AUXILIARES DE PK ---

    /**
     * Constrói a chave primária composta (DispatchItemPK) usando referências.
     * @param dispatchId O ID da Dispatch (parte da PK).
     * @param partnerId O ID do Partner (parte da PK).
     * @param materialId O ID do Material (parte da PK).
     * @return A chave DispatchItemPK totalmente inicializada.
     */
    private DispatchItemPK buildDispatchItemPK(Long dispatchId, Long partnerId, Long materialId) {
        Dispatch dispatchRef = dispatchRepository.getReferenceById(dispatchId);
        Partner partnerRef = partnerRepository.getReferenceById(partnerId);
        Material materialRef = materialRepository.getReferenceById(materialId);

        return new DispatchItemPK(dispatchRef, partnerRef, materialRef);
    }

    @Transactional(readOnly = true)
    public List<DispatchItemDTO> findAll() {
        return dispatchItemRepository.findAll().stream().map(DispatchItemDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public DispatchItemDTO findById(Long dispatchId, Long partnerId, Long materialId) {
        DispatchItemPK pk = buildDispatchItemPK(dispatchId, partnerId, materialId);
        
        Optional<DispatchItem> obj = dispatchItemRepository.findById(pk);
        DispatchItem entity = obj.orElseThrow(() -> new ResourceNotFoundException("DispatchItem not found for the given IDs"));
        
        return new DispatchItemDTO(entity);
    }

    @Transactional
    public DispatchItemDTO insert(Long parentDispatchId, DispatchItemDTO dto) {

        if (dto.getDispatchId() != null && !dto.getDispatchId().equals(parentDispatchId)) {
            throw new IllegalArgumentException("O Dispatch ID no corpo (" + dto.getDispatchId() + ") não corresponde ao ID do Path (" + parentDispatchId + ")");
        }

        // 2. Constrói a PK usando o ID da URL (priorizando o parentDispatchId)
        // O buildDispatchItemPK carrega as referências de Dispatch, Partner e Material.
        DispatchItemPK pk = buildDispatchItemPK(
            parentDispatchId, 
            dto.getPartnerId(), 
            dto.getMaterialId()
        );

        // 3. Verifica se o item já existe (para evitar duplicidade em uma inserção de chave composta)
        if (dispatchItemRepository.existsById(pk)) {
            throw new IllegalArgumentException("Um DispatchItem com esta chave (DispatchID: " + parentDispatchId + 
                                                ", PartnerID: " + dto.getPartnerId() + 
                                                ", MaterialID: " + dto.getMaterialId() + ") já existe.");
        }
        
        // 4. Cria a nova entidade e seta a PK (que está totalmente inicializada)
        DispatchItem entity = new DispatchItem();
        entity.setId(pk); 
        
        // 5. Copia os campos de dados não-PK do DTO para a Entidade
        // Usamos o DTO original, pois o copyDtoToEntity lida apenas com os campos de dados (quantity, price, observation, etc.).
        copyDtoToEntity(dto, entity); 
        
        // 6. Salva e retorna o DTO
        entity = dispatchItemRepository.save(entity);
        return new DispatchItemDTO(entity);
    }

    @Transactional
    public DispatchItemDTO update(Long dispatchId, Long partnerId, Long materialId, DispatchItemDTO dto) {
        
        // 1. Constrói a PK para localizar o item existente
        DispatchItemPK pk = buildDispatchItemPK(dispatchId, partnerId, materialId);

        try {
            // 2. Obtém a referência da entidade
            DispatchItem entity = dispatchItemRepository.getReferenceById(pk);
            
            // 3. Copia os novos dados (a PK não precisa ser alterada)
            copyDtoToEntity(dto, entity);
            
            // 4. Salva e converte para DTO
            entity = dispatchItemRepository.save(entity);
            return new DispatchItemDTO(entity);
            
        } catch (jakarta.persistence.EntityNotFoundException e) {
            throw new ResourceNotFoundException("DispatchItem not found for update.");
        }
    }

    @Transactional
    public void delete(Long dispatchId, Long partnerId, Long materialId) {
        DispatchItemPK pk = buildDispatchItemPK(dispatchId, partnerId, materialId);
        
        // Se a validação for necessária
        if (!dispatchItemRepository.existsById(pk)) {
            throw new ResourceNotFoundException("DispatchItem not found for deletion.");
        }
        
        dispatchItemRepository.deleteById(pk);
    }

    // --- FUNÇÃO AUXILIAR DE MAPPING ---

    private void copyDtoToEntity(DispatchItemDTO itemDto, DispatchItem itemEntity) {
        
        // Mapeamento dos campos que não fazem parte da PK:
        
    	// Conversão de Enum
		try {
			TypeTransactionOutGoing typeDispatch = TypeTransactionOutGoing.fromDescription(itemDto.getTypeDispatch());
			itemEntity.setTypeDispatch(typeDispatch);
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de expedição inválida: " + itemDto.getTypeDispatch());
		}

		try {
			AluminumAlloy alloy = AluminumAlloy.fromDescription(itemDto.getAlloy());
			itemEntity.setAlloy(alloy);
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de liga inválida: " + itemDto.getAlloy());
		}

		try {
			AluminumAlloyPol alloyPol = AluminumAlloyPol.valueOf(itemDto.getAlloyPol());
			itemEntity.setAlloyPol(alloyPol);
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de polegada inválida: " + itemDto.getAlloyPol());
		}
		
		try {
			AluminumAlloyFootage alloyFootage = AluminumAlloyFootage.valueOf(itemDto.getAlloyFootage());
			itemEntity.setAlloyFootage(alloyFootage);
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de metragem inválida: " + itemDto.getAlloyFootage());
		}

		itemEntity.setDocumentNumber(itemDto.getDocumentNumber());
		itemEntity.setQuantity(itemDto.getQuantity());
		itemEntity.setPrice(itemDto.getPrice());

        // 🧮 Recalcula o valor total (também está no @PrePersist/@PreUpdate da entidade, mas bom ter aqui)
        if (itemDto.getQuantity() != null && itemDto.getPrice() != null) {
        	itemEntity.setTotalValue(itemDto.getQuantity().multiply(itemDto.getPrice()));
        } else {
        	itemEntity.setTotalValue(BigDecimal.ZERO);
        }

        itemEntity.setObservation(itemDto.getObservation());
    }
}