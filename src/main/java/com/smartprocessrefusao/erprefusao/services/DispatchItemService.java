package com.smartprocessrefusao.erprefusao.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.DispatchItemDTO;
import com.smartprocessrefusao.erprefusao.entities.Dispatch;
import com.smartprocessrefusao.erprefusao.entities.DispatchItem;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.Product;
import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloy;
import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloyFootage;
import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloyPol;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionOutGoing;
import com.smartprocessrefusao.erprefusao.repositories.DispatchItemRepository;
import com.smartprocessrefusao.erprefusao.repositories.DispatchRepository;
import com.smartprocessrefusao.erprefusao.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.repositories.ProductRepository;
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
    private ProductRepository productRepository;

	//INSERT
	@Transactional
	public DispatchItemDTO insert(Long dispatchId, DispatchItemDTO dto) {

		Dispatch dispatch = dispatchRepository.findById(dto.getDispatchId())
				.orElseThrow(() -> new ResourceNotFoundException("Dispatch não encontrado: " + dto.getDispatchId()));

		Partner partner = partnerRepository.findById(dto.getPartnerId())
				.orElseThrow(() -> new ResourceNotFoundException("Partner não encontrado: " + dto.getPartnerId()));

		Product product = productRepository.findByProductCode(dto.getProductCode())
				.orElseThrow(() -> new ResourceNotFoundException("Product não encontrado: " + dto.getProductCode()));

		DispatchItem entity = new DispatchItem();
		entity.setDispatch(dispatch);
		entity.setPartner(partner);
		entity.setProduct(product);

		// 🔢 sequência (exemplo simples)
		Integer nextSequence = dispatchItemRepository
				.findMaxSequenceByDispatchId(dispatchId)
				.orElse(0) + 1;

		entity.setItemSequence(nextSequence);

		copyDtoToEntity(dto, entity);

		entity = dispatchItemRepository.save(entity);
		return new DispatchItemDTO(entity);
	}

	// UPDATE
	@Transactional
	public DispatchItemDTO update(Long itemId, DispatchItemDTO dto) {

		DispatchItem entity = dispatchItemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("DispatchItem não encontrado: " + itemId));

		if (dto.getPartnerId() != null &&
				!dto.getPartnerId().equals(entity.getPartner().getId())) {

			Partner partner = partnerRepository.findById(dto.getPartnerId())
					.orElseThrow(() -> new ResourceNotFoundException("Partner não encontrado: " + dto.getPartnerId()));
			entity.setPartner(partner);
		}

		if (dto.getProductCode() != null &&
				!dto.getProductCode().equals(entity.getProduct().getProductCode())) {

			Product product = productRepository.findByProductCode(dto.getProductCode())
					.orElseThrow(() -> new ResourceNotFoundException("Product não encontrado: " + dto.getProductCode()));
			entity.setProduct(product);
		}

		copyDtoToEntity(dto, entity);

		entity = dispatchItemRepository.save(entity);
		return new DispatchItemDTO(entity);
	}
	
	// DELETE
	@Transactional
	public void delete(Long itemId) {

		if (!dispatchItemRepository.existsById(itemId)) {
			throw new ResourceNotFoundException("DispatchItem não encontrado para exclusão: " + itemId);
		}

		dispatchItemRepository.deleteById(itemId);
	}

    // --- FUNÇÃO AUXILIAR DE MAPPING ---

    private void copyDtoToEntity(DispatchItemDTO itemDto, DispatchItem itemEntity) {
        
        // Mapeamento dos campos que não fazem parte da PK:
    	
    	itemEntity.setDocumentNumber(itemDto.getDocumentNumber());
		itemEntity.setQuantity(itemDto.getQuantity());
		itemEntity.setPrice(itemDto.getPrice());
        
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

		
        // 🧮 Recalcula o valor total (também está no @PrePersist/@PreUpdate da entidade, mas bom ter aqui)
        if (itemDto.getQuantity() != null && itemDto.getPrice() != null) {
        	itemEntity.setTotalValue(itemDto.getQuantity().multiply(itemDto.getPrice()));
        } else {
        	itemEntity.setTotalValue(BigDecimal.ZERO);
        }

        itemEntity.setObservation(itemDto.getObservation());
    }
}