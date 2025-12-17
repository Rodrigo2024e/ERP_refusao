package com.smartprocessrefusao.erprefusao.services;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smartprocessrefusao.erprefusao.dto.EmployeeMeltingDTO;
import com.smartprocessrefusao.erprefusao.dto.EmployeeMeltingReportDTO;
import com.smartprocessrefusao.erprefusao.dto.MeltingDTO;
import com.smartprocessrefusao.erprefusao.dto.MeltingReportDTO;
import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.entities.Machine;
import com.smartprocessrefusao.erprefusao.entities.Melting;
import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionOutGoing;
import com.smartprocessrefusao.erprefusao.projections.MeltingReportProjection;
import com.smartprocessrefusao.erprefusao.repositories.EmployeeRepository;
import com.smartprocessrefusao.erprefusao.repositories.MachineRepository;
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

	@Transactional(readOnly = true)
	public List<MeltingDTO> findAll() {
		List<Melting> list = meltingRepository.findAll();
		return list.stream().map(MeltingDTO::new).toList();
	}

	@Transactional(readOnly = true)
	public MeltingDTO findByNumberMelting(Long numberMelting) {
		Melting entity = meltingRepository.findByNumberMelting(numberMelting)
				.orElseThrow(() -> new ResourceNotFoundException("Melting não encontrado"));

		return new MeltingDTO(entity);
	}

	@Transactional
	public MeltingDTO insert(MeltingDTO dto) {

		// 1. Validação de número da fusão
		Long numberMelting = Optional.ofNullable(dto.getNumberMelting())
				.orElseThrow(() -> new IllegalArgumentException("O número da fusão é obrigatório."));
		meltingRepository.findByNumberMelting(numberMelting).ifPresent(e -> {
			throw new IllegalArgumentException("O número da fusão '" + numberMelting + "' já existe no sistema.");
		});

		Melting entity = new Melting();
		copyDtoToEntity(dto, entity);
		entity = meltingRepository.save(entity);
		return new MeltingDTO(entity);
	}

	@Transactional
	public MeltingDTO update(Long numberMelting, MeltingDTO dto) {

		Melting entity = meltingRepository.findByNumberMelting(numberMelting)
				.orElseThrow(() -> new ResourceNotFoundException("Fusão não encontrada. Número: " + numberMelting));

		// continua a lógica de update
		copyDtoToEntity(dto, entity);

		entity = meltingRepository.save(entity);
		return new MeltingDTO(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteByNumberMelting(Long numberMelting) {

		Melting melting = meltingRepository.findByNumberMelting(numberMelting)
				.orElseThrow(() -> new ResourceNotFoundException("Fusão não encontrada: " + numberMelting));

		// 🔥 REMOVE RELACIONAMENTO DOS DOIS LADOS
		for (Employee emp : melting.getEmployees()) {
			emp.getMeltings().remove(melting);
		}

		melting.getEmployees().clear();

		meltingRepository.delete(melting);
	}

	public void copyDtoToEntity(MeltingDTO dto, Melting entity) {
		entity.setDateMelting(dto.getDateMelting());
		entity.setNumberMelting(dto.getNumberMelting());

		Optional.ofNullable(dto.getPartnerId()).ifPresent(id -> {
			Partner partner = partnerRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Parceiro não encontrado"));
			entity.setPartner(partner);
		});

		try {
			TypeTransactionOutGoing typeTransaction = TypeTransactionOutGoing.fromDescription(dto.getTypeTransaction());
			entity.setTypeTransaction(typeTransaction);
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Tipo de operação inválida: " + dto.getTypeTransaction());
		}

		Optional.ofNullable(dto.getMachineId()).ifPresent(id -> {
			Machine machine = machineRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Forno não encontrado"));
			entity.setMachine(machine);
		});

		entity.setStartOfFurnaceCharging(dto.getStartOfFurnaceCharging());
		entity.setEndOfFurnaceCharging(dto.getEndOfFurnaceCharging());
		entity.setStartOfFurnaceToFurnaceMetalTransfer(dto.getStartOfFurnaceToFurnaceMetalTransfer());
		entity.setEndOfFurnaceToFurnaceMetalTransfer(dto.getEndOfFurnaceToFurnaceMetalTransfer());
		entity.setStartOfFurnaceTapping(dto.getStartOfFurnaceTapping());
		entity.setEndOfFurnaceTapping(dto.getEndOfFurnaceTapping());
		entity.setObservation(dto.getObservation() != null ? dto.getObservation().toUpperCase() : null);

		entity.getEmployees().clear();
		for (EmployeeMeltingDTO empDto : dto.getEmployees()) {
			Employee employee = employeeRepository.findById(empDto.getEmployeeId())
					.orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado"));

			entity.getEmployees().add(employee);
			employee.getMeltings().add(entity);
		}

		// Calculate cycleChargingTime
		Duration cycleChargingTime = CalcDuration(entity.getStartOfFurnaceCharging(), entity.getEndOfFurnaceCharging());
		entity.setTotalChargingTime(cycleChargingTime);

		// Calculate cycleTransferTime
		Duration cycleTransferTime = CalcDuration(entity.getStartOfFurnaceToFurnaceMetalTransfer(),
				entity.getEndOfFurnaceToFurnaceMetalTransfer());
		entity.setTotalTransferTime(cycleTransferTime);

		// Calculate cycleTappingTime
		Duration cycleTappingTime = CalcDuration(entity.getStartOfFurnaceTapping(), entity.getEndOfFurnaceTapping());
		entity.setTotalTappingTime(cycleTappingTime);

		// Calculate totalCycleTime
		Duration totalCycleTime = CalcDuration(entity.getStartOfFurnaceCharging(), entity.getEndOfFurnaceTapping());
		entity.setTotalCycleTime(totalCycleTime);

	}

	public Duration CalcDuration(LocalDateTime start, LocalDateTime end) {
		if (start == null || end == null)
			throw new IllegalArgumentException("Datas de início e fim são obrigatórias");
		return Duration.between(start, end);
	}

	@Transactional(readOnly = true)
	public Page<MeltingReportDTO> searchMeltingReport(Long partnerId, Long meltingNumber, LocalDate startDate,
			LocalDate endDate, Pageable pageable) {

		Page<MeltingReportProjection> flat = meltingRepository.searchMeltingReport(partnerId, meltingNumber, startDate,
				endDate, pageable);

		Map<Long, MeltingReportDTO> grouped = new LinkedHashMap<>();

		for (MeltingReportProjection row : flat.getContent()) {

			MeltingReportDTO melting = grouped.computeIfAbsent(row.getMeltingId(),
					id -> new MeltingReportDTO(row.getMeltingId(), row.getDateMelting(), row.getNumberMelting(),
							row.getPartnerId(), row.getPartnerName(), row.getTypeTransaction(), row.getMachineId(),
							row.getMachineName(), row.getStartOfFurnaceCharging(), row.getEndOfFurnaceCharging(),
							row.getStartOfFurnaceToFurnaceMetalTransfer(), row.getEndOfFurnaceToFurnaceMetalTransfer(),
							row.getStartOfFurnaceTapping(), row.getEndOfFurnaceTapping(),
							formatDuration(row.getTotalChargingTime()), formatDuration(row.getTotalTransferTime()),
							formatDuration(row.getTotalTappingTime()), formatDuration(row.getTotalCycleTime()),
							row.getObservation(), new ArrayList<>()));

			melting.getEmployees().add(new EmployeeMeltingReportDTO(row.getEmployeeId(), row.getEmployeeName(),
					row.getDepartamentId(), row.getDepartamentName(), row.getDepartamentProcess()

			));
		}

		return new PageImpl<>(new ArrayList<>(grouped.values()), pageable, flat.getTotalElements());
	}

	private String formatDuration(Long nanos) {
		if (nanos == null)
			return null;

		Duration d = Duration.ofNanos(nanos);

		long hours = d.toHours();
		long minutes = d.minusHours(hours).toMinutes();
		long seconds = d.minusHours(hours).minusMinutes(minutes).getSeconds();

		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}

}
