package com.smartprocessrefusao.erprefusao.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MeltingReportDTO {

	private Long meltingId;

	private LocalDate dateMelting;

	private Integer numberMelting;

	private Long partnerId;
	private String partnerName;

	private String typeTransaction;

	private Long machineId;
	private String machineName;

	private LocalDateTime startOfFurnaceCharging;

	private LocalDateTime endOfFurnaceCharging;

	private LocalDateTime startOfFurnaceToFurnaceMetalTransfer;

	private LocalDateTime endOfFurnaceToFurnaceMetalTransfer;

	private LocalDateTime startOfFurnaceTapping;

	private LocalDateTime endOfFurnaceTapping;

	private String totalChargingTime;
	private String totalTransferTime;
	private String totalTappingTime;
	private String totalCycleTime;
	private String observation;

	private List<EmployeeMeltingReportDTO> employees = new ArrayList<>();

	public MeltingReportDTO() {
	}

	public MeltingReportDTO(Long meltingId, LocalDate dateMelting, Integer numberMelting, Long partnerId,
			String partnerName, String typeTransaction, Long machineId, String machineName,
			LocalDateTime startOfFurnaceCharging, LocalDateTime endOfFurnaceCharging,
			LocalDateTime startOfFurnaceToFurnaceMetalTransfer, LocalDateTime endOfFurnaceToFurnaceMetalTransfer,
			LocalDateTime startOfFurnaceTapping, LocalDateTime endOfFurnaceTapping, String totalChargingTime,
			String totalTransferTime, String totalTappingTime, String totalCycleTime, String observation,
			List<EmployeeMeltingReportDTO> employees) {
		this.meltingId = meltingId;
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
		this.employees = employees;
	}

	public Long getMeltingId() {
		return meltingId;
	}

	public LocalDate getDateMelting() {
		return dateMelting;
	}

	public Integer getNumberMelting() {
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

	public String getTotalChargingTime() {
		return totalChargingTime;
	}

	public String getTotalTransferTime() {
		return totalTransferTime;
	}

	public String getTotalTappingTime() {
		return totalTappingTime;
	}

	public String getTotalCycleTime() {
		return totalCycleTime;
	}

	public String getObservation() {
		return observation;
	}

	public List<EmployeeMeltingReportDTO> getEmployees() {
		return employees;
	}

}
