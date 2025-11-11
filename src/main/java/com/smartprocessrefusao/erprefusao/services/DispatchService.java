package com.smartprocessrefusao.erprefusao.services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.DispatchDTO;
import com.smartprocessrefusao.erprefusao.dto.DispatchItemDTO;
import com.smartprocessrefusao.erprefusao.dto.ReportDispatchDTO;
import com.smartprocessrefusao.erprefusao.entities.Dispatch;
import com.smartprocessrefusao.erprefusao.entities.DispatchItem;
import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloy;
import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloyFootage;
import com.smartprocessrefusao.erprefusao.enumerados.AluminumAlloyPol;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionOutGoing;
import com.smartprocessrefusao.erprefusao.projections.ReportDispatchProjection;
import com.smartprocessrefusao.erprefusao.repositories.DispatchRepository;
import com.smartprocessrefusao.erprefusao.repositories.MaterialRepository;
import com.smartprocessrefusao.erprefusao.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.repositories.TicketRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

@Service
public class DispatchService {

	@Autowired
	private DispatchRepository dispatchRepository;

	@Autowired
	private PartnerRepository partnerRepository;

	@Autowired
	private MaterialRepository materialRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Transactional(readOnly = true)
	public Page<ReportDispatchDTO> reportDispatch(String description, Long numTicket, Long people_id,
			Pageable pageable) {
		Page<ReportDispatchProjection> page = dispatchRepository.searchDescriptionMaterialOrNumTicketPeople(description,
				numTicket, people_id, pageable);
		return page.map(ReportDispatchDTO::new);
	}

	@Transactional
	public DispatchDTO insert(DispatchDTO dto) {

		Long numTicket = java.util.Optional.ofNullable(dto.getNumTicket())
				.orElseThrow(() -> new IllegalArgumentException("O número do ticket é obrigatório."));

		// 2. VALIDAÇÃO DE UNICIDADE (sem 'if' explícito)
		// Se o repositório encontrar um ticket com esse numTicket, lança uma exceção.
		ticketRepository.findByNumTicket(numTicket).ifPresent(e -> {
			throw new IllegalArgumentException(
					String.format("O número de ticket '%d' já existe no sistema.", numTicket));
		});

		// 1. VALIDAÇÃO DE NEGÓCIO: A soma das quantidades dos itens não pode exceder o
		// Net Weight.
		BigDecimal totalItemsQuantity = calculateAndValidateItemQuantities(dto);

		if (dto.getNetWeight() != null && totalItemsQuantity.compareTo(dto.getNetWeight()) > 0) {
			throw new IllegalArgumentException("A soma das quantidades dos itens (" + totalItemsQuantity
					+ ") não pode ultrapassar o Peso Líquido (Net Weight) do ticket (" + dto.getNetWeight() + ").");
		}

		// 2. Inicializa o Cache (para evitar NonUniqueObjectException com
		// Partner/Material)
		Map<Long, Partner> partnerCache = new HashMap<>();
		Map<Long, Material> materialCache = new HashMap<>();

		// 3. Mapear DTO para a entidade principal
		Dispatch newDispatch = new Dispatch();
		copyDispatchDtoToEntity(dto, newDispatch);

		// 4. Persistir o Dispatch principal para obter o ID gerado
		newDispatch = dispatchRepository.save(newDispatch);

		// 5. Iterar sobre os DTOs de itens e configurar as entidades.
		if (dto.getDispatchItems() != null) {

			newDispatch.getDispatchItems().clear();

			// Necessário para a unicidade do item, pois a PK é (ID, Partner, Material,
			// Sequência)
			Long sequenceCounter = 1L;

			for (DispatchItemDTO itemDto : dto.getDispatchItems()) {

				DispatchItem itemEntity = new DispatchItem();

				// 5a. Obter instâncias gerenciadas do Cache (Partner/Material)
				Long partnerId = itemDto.getPartnerId();
				if (partnerId == null) {
					throw new ResourceNotFoundException("Partner ID é obrigatório para a PK do Item.");
				}
				Partner partner = partnerCache.computeIfAbsent(partnerId, id -> partnerRepository.getReferenceById(id));

				Long materialId = itemDto.getMaterialId();
				if (materialId == null) {
					throw new ResourceNotFoundException("Material ID é obrigatório para a PK do Item.");
				}
				Material material = materialCache.computeIfAbsent(materialId,
						id -> materialRepository.getReferenceById(id));

				// 5b. Configurar a chave composta (PK) e copiar os dados.
				copyItemDtoToItemEntity(itemDto, itemEntity, newDispatch, partner, material, sequenceCounter

				);

				// Adiciona o item à coleção.
				newDispatch.getDispatchItems().add(itemEntity);

				sequenceCounter++;
			}
		}

		return new DispatchDTO(newDispatch);
	}

	@Transactional
	public DispatchDTO updateByNumTicket(Long numTicket, DispatchDTO dto) {
		Dispatch entity = dispatchRepository.findByNumTicket(numTicket)
				.orElseThrow(() -> new ResourceNotFoundException("Dispatch não encontrado para atualização, ID: " + numTicket));

		// 2. VALIDAÇÃO DE UNICIDADE DO numTicket (Herdado de Ticket)
		// a) Garante que o numTicket do DTO não é nulo e obtém o valor
		Long newNumTicket = Optional.ofNullable(dto.getNumTicket())
				.orElseThrow(() -> new IllegalArgumentException("O número do ticket é obrigatório."));

		ticketRepository.findByNumTicket(newNumTicket).ifPresent(existing -> {
			if (!existing.getId().equals(entity.getId())) {
				throw new IllegalArgumentException(
						String.format("O número de ticket '%d' já está em uso por outro registro.", newNumTicket));
			}
		});

		// 1. VALIDAÇÃO DE NEGÓCIO: A soma das quantidades dos itens não pode exceder o
		// Net Weight.
		BigDecimal totalItemsQuantity = calculateAndValidateItemQuantities(dto);

		if (dto.getNetWeight() != null && totalItemsQuantity.compareTo(dto.getNetWeight()) > 0) {
			throw new IllegalArgumentException("A soma das quantidades dos itens (" + totalItemsQuantity
					+ ") não pode ultrapassar o Peso Líquido (Net Weight) do ticket (" + dto.getNetWeight() + ").");
		}

		copyDispatchDtoToEntity(dto, entity);

		Map<Long, Partner> partnerCache = new HashMap<>();
		Map<Long, Material> materialCache = new HashMap<>();

		entity.getDispatchItems().clear();

		if (dto.getDispatchItems() != null) {
			Long sequenceCounter = 1L;

			for (DispatchItemDTO itemDto : dto.getDispatchItems()) {

				DispatchItem itemEntity = new DispatchItem();

				Long partnerId = itemDto.getPartnerId();
				Partner partner = partnerCache.computeIfAbsent(partnerId,
						pid -> partnerRepository.getReferenceById(pid));

				Long materialId = itemDto.getMaterialId();
				Material material = materialCache.computeIfAbsent(materialId,
						mid -> materialRepository.getReferenceById(mid));

				// Configura a chave composta (PK) e copia os dados.
				copyItemDtoToItemEntity(itemDto, itemEntity, entity, partner, material, sequenceCounter);

				entity.getDispatchItems().add(itemEntity);

				sequenceCounter++;
			}
		}

		return new DispatchDTO(dispatchRepository.save(entity));
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long numTicket) {
		if (!dispatchRepository.existsByNumTicket(numTicket)) {
			throw new ResourceNotFoundException("Ticket not found " + numTicket);
		}
		try {
			dispatchRepository.deleteByNumTicket(numTicket);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	// --- MÉTODOS AUXILIARES ---

	private void copyDispatchDtoToEntity(DispatchDTO dto, Dispatch entity) {
		entity.setNumTicket(Long.valueOf(dto.getNumTicket()));
		entity.setDateTicket(dto.getDateTicket());
		entity.setNumberPlate(dto.getNumberPlate().toUpperCase());
		entity.setNetWeight(dto.getNetWeight());
	}

	private void copyItemDtoToItemEntity(DispatchItemDTO itemDto, DispatchItem itemEntity, Dispatch parentDispatch,
			Partner partner, Material material, Long itemSequence) {

		// --- 1. SETA OS COMPONENTES DA CHAVE COMPOSTA (PK) ---
		itemEntity.getId().setDispatch(parentDispatch);
		itemEntity.getId().setPartner(partner);
		itemEntity.getId().setMaterial(material);
		itemEntity.getId().setItemSequence(itemSequence); // Novo campo sequencial

		// --- 2. SETA OUTROS ATRIBUTOS (Dados Simples) ---
		itemEntity.setDocumentNumber(itemDto.getDocumentNumber());
		itemEntity.setObservation(itemDto.getObservation().toUpperCase());
		itemEntity.setPrice(itemDto.getPrice());
		itemEntity.setQuantity(itemDto.getQuantity());

		BigDecimal totalValue = itemEntity.getQuantity().multiply(itemEntity.getPrice());
		itemEntity.setTotalValue(totalValue);

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

	}

	/**
	 * Calcula a soma total das quantidades dos itens e valida o formato. * @param
	 * dto O DispatchDTO contendo a lista de DispatchItemDTOs.
	 * 
	 * @return A soma total das quantidades dos itens como BigDecimal.
	 */
	private BigDecimal calculateAndValidateItemQuantities(DispatchDTO dto) {
		if (dto.getDispatchItems() == null || dto.getDispatchItems().isEmpty()) {
			return BigDecimal.ZERO;
		}

		BigDecimal totalQuantity = BigDecimal.ZERO;

		for (DispatchItemDTO itemDto : dto.getDispatchItems()) {

			BigDecimal itemQuantity = itemDto.getQuantity();

			if (itemQuantity == null) {

				itemQuantity = BigDecimal.ZERO;
			}

			totalQuantity = totalQuantity.add(itemQuantity);
		}

		return totalQuantity;
	}
}
