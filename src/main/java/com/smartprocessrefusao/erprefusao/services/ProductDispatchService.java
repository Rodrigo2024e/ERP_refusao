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

import com.smartprocessrefusao.erprefusao.dto.ProductDispatchDTO;
import com.smartprocessrefusao.erprefusao.dto.ProductDispatchReportDTO;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.Product;
import com.smartprocessrefusao.erprefusao.entities.ProductDispatch;
import com.smartprocessrefusao.erprefusao.entities.Ticket;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionOutGoing;
import com.smartprocessrefusao.erprefusao.projections.ProductDispatchReportProjection;
import com.smartprocessrefusao.erprefusao.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.repositories.ProductDispatchRepository;
import com.smartprocessrefusao.erprefusao.repositories.ProductRepository;
import com.smartprocessrefusao.erprefusao.repositories.TicketRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.DatabaseException;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductDispatchService {

	@Autowired
	private ProductDispatchRepository productDispatchRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private PartnerRepository partnerRepository;

	@Transactional(readOnly = true)
	public Page<ProductDispatchReportDTO> reportDispatch(Integer numberTicketId, Pageable pageable) {

		Page<ProductDispatchReportProjection> page = productDispatchRepository
				.searchProductDispatchByNumberTicket(numberTicketId, pageable);

		return page.map(ProductDispatchReportDTO::new);
	}

	@Transactional(readOnly = true)
	public ProductDispatchDTO findById(Long id) {
		try {
			Optional<ProductDispatch> obj = productDispatchRepository.findById(id);
			ProductDispatch entity = obj.orElseThrow(() -> new EntityNotFoundException("Dispatch not found"));
			return new ProductDispatchDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}

	}

	@Transactional
	public ProductDispatchDTO insert(ProductDispatchDTO dto) {

		// 1. Buscar ticket
		Ticket ticket = ticketRepository.findByNumTicket(dto.getNumTicketId())
				.orElseThrow(() -> new ResourceNotFoundException("Ticket não encontrado"));

		// 2. Somar amountScrap existentes do ticket (se tiver)
		BigDecimal somaAmountProduct = productDispatchRepository.sumAmountProductByNumTicket(dto.getNumTicketId());
		if (somaAmountProduct == null) {
			somaAmountProduct = BigDecimal.ZERO;
		}

		// 3. Calcular novo total incluindo o dto.amountMaterial
		BigDecimal newScrap = somaAmountProduct.add(dto.getAmountProduct());

		// 4. Validar com netWeight do ticket
		if (newScrap.compareTo(ticket.getNetWeight()) > 0) {
			throw new IllegalArgumentException("Peso de ticket excedido! " + "Ticket nº: " + ticket.getNumTicket()
					+ " Peso Total: " + ticket.getNetWeight() + " kg");

		}

		ProductDispatch entity = new ProductDispatch();
		copyDtoToEntity(dto, entity);
		entity = productDispatchRepository.save(entity);
		return new ProductDispatchDTO(entity);
	}

	@Transactional
	public ProductDispatchDTO update(Long id, ProductDispatchDTO dto) {
		try {
			ProductDispatch entity = productDispatchRepository.getReferenceById(id);

			// 1. Buscar ticket
			Ticket ticket = ticketRepository.findByNumTicket(dto.getNumTicketId())
					.orElseThrow(() -> new ResourceNotFoundException("Ticket não encontrado"));

			// 2. Somar amountMaterial de todos os movimentos do ticket, exceto o atual
			BigDecimal somaAmountProduct = Optional.ofNullable(
					productDispatchRepository.sumAmountProductByNumTicketExcludingId(dto.getNumTicketId(), id)
	        ).orElse(BigDecimal.ZERO);

			// 3. Calcular novo total
			BigDecimal newTotal = somaAmountProduct.add(dto.getAmountProduct());

			// 4. Validar
			if (newTotal.compareTo(ticket.getNetWeight()) > 0) {
				throw new IllegalArgumentException("Peso de ticket excedido! " + "Ticket nº: " + ticket.getNumTicket()
						+ " Peso Total: " + ticket.getNetWeight() + " kg");
			}

			copyDtoToEntity(dto, entity);
			entity = productDispatchRepository.save(entity);
			return new ProductDispatchDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!productDispatchRepository.existsById(id)) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		try {
			productDispatchRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	public void copyDtoToEntity(ProductDispatchDTO dto, ProductDispatch entity) {
		entity.setAmountProduct(dto.getAmountProduct());
		entity.setUnitValue(dto.getUnitValue());

		// 🧮 Cálculo de totalValue
		BigDecimal amount = Objects.requireNonNull(dto.getAmountProduct(), "'amountProduct' não pode ser nulo.");
		BigDecimal unit = Objects.requireNonNull(dto.getUnitValue(), "'unitValue' não pode ser nulo.");
		entity.setTotalValue(amount.multiply(unit).setScale(2, RoundingMode.HALF_UP));

		Optional.ofNullable(dto.getNumTicketId()).ifPresent(id -> {
			Ticket ticket = ticketRepository.findByNumTicket(id)
					.orElseThrow(() -> new ResourceNotFoundException("Ticket não encontrada"));
			entity.setNumTicket(ticket);
		});

		Optional.ofNullable(dto.getPartnerId()).ifPresent(id -> {
			Partner partner = partnerRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Parceiro não encontrado"));
			entity.setPartner(partner);
		});

		Optional.ofNullable(dto.getProductId()).ifPresent(id -> {
			Product product = productRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
			entity.setProduct(product);
		});

		try {

			TypeTransactionOutGoing transaction = TypeTransactionOutGoing
					.valueOf(dto.getTransactionDescription().toUpperCase());
			entity.setTransaction(transaction);

		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de operação inválida: " + dto.getTransactionDescription());
		}
	}

}
