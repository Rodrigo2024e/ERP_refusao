package com.smartprocessrefusao.erprefusao.services;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.EmployeeMeltingDTO;
import com.smartprocessrefusao.erprefusao.dto.EmployeeMeltingReportDTO;
import com.smartprocessrefusao.erprefusao.dto.MeltingDTO;
import com.smartprocessrefusao.erprefusao.dto.MeltingItemDTO;
import com.smartprocessrefusao.erprefusao.dto.MeltingReportDTO;
import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.entities.Machine;
import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.entities.Melting;
import com.smartprocessrefusao.erprefusao.entities.MeltingItem;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.entities.PK.MeltingItemPK;
import com.smartprocessrefusao.erprefusao.projections.MeltingProjection;
import com.smartprocessrefusao.erprefusao.repositories.EmployeeRepository;
import com.smartprocessrefusao.erprefusao.repositories.MachineRepository;
import com.smartprocessrefusao.erprefusao.repositories.MaterialRepository;
import com.smartprocessrefusao.erprefusao.repositories.MeltingItemRepository;
import com.smartprocessrefusao.erprefusao.repositories.MeltingRepository;
import com.smartprocessrefusao.erprefusao.repositories.PartnerRepository;
import com.smartprocessrefusao.erprefusao.services.exceptions.ResourceNotFoundException;

@Service
public class MeltingService {

	@Autowired
	private MeltingRepository meltingRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private PartnerRepository partnerRepository;

	@Autowired
	private MachineRepository machineRepository;

	@Autowired
	private MaterialRepository materialRepository;

	@Autowired
	private MeltingItemRepository meltingItemRepository;

	/* ===================== FIND ===================== */

	@Transactional(readOnly = true)
	public List<MeltingDTO> findAll() {
		return meltingRepository.findAll().stream().map(MeltingDTO::new).toList();
	}

	@Transactional(readOnly = true)
	public MeltingDTO findByNumberMelting(Long numberMelting) {
		Melting entity = meltingRepository.findByNumberMelting(numberMelting)
				.orElseThrow(() -> new ResourceNotFoundException("Fusão não encontrada"));
		return new MeltingDTO(entity);
	}

	/* ===================== INSERT ===================== */

	@Transactional
	public MeltingDTO insert(MeltingDTO dto) {

		Long numberMelting = Optional.ofNullable(dto.getNumberMelting())
				.orElseThrow(() -> new IllegalArgumentException("Número da fusão é obrigatório"));

		if (meltingRepository.existsByNumberMelting(numberMelting)) {
			throw new IllegalArgumentException("Número da fusão já existe: " + numberMelting);
		}

		Melting entity = new Melting();
		copyDtoToEntity(dto, entity);

		// 1️⃣ SALVA MELTING PRIMEIRO
		entity = meltingRepository.save(entity);

		// 2️⃣ RELACIONA EMPLOYEES (N:N)
		relateEmployees(dto, entity);

		// 3️⃣ CRIA MELTING ITEMS
		buildMeltingItems(dto, entity);

		return new MeltingDTO(entity);
	}

	/* ===================== UPDATE ===================== */

	@Transactional
	public MeltingDTO update(Long numberMelting, MeltingDTO dto) {

		// 🔹 1. Valida a fusão
		Melting entity = meltingRepository.findByNumberMelting(numberMelting)
				.orElseThrow(() -> new ResourceNotFoundException("Fusão não encontrada: " + numberMelting));

		// 🔹 2. Campos simples
		copyDtoToEntity(dto, entity);

		// 🔹 3. Employees (ManyToMany)
		syncEmployees(dto, entity);

		// 🔹 4. Melting Items (PK composta + validação de material)
		syncMeltingItems(dto, entity);

		entity = meltingRepository.save(entity);
		return new MeltingDTO(entity);
	}

	/* ===================== DELETE ===================== */

	@Transactional
	public void deleteByNumberMelting(Long numberMelting) {

		Melting melting = meltingRepository.findByNumberMelting(numberMelting)
				.orElseThrow(() -> new ResourceNotFoundException("Fusão não encontrada: " + numberMelting));

		// remove relação N:N
		for (Employee emp : melting.getEmployees()) {
			emp.getMeltings().remove(melting);
		}
		melting.getEmployees().clear();

		// remove itens
		melting.getMeltingItems().clear();

		meltingRepository.delete(melting);
	}

	/* ===================== CORE ===================== */

	private void copyDtoToEntity(MeltingDTO dto, Melting entity) {

		entity.setDateMelting(dto.getDateMelting());
		entity.setNumberMelting(dto.getNumberMelting());
		entity.setObservation(dto.getObservation());
		entity.setTypeTransaction(dto.getTypeTransaction());

		if (dto.getPartnerId() != null) {
			Partner partner = partnerRepository.findById(dto.getPartnerId())
					.orElseThrow(() -> new ResourceNotFoundException("Parceiro não encontrado"));
			entity.setPartner(partner);
		}

		if (dto.getMachineId() != null) {
			Machine machine = machineRepository.findById(dto.getMachineId())
					.orElseThrow(() -> new ResourceNotFoundException("Máquina não encontrada"));
			entity.setMachine(machine);
		}

		entity.setStartOfFurnaceCharging(dto.getStartOfFurnaceCharging());
		entity.setEndOfFurnaceCharging(dto.getEndOfFurnaceCharging());
		entity.setStartOfFurnaceToFurnaceMetalTransfer(dto.getStartOfFurnaceToFurnaceMetalTransfer());
		entity.setEndOfFurnaceToFurnaceMetalTransfer(dto.getEndOfFurnaceToFurnaceMetalTransfer());
		entity.setStartOfFurnaceTapping(dto.getStartOfFurnaceTapping());
		entity.setEndOfFurnaceTapping(dto.getEndOfFurnaceTapping());

		calcTimes(entity);
	}

	private void relateEmployees(MeltingDTO dto, Melting entity) {

		if (dto.getEmployees() == null)
			return;

		for (EmployeeMeltingDTO empDto : dto.getEmployees()) {
			Employee employee = employeeRepository.findById(empDto.getEmployeeId())
					.orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado"));

			entity.getEmployees().add(employee);
			employee.getMeltings().add(entity);
		}
	}

	private void buildMeltingItems(MeltingDTO dto, Melting entity) {

		if (dto.getMeltingItems() == null)
			return;

		int sequence = 1;
		Map<Long, Material> materialCache = new HashMap<>();

		for (MeltingItemDTO itemDto : dto.getMeltingItems()) {

			Long code = Optional.ofNullable(itemDto.getMaterialCode())
					.orElseThrow(() -> new IllegalArgumentException("Código do material obrigatório"));

			Material material = materialCache.computeIfAbsent(code, c -> materialRepository.findByMaterialCode(c)
					.orElseThrow(() -> new ResourceNotFoundException("Material não encontrado: " + c)));

			MeltingItem item = new MeltingItem();
			item.getId().setMelting(entity);
			item.getId().setMaterial(material);
			item.getId().setItemSequence(sequence++);

			BigDecimal quantity = nz(itemDto.getQuantity());
			BigDecimal avgYield = nz(itemDto.getAverageRecoveryYield());
			BigDecimal avgPrice = nz(itemDto.getAveragePrice());

			BigDecimal quantityMco = quantity.multiply(avgYield);

			item.setQuantity(quantity);
			item.setAveragePrice(avgPrice);
			item.setTotalValue(quantity.multiply(avgPrice));
			item.setAverageRecoveryYield(avgYield);
			item.setQuantityMco(quantityMco);
			item.setSlagWeight(quantity.subtract(quantityMco));

			entity.getMeltingItems().add(item);
		}
	}

	private void calcTimes(Melting entity) {

		entity.setTotalChargingTime(calcDuration(entity.getStartOfFurnaceCharging(), entity.getEndOfFurnaceCharging()));

		entity.setTotalTransferTime(calcDuration(entity.getStartOfFurnaceToFurnaceMetalTransfer(),
				entity.getEndOfFurnaceToFurnaceMetalTransfer()));

		entity.setTotalTappingTime(calcDuration(entity.getStartOfFurnaceTapping(), entity.getEndOfFurnaceTapping()));

		entity.setTotalCycleTime(calcDuration(entity.getStartOfFurnaceCharging(), entity.getEndOfFurnaceTapping()));
	}

	// REPORT
	public Page<MeltingReportDTO> findDetails(
			Long partnerId, 
			Long numberMelting, 
			LocalDate startDate,
			LocalDate endDate, 
			Pageable pageable) {

		Page<MeltingProjection> page = meltingRepository.findMeltingBase(
				partnerId, 
				numberMelting, 
				startDate, 
				endDate,
				pageable);

		// 🔹 IDs das fusões (fora do map!)
		List<Long> meltingIds = page.stream().map(MeltingProjection::getMeltingId).toList();

		// 🔹 1 query só — Itens
		Map<Long, List<MeltingItemDTO>> itemsMap = meltingItemRepository.findItemsByMeltingIds(meltingIds).stream()
				.map(p -> new MeltingItemDTO(
						p.getMeltingId(), 
						p.getItemSequence(), 
						p.getMaterialCode(),
						p.getDescription(), 
						p.getQuantity(), 
						p.getAveragePrice(), 
						p.getTotalValue(),
						p.getAverageRecoveryYield(), 
						p.getQuantityMco(), 
						p.getSlagWeight()))
				.collect(Collectors.groupingBy(MeltingItemDTO::getMeltingId));

		// 🔹 1 query só — Employees
		Map<Long, List<EmployeeMeltingReportDTO>> employeesMap = employeeRepository
				.findEmployeesByMeltingIds(meltingIds).stream()
				.map(p -> new EmployeeMeltingReportDTO(
						p.getMeltingId(), 
						p.getEmployeeId(), 
						p.getEmployeeName(),
						p.getDepartamentId(), 
						p.getDepartamentName(), 
						p.getDepartamentProcess(),
						p.getEmployeePosition()))
				.collect(Collectors.groupingBy(EmployeeMeltingReportDTO::getMeltingId));

		// 🔹 Montagem final do DTO (SEM N+1)
		return page.map(p -> new MeltingReportDTO(
				p.getMeltingId(), 
				p.getDateMelting(), 
				p.getNumberMelting(),
				p.getPartnerId(), 
				p.getPartnerName(), 
				p.getTypeTransaction(), 
				p.getMachineId(), 
				p.getMachineName(),
				p.getStartOfFurnaceCharging(), 
				p.getEndOfFurnaceCharging(), 
				p.getStartOfFurnaceToFurnaceMetalTransfer(),
				p.getEndOfFurnaceToFurnaceMetalTransfer(), 
				p.getStartOfFurnaceTapping(), 
				p.getEndOfFurnaceTapping(),
				p.getTotalChargingTime(), 
				p.getTotalTransferTime(), 
				p.getTotalTappingTime(), 
				p.getTotalCycleTime(),
				p.getObservation(), 
				itemsMap.getOrDefault(
						p.getMeltingId(), List.of()),
				employeesMap.getOrDefault(p.getMeltingId(), List.of())));
	}

	// Calculate times melting
	private Duration calcDuration(LocalDateTime start, LocalDateTime end) {
		if (start == null || end == null)
			return null;
		return Duration.between(start, end);
	}

	// Return Zero when null value
	private BigDecimal nz(BigDecimal value) {
		return value != null ? value : BigDecimal.ZERO;
	}

	// sincronização com update
	private void syncEmployees(MeltingDTO dto, Melting entity) {

		// remove vínculo antigo (lado inverso)
		for (Employee e : entity.getEmployees()) {
			e.getMeltings().remove(entity);
		}
		entity.getEmployees().clear();

		if (dto.getEmployees() == null) {
			return;
		}

		for (EmployeeMeltingDTO empDto : dto.getEmployees()) {
			Employee employee = employeeRepository.getReferenceById(empDto.getEmployeeId());
			entity.getEmployees().add(employee);
			employee.getMeltings().add(entity);
		}
	}

	// sincronização com update
	private void syncMeltingItems(MeltingDTO dto, Melting entity) {

		// 🔥 orphanRemoval garante DELETE físico
		entity.getMeltingItems().clear();

		if (dto.getMeltingItems() == null || dto.getMeltingItems().isEmpty()) {
			return;
		}

		int sequence = 1;

		for (MeltingItemDTO itemDto : dto.getMeltingItems()) {

			// 🔒 Validação FORTE do material (EVITA EntityNotFoundException)
			Material material = materialRepository.findById(itemDto.getMaterialCode())
					.orElseThrow(() -> new ResourceNotFoundException(
							"Material não encontrado. Código: " + itemDto.getMaterialCode()));

			// 🔒 Normalização (NUNCA deixa null passar)
			BigDecimal quantity = nz(itemDto.getQuantity());
			BigDecimal averagePrice = nz(itemDto.getAveragePrice());
			BigDecimal averageRecoveryYield = nz(itemDto.getAverageRecoveryYield());

			// 🔢 Cálculos seguros
			BigDecimal totalValue = quantity.multiply(averagePrice);
			BigDecimal quantityMco = quantity.multiply(averageRecoveryYield);
			BigDecimal slagWeight = quantity.subtract(quantityMco);

			MeltingItem item = new MeltingItem();

			MeltingItemPK pk = new MeltingItemPK();
			pk.setMelting(entity); // 🔥 essencial
			pk.setMaterial(material); // ✅ material VALIDADO
			pk.setItemSequence(sequence++);

			item.setId(pk);
			item.setQuantity(quantity);
			item.setAveragePrice(averagePrice);
			item.setAverageRecoveryYield(averageRecoveryYield);
			item.setTotalValue(totalValue);
			item.setQuantityMco(quantityMco);
			item.setSlagWeight(slagWeight);

			entity.getMeltingItems().add(item);
		}
	}
}