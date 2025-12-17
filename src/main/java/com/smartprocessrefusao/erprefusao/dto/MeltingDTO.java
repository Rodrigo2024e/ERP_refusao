package com.smartprocessrefusao.erprefusao.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartprocessrefusao.erprefusao.entities.Melting;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public class MeltingDTO {

	private Long id;

	@NotNull(message = "Informe a data da fusão")
	private LocalDate dateMelting;

	@NotNull(message = "Informe o número da fusão")
	@Column(name = "numberMelting", unique = true)
	private Long numberMelting;

	@NotNull(message = "Informe o id do parceiro")
	private Long partnerId;
	private String partnerName;

	@NotNull(message = "Informe o tipo da transação")
	private String typeTransaction;

	@NotNull(message = "Informe o id do forno")
	private Long machineId;
	private String machineName;

	@NotNull(message = "Informe a data e a hora do início do carregamento")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime startOfFurnaceCharging;

	@NotNull(message = "Informe a data e a hora do fim do carregamento")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime endOfFurnaceCharging;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime startOfFurnaceToFurnaceMetalTransfer;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime endOfFurnaceToFurnaceMetalTransfer;

	@NotNull(message = "Informe a data e a hora do início do vazamento")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime startOfFurnaceTapping;

	@NotNull(message = "Informe a data e a hora do fim do vazamento")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime endOfFurnaceTapping;

	private Duration totalChargingTime;
	private Duration totalTransferTime;
	private Duration totalTappingTime;
	private Duration totalCycleTime;
	private String observation;

	private List<EmployeeMeltingDTO> employees = new ArrayList<>();

	public MeltingDTO() {
	}

	public MeltingDTO(Long id, LocalDate dateMelting, Long numberMelting, Long partnerId, String partnerName,
			String typeTransaction, Long machineId, String machineName, LocalDateTime startOfFurnaceCharging,
			LocalDateTime endOfFurnaceCharging, LocalDateTime startOfFurnaceToFurnaceMetalTransfer,
			LocalDateTime endOfFurnaceToFurnaceMetalTransfer, LocalDateTime startOfFurnaceTapping,
			LocalDateTime endOfFurnaceTapping, Duration totalChargingTime, Duration totalTransferTime,
			Duration totalTappingTime, Duration totalCycleTime, String observation) {
		this.id = id;
		this.dateMelting = dateMelting;
		this.numberMelting = numberMelting;
		this.partnerId = partnerId;
		this.partnerName = partnerName;
		this.typeTransaction = typeTransaction;
		this.machineId = machineId;
		this.machineName = machineName;
		this.startOfFurnaceCharging = startOfFurnaceCharging;
		this.endOfFurnaceCharging = endOfFurnaceCharging;
		this.startOfFurnaceToFurnaceMetalTransfer = startOfFurnaceToFurnaceMetalTransfer;
		this.endOfFurnaceToFurnaceMetalTransfer = endOfFurnaceToFurnaceMetalTransfer;
		this.startOfFurnaceTapping = startOfFurnaceTapping;
		this.endOfFurnaceTapping = endOfFurnaceTapping;
		this.totalChargingTime = totalChargingTime;
		this.totalTransferTime = totalTransferTime;
		this.totalTappingTime = totalTappingTime;
		this.totalCycleTime = totalCycleTime;
		this.observation = observation;
	}

	public MeltingDTO(Melting entity) {
		id = entity.getId();
		dateMelting = entity.getDateMelting();
		numberMelting = entity.getNumberMelting();
		partnerId = entity.getPartner().getId();
		partnerName = entity.getPartner().getName();
		typeTransaction = entity.getTypeTransaction().toString();
		machineId = entity.getMachine().getId();
		machineName = entity.getMachine().getDescription();
		startOfFurnaceCharging = entity.getStartOfFurnaceCharging();
		endOfFurnaceCharging = entity.getEndOfFurnaceCharging();
		startOfFurnaceToFurnaceMetalTransfer = entity.getStartOfFurnaceToFurnaceMetalTransfer();
		endOfFurnaceToFurnaceMetalTransfer = entity.getEndOfFurnaceToFurnaceMetalTransfer();
		startOfFurnaceTapping = entity.getStartOfFurnaceTapping();
		endOfFurnaceTapping = entity.getEndOfFurnaceTapping();
		totalChargingTime = entity.getTotalChargingTime();
		totalTransferTime = entity.getTotalTransferTime();
		totalTappingTime = entity.getTotalTappingTime();
		totalCycleTime = entity.getTotalCycleTime();
		observation = entity.getObservation();

		employees = entity.getEmployees().stream().map(EmployeeMeltingDTO::new).toList();

	}
	
		public Long getId() {
		return id;
	}

	public LocalDate getDateMelting() {
		return dateMelting;
	}

	public Long getNumberMelting() {
		return numberMelting;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public String getTypeTransaction() {
		return typeTransaction;
	}

	public Long getMachineId() {
		return machineId;
	}

	public String getMachineName() {
		return machineName;
	}

	public LocalDateTime getStartOfFurnaceCharging() {
		return startOfFurnaceCharging;
	}

	public LocalDateTime getEndOfFurnaceCharging() {
		return endOfFurnaceCharging;
	}

	public LocalDateTime getStartOfFurnaceToFurnaceMetalTransfer() {
		return startOfFurnaceToFurnaceMetalTransfer;
	}

	public LocalDateTime getEndOfFurnaceToFurnaceMetalTransfer() {
		return endOfFurnaceToFurnaceMetalTransfer;
	}

	public LocalDateTime getStartOfFurnaceTapping() {
		return startOfFurnaceTapping;
	}

	public LocalDateTime getEndOfFurnaceTapping() {
		return endOfFurnaceTapping;
	}

	public Duration getTotalChargingTime() {
		return totalChargingTime;
	}

	public Duration getTotalTransferTime() {
		return totalTransferTime;
	}

	public Duration getTotalTappingTime() {
		return totalTappingTime;
	}

	public Duration getTotalCycleTime() {
		return totalCycleTime;
	}

	public String getObservation() {
		return observation;
	}

	public List<EmployeeMeltingDTO> getEmployees() {
		return employees;
	}

}
