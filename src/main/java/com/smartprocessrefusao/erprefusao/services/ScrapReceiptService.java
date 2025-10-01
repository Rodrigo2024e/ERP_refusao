package com.smartprocessrefusao.erprefusao.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.ReportScrapReceiptDTO;
import com.smartprocessrefusao.erprefusao.dto.ScrapReceiptDTO;
import com.smartprocessrefusao.erprefusao.entities.Input;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.ScrapReceipt;
import com.smartprocessrefusao.erprefusao.entities.Ticket;
import com.smartprocessrefusao.erprefusao.enumerados.TypeCosts;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionReceipt;
import com.smartprocessrefusao.erprefusao.projections.ScrapReceiptProjection;
import com.smartprocessrefusao.erprefusao.repositories.InputRepository;
import com.smartprocessrefusao.erprefusao.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.repositories.ScrapReceiptRepository;
import com.smartprocessrefusao.erprefusao.repositories.TicketRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ScrapReceiptService {

	@Autowired
	private ScrapReceiptRepository scrapReceiptRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private InputRepository inputRepository;

	@Autowired
	private PartnerRepository partnerRepository;

	@Transactional(readOnly = true)
	public Page<ReportScrapReceiptDTO> reportMovement(Integer numberTicketId, Pageable pageable) {

		Page<ScrapReceiptProjection> page = scrapReceiptRepository.searchScrapReceiptByNumberTicket(numberTicketId,
				pageable);

		return page.map(ReportScrapReceiptDTO::new);
	}

	@Transactional(readOnly = true)
	public ScrapReceiptDTO findById(Long id) {
		try {
			Optional<ScrapReceipt> obj = scrapReceiptRepository.findById(id);
			ScrapReceipt entity = obj.orElseThrow(() -> new EntityNotFoundException("Receipt not found"));
			return new ScrapReceiptDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}

	}

	@Transactional
	public ScrapReceiptDTO insert(ScrapReceiptDTO dto) {

		// 1. Buscar ticket
		Ticket ticket = ticketRepository.findByNumTicket(dto.getNumTicketId())
				.orElseThrow(() -> new ResourceNotFoundException("Ticket não encontrado"));

		// 2. Somar amountScrap existentes do ticket (se tiver)
		BigDecimal somaAmountScrap = scrapReceiptRepository.sumAmountScrapByNumTicket(dto.getNumTicketId());
		if (somaAmountScrap == null) {
			somaAmountScrap = BigDecimal.ZERO;
		}

		// 3. Calcular novo total incluindo o dto.amountMaterial
		BigDecimal newScrap = somaAmountScrap.add(dto.getAmountScrap());

		// 4. Validar com netWeight do ticket
		if (newScrap.compareTo(ticket.getNetWeight()) > 0) {
			throw new IllegalArgumentException("Peso de ticket excedido! " + "Ticket nº: " + ticket.getNumTicket()
					+ " Peso Total: " + ticket.getNetWeight() + " kg");

		}

		ScrapReceipt entity = new ScrapReceipt();
		copyDtoToEntity(dto, entity);
		entity = scrapReceiptRepository.save(entity);
		return new ScrapReceiptDTO(entity);
	}

	@Transactional
	public ScrapReceiptDTO update(Long id, ScrapReceiptDTO dto) {
		try {
			// 1. Buscar entidade
			ScrapReceipt entity = scrapReceiptRepository.getReferenceById(id);

			// 2. Buscar ticket
			Ticket ticket = ticketRepository.findByNumTicket(dto.getNumTicketId())
					.orElseThrow(() -> new ResourceNotFoundException("Ticket não encontrado"));

			// 3. Somar amountMaterial de todos os movimentos do ticket, exceto o atual
			BigDecimal somaAmountScrap = Optional.ofNullable(
	                scrapReceiptRepository.sumAmountScrapByNumTicketExcludingId(dto.getNumTicketId(), id)
	        ).orElse(BigDecimal.ZERO);

			// 4. Calcular novo total
			BigDecimal newTotal = somaAmountScrap.add(dto.getAmountScrap());

			// 5. Validar
			if (newTotal.compareTo(ticket.getNetWeight()) > 0) {
				throw new IllegalArgumentException("Peso de ticket excedido! " + "Ticket nº: " + ticket.getNumTicket()
						+ " Peso Total: " + ticket.getNetWeight() + " kg");
			}

			copyDtoToEntity(dto, entity);
			entity = scrapReceiptRepository.save(entity);
			return new ScrapReceiptDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!scrapReceiptRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			scrapReceiptRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	public void copyDtoToEntity(ScrapReceiptDTO dto, ScrapReceipt entity) {
		entity.setAmountScrap(dto.getAmountScrap());
		entity.setUnitValue(dto.getUnitValue());
		entity.setMetalYield(dto.getMetalYield());

		// 🧮 Cálculo de totalValue
		BigDecimal amount = Objects.requireNonNull(dto.getAmountScrap(), "'amountScrap' não pode ser nulo.");
		BigDecimal unit = Objects.requireNonNull(dto.getUnitValue(), "'unitValue' não pode ser nulo.");
		entity.setTotalValue(amount.multiply(unit).setScale(2, RoundingMode.HALF_UP));

		// 🧮 Cálculo de metalWeight
		BigDecimal yield = Objects.requireNonNull(dto.getMetalYield(), "'metalYield' não pode ser nulo.");
		entity.setMetalWeight(amount.multiply(yield).setScale(2, RoundingMode.HALF_UP));

		// 🧮 Cálculo de slag
		BigDecimal metalWeight = Objects.requireNonNull(entity.getMetalWeight(), "'metalWeight' não pode ser nulo.");
		entity.setSlag(amount.subtract(metalWeight).setScale(2, RoundingMode.HALF_UP));

		
		Optional.ofNullable(dto.getNumTicketId()).ifPresent(id -> {
			Ticket ticket = ticketRepository.findByNumTicket(id)
					.orElseThrow(() -> new ResourceNotFoundException("Ticket não encontrado"));
			entity.setNumTicket(ticket);
		});

		try {
			TypeCosts expenses = TypeCosts.valueOf(dto.getCosts().toUpperCase());
			entity.setCosts(expenses);

		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de despesa inválida: " + dto.getCosts());
		}

		Optional.ofNullable(dto.getPartnerId()).ifPresent(id -> {
			Partner partner = partnerRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Parceiro não encontrado"));
			entity.setPartner(partner);
		});

		Optional.ofNullable(dto.getInputId()).ifPresent(id -> {
			Input input = inputRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Sucata não encontrado"));
			entity.setInput(input);
		});

		try {

			TypeTransactionReceipt transaction = TypeTransactionReceipt
					.valueOf(dto.getTransactionDescription().toUpperCase());
			entity.setTransaction(transaction);

		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de operação inválida: " + dto.getTransactionDescription());
		}
	}

}
