package com.smartprocessrefusao.erprefusao.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.smartprocessrefusao.erprefusao.projections.MeltingEmployeeProjection;
import com.smartprocessrefusao.erprefusao.projections.MeltingItemProjection;

public class MeltingDetailsDTO {

	private Long meltingId;
	private LocalDate dateMelting;
	private Long numberMelting;

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

	private Duration totalChargingTime;
	private Duration totalTransferTime;
	private Duration totalTappingTime;
	private Duration totalCycleTime;

	private String observation;

	private List<MeltingItemProjection> meltingItems;
	private List<MeltingEmployeeProjection> employees;

	public MeltingDetailsDTO() {
	}

	public MeltingDetailsDTO(Long meltingId, LocalDate dateMelting, Long numberMelting, Long partnerId,
			String partnerName, String typeTransaction, Long machineId, String machineName,
			LocalDateTime startOfFurnaceCharging, LocalDateTime endOfFurnaceCharging,
			LocalDateTime startOfFurnaceToFurnaceMetalTransfer, LocalDateTime endOfFurnaceToFurnaceMetalTransfer,
			LocalDateTime startOfFurnaceTapping, LocalDateTime endOfFurnaceTapping, Duration totalChargingTime,
			Duration totalTransferTime, Duration totalTappingTime, Duration totalCycleTime, String observation,
			List<MeltingItemProjection> meltingItems, List<MeltingEmployeeProjection> employees) {
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
		this.meltingItems = List.copyOf(meltingItems);
		this.employees = List.copyOf(employees);

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

	public List<MeltingItemProjection> getMeltingItems() {
		return meltingItems;
	}

	public List<MeltingEmployeeProjection> getEmployees() {
		return employees;
	}

}
