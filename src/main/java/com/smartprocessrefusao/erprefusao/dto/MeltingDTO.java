package com.smartprocessrefusao.erprefusao.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartprocessrefusao.erprefusao.entities.Melting;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class MeltingDTO {

	private Long meltingId;

	@NotNull(message = "Informe a data da fusão")
	private LocalDate dateMelting;

	@NotNull(message = "Informe o número da fusão")
	private Long numberMelting;

	@NotNull(message = "Informe a liga do tarugo")
	private String alloy;

	@NotNull(message = "Informe a polegada do tarugo")
	private String alloyPol;

	@NotNull(message = "Informe o comprimento do tarugo")
	private String alloyFootage;

	@NotNull(message = "Informe o parceiro")
	private Long partnerId;
	private String partnerName;

	@NotNull(message = "Informe o tipo da transação")
	private String typeTransaction;

	@NotNull(message = "Informe o forno")
	private Long machineId;
	private String machineName;

	@NotNull(message = "Informe a data e hora de início do carregamento")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime startOfFurnaceCharging;

	@NotNull(message = "Informe a data e hora de fim do carregamento")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime endOfFurnaceCharging;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime startOfFurnaceToFurnaceMetalTransfer;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime endOfFurnaceToFurnaceMetalTransfer;

//	@NotNull(message = "Informe a data e hora de início do vazamento")
//	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
//	private LocalDateTime startOfFurnaceTapping;

//	@NotNull(message = "Informe a data e hora de fim do vazamento")
//	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
//	private LocalDateTime endOfFurnaceTapping;

	private Duration totalChargingTime;
	private Duration totalTransferTime;
	private Duration totalCycleTime;

	private String observation;

	@NotEmpty(message = "Informe os itens da fusão")
	private List<MeltingItemDTO> meltingItems = new ArrayList<>();

	@NotEmpty(message = "Informe os colaboradores participantes da fusão")
	private List<EmployeeMeltingDTO> employees = new ArrayList<>();

	public MeltingDTO() {
	}

	public MeltingDTO(Long meltingId, LocalDate dateMelting, Long numberMelting, String alloy, String alloyPol,
			String alloyFootage, Long partnerId, String partnerName, String typeTransaction, Long machineId,
			String machineName, LocalDateTime startOfFurnaceCharging, LocalDateTime endOfFurnaceCharging,
			LocalDateTime startOfFurnaceToFurnaceMetalTransfer, LocalDateTime endOfFurnaceToFurnaceMetalTransfer,
			Duration totalChargingTime, Duration totalTransferTime, Duration totalCycleTime, String observation,
			List<MeltingItemDTO> meltingItems, List<EmployeeMeltingDTO> employees) {
		this.meltingId = meltingId;
		this.dateMelting = dateMelting;
		this.numberMelting = numberMelting;
		this.alloy = alloy;
		this.alloyPol = alloyPol;
		this.alloyFootage = alloyFootage;
		this.partnerId = partnerId;
		this.partnerName = partnerName;
		this.typeTransaction = typeTransaction;
		this.machineId = machineId;
		this.machineName = machineName;
		this.startOfFurnaceCharging = startOfFurnaceCharging;
		this.endOfFurnaceCharging = endOfFurnaceCharging;
		this.startOfFurnaceToFurnaceMetalTransfer = startOfFurnaceToFurnaceMetalTransfer;
		this.endOfFurnaceToFurnaceMetalTransfer = endOfFurnaceToFurnaceMetalTransfer;
		this.totalChargingTime = totalChargingTime;
		this.totalTransferTime = totalTransferTime;
		this.totalCycleTime = totalCycleTime;
		this.observation = observation;
		this.meltingItems = meltingItems;
		this.employees = employees;
	}

	public MeltingDTO(Melting entity) {
		meltingId = entity.getId();
		dateMelting = entity.getDateMelting();
		numberMelting = entity.getNumberMelting();

		alloy = entity.getAlloy().toString();
		alloyPol = entity.getAlloyPol().toString();
		alloyFootage = entity.getAlloyFootage().toString();

		if (entity.getPartner() != null) {
			partnerId = entity.getPartner().getId();
			partnerName = entity.getPartner().getName();
		}

		typeTransaction = entity.getTypeTransaction().toString();

		if (entity.getMachine() != null) {
			machineId = entity.getMachine().getId();
			machineName = entity.getMachine().getDescription();
		}

		startOfFurnaceCharging = entity.getStartOfFurnaceCharging();
		endOfFurnaceCharging = entity.getEndOfFurnaceCharging();
		startOfFurnaceToFurnaceMetalTransfer = entity.getStartOfFurnaceToFurnaceMetalTransfer();
		endOfFurnaceToFurnaceMetalTransfer = entity.getEndOfFurnaceToFurnaceMetalTransfer();
		totalChargingTime = entity.getTotalChargingTime();
		totalTransferTime = entity.getTotalTransferTime();
		totalCycleTime = entity.getTotalCycleTime();

		observation = entity.getObservation();

		employees = entity.getEmployees().stream().map(EmployeeMeltingDTO::new).toList();

		meltingItems = entity.getMeltingItems().stream().map(MeltingItemDTO::new).toList();
	}

	public Long getMeltingId() {
		return meltingId;
	}

	public LocalDate getDateMelting() {
		return dateMelting;
	}

	public Long getNumberMelting() {
		return numberMelting;
	}

	public String getAlloy() {
		return alloy;
	}

	public String getAlloyPol() {
		return alloyPol;
	}

	public String getAlloyFootage() {
		return alloyFootage;
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

	public Duration getTotalChargingTime() {
		return totalChargingTime;
	}

	public Duration getTotalTransferTime() {
		return totalTransferTime;
	}

	public Duration getTotalCycleTime() {
		return totalCycleTime;
	}

	public String getObservation() {
		return observation;
	}

	public List<MeltingItemDTO> getMeltingItems() {
		return meltingItems;
	}

	public List<EmployeeMeltingDTO> getEmployees() {
		return employees;
	}

}
