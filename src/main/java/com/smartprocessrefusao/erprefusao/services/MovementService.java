package com.smartprocessrefusao.erprefusao.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.MovementDTO;
import com.smartprocessrefusao.erprefusao.entities.Input;
import com.smartprocessrefusao.erprefusao.entities.Movement;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.Ticket;
import com.smartprocessrefusao.erprefusao.entities.TypeTransaction;
import com.smartprocessrefusao.erprefusao.enumerados.TypeExpenses;
import com.smartprocessrefusao.erprefusao.projections.MovementProjection;
import com.smartprocessrefusao.erprefusao.repositories.InputRepository;
import com.smartprocessrefusao.erprefusao.repositories.MovementRepository;
import com.smartprocessrefusao.erprefusao.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.repositories.TicketRepository;
import com.smartprocessrefusao.erprefusao.repositories.TypeTransactionRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MovementService {

	@Autowired
	private MovementRepository movementRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private InputRepository inputRepository;

	@Autowired
	private PartnerRepository partnerRepository;

	@Autowired
	private TypeTransactionRepository transactionRepository;

	@Transactional(readOnly = true)
	public Page<MovementDTO> reportMovement(Integer numberTicketId, Pageable pageable) {

		Page<MovementProjection> page = movementRepository.searchMovementByNumberTicket(numberTicketId, pageable);

		return page.map(MovementDTO::new);
	}

	@Transactional(readOnly = true)
	public MovementDTO findById(Long id) {
		try {
			Optional<Movement> obj = movementRepository.findById(id);
			Movement entity = obj.orElseThrow(() -> new EntityNotFoundException("Movement not found"));
			return new MovementDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}

	}

	@Transactional
	public MovementDTO insert(MovementDTO dto) {

		// 1. Buscar ticket
		Ticket ticket = ticketRepository.findByNumTicket(dto.getNumTicketId())
				.orElseThrow(() -> new ResourceNotFoundException("Ticket não encontrado"));

		// 2. Somar amountMaterial existentes do ticket (se tiver)
		BigDecimal somaAmountMaterial = movementRepository.sumAmountMaterialByNumTicket(dto.getNumTicketId());
		if (somaAmountMaterial == null) {
			somaAmountMaterial = BigDecimal.ZERO;
		}

		// 3. Calcular novo total incluindo o dto.amountMaterial
		BigDecimal novoTotal = somaAmountMaterial.add(dto.getAmountMaterial());

		// 4. Validar com netWeight do ticket
		if (novoTotal.compareTo(ticket.getNetWeight()) > 0) {
			throw new IllegalArgumentException("Peso de ticket excedido! " + "Ticket nº: "
					+ ticket.getNumTicket() + " Peso Total: " + ticket.getNetWeight() + " kg");

		}

		Movement entity = new Movement();
		copyDtoToEntity(dto, entity);
		entity = movementRepository.save(entity);
		return new MovementDTO(entity);
	}

	@Transactional
	public MovementDTO update(Long id, MovementDTO dto) {
		try {
			Movement entity = movementRepository.getReferenceById(id);

			// 1. Buscar ticket
			Ticket ticket = ticketRepository.findByNumTicket(dto.getNumTicketId())
					.orElseThrow(() -> new ResourceNotFoundException("Ticket não encontrada"));

			// 2. Somar amountMaterial de todos os movimentos do ticket, exceto o atual
			BigDecimal somaAmountMaterial = movementRepository
					.sumAmountMaterialByNumTicketExcludingId(dto.getNumTicketId(), id);
			if (somaAmountMaterial == null) {
				somaAmountMaterial = BigDecimal.ZERO;
			}

			// 3. Calcular novo total
			BigDecimal novoTotal = somaAmountMaterial.add(dto.getAmountMaterial());

			// 4. Validar
			if (novoTotal.compareTo(ticket.getNetWeight()) > 0) {
				throw new IllegalArgumentException("Peso de ticket excedido! " + "Ticket nº: "
						+ ticket.getNumTicket() + " Peso Total: " + ticket.getNetWeight() + " kg");
			}

			copyDtoToEntity(dto, entity);
			entity = movementRepository.save(entity);
			return new MovementDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!movementRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			movementRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	public void copyDtoToEntity(MovementDTO dto, Movement entity) {
		entity.setAmountMaterial(dto.getAmountMaterial());
		entity.setUnitValue(dto.getUnitValue());
		entity.setMetalYield(dto.getMetalYield());

		// 🧮 Cálculo de totalValue
		if (dto.getAmountMaterial() != null && dto.getUnitValue() != null) {
			entity.setTotalValue(
					dto.getAmountMaterial().multiply(dto.getUnitValue()).setScale(2, RoundingMode.HALF_UP));
		}

		// 🧮 Cálculo de metalWeight
		if (dto.getAmountMaterial() != null && dto.getMetalYield() != null) {
			entity.setMetalWeight(
					dto.getAmountMaterial().multiply(dto.getMetalYield()).setScale(2, RoundingMode.HALF_UP));
		}

		// 🧮 Cálculo de slag
		if (dto.getAmountMaterial() != null && entity.getMetalWeight() != null) {
			entity.setSlag(dto.getAmountMaterial().subtract(entity.getMetalWeight()).setScale(2, RoundingMode.HALF_UP));
		}

		Optional.ofNullable(dto.getNumTicketId()).ifPresent(id -> {
			Ticket ticket = ticketRepository.findByNumTicket(id)
					.orElseThrow(() -> new ResourceNotFoundException("Ticket não encontrada"));
			entity.setNumTicket(ticket);
		});

		try {
			if (dto.getExpenses() != null) {
				TypeExpenses expenses = TypeExpenses.valueOf(dto.getExpenses().toUpperCase());
				entity.setExpenses(expenses);
			}
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de despesa inválida: " + dto.getExpenses());
		}

		Optional.ofNullable(dto.getPartnerId()).ifPresent(id -> {
			Partner partner = partnerRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Parceiro não encontrado"));
			entity.setPartner(partner);
		});

		Optional.ofNullable(dto.getInputId()).ifPresent(id -> {
			Input input = inputRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Material não encontrado"));
			entity.setInput(input);
		});

		Optional.ofNullable(dto.getTransactionId()).ifPresent(id -> {
			TypeTransaction transaction = transactionRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Operação não encontrada"));
			entity.setTransaction(transaction);
		});

	}

}
