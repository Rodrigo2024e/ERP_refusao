package com.smartprocessrefusao.erprefusao.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FlatMeltingEmployeeDTO {

	private Long meltingId;
	private LocalDate dateMelting;
	private Integer numberMelting;
	private String typeTransaction;

	private LocalDateTime startOfFurnaceCharging;
	private LocalDateTime endOfFurnaceCharging;

	private LocalDateTime startOfFurnaceToFurnaceMetalTransfer;
	private LocalDateTime endOfFurnaceToFurnaceMetalTransfer;

	private LocalDateTime startOfFurnaceTapping;
	private LocalDateTime endOfFurnaceTapping;

	private Long totalChargingTime;
	private Long totalTransferTime;
	private Long totalTappingTime;
	private Long totalCycleTime;

	private String observation;

	private Long partnerId;
	private String partnerName;

	private Long employeeId;
	private String employeeRole;

	private Long departamentId;
	private String departamentName;
	private String departamentProcess;

	public FlatMeltingEmployeeDTO(Long meltingId, LocalDate dateMelting, Integer numberMelting, String typeTransaction,

			LocalDateTime startOfFurnaceCharging, LocalDateTime endOfFurnaceCharging,
			LocalDateTime startOfFurnaceToFurnaceMetalTransfer, LocalDateTime endOfFurnaceToFurnaceMetalTransfer,
			LocalDateTime startOfFurnaceTapping, LocalDateTime endOfFurnaceTapping,

			Long totalChargingTime, Long totalTransferTime, Long totalTappingTime, Long totalCycleTime,

			String observation,

			Long partnerId, String partnerName,

			Long employeeId, String employeeRole,

			Long departamentId, String departamentName, String departamentProcess) {
		this.meltingId = meltingId;
		this.dateMelting = dateMelting;
		this.numberMelting = numberMelting;
		this.typeTransaction = typeTransaction;

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

		this.partnerId = partnerId;
		this.partnerName = partnerName;

		this.employeeId = employeeId;
		this.employeeRole = employeeRole;

		this.departamentId = departamentId;
		this.departamentName = departamentName;
		this.departamentProcess = departamentProcess;
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

	public String getTypeTransaction() {
		return typeTransaction;
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

	public Long getTotalChargingTime() {
		return totalChargingTime;
	}

	public Long getTotalTransferTime() {
		return totalTransferTime;
	}

	public Long getTotalTappingTime() {
		return totalTappingTime;
	}

	public Long getTotalCycleTime() {
		return totalCycleTime;
	}

	public String getObservation() {
		return observation;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public String getEmployeeRole() {
		return employeeRole;
	}

	public Long getDepartamentId() {
		return departamentId;
	}

	public String getDepartamentName() {
		return departamentName;
	}

	public String getDepartamentProcess() {
		return departamentProcess;
	}

}
