package com.smartprocessrefusao.erprefusao.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.smartprocessrefusao.erprefusao.projections.MeltingProjection;

public class MeltingReportDTO {

	private Long meltingId;
	private LocalDate dateMelting;

	private Long numberMelting;

	private String alloy;

	private String alloyPol;

	private String alloyFootage;

	private Long partnerId;
	private String partnerName;

	private String typeTransaction;

	private Long machineId;
	private String machineName;

	private LocalDateTime startOfFurnaceCharging;

	private LocalDateTime endOfFurnaceCharging;

	private LocalDateTime startOfFurnaceToFurnaceMetalTransfer;

	private LocalDateTime endOfFurnaceToFurnaceMetalTransfer;

	private Duration totalChargingTime;
	private Duration totalTransferTime;

	private Duration totalCycleTime;
	private String observation;

	private List<MeltingItemDTO> meltingItems = new ArrayList<>();
	private List<EmployeeMeltingReportDTO> employees = new ArrayList<>();

	public MeltingReportDTO() {
	}

	public MeltingReportDTO(Long meltingId, LocalDate dateMelting, Long numberMelting, String alloy, String alloyPol,
			String alloyFootage, Long partnerId, String partnerName, String typeTransaction, Long machineId,
			String machineName, LocalDateTime startOfFurnaceCharging, LocalDateTime endOfFurnaceCharging,
			LocalDateTime startOfFurnaceToFurnaceMetalTransfer, LocalDateTime endOfFurnaceToFurnaceMetalTransfer,
			Duration totalChargingTime, Duration totalTransferTime, Duration totalCycleTime, String observation,
			List<MeltingItemDTO> meltingItems, List<EmployeeMeltingReportDTO> employees) {
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

	public MeltingReportDTO(MeltingProjection p) {
		meltingId = p.getMeltingId();
		dateMelting = p.getDateMelting();
		numberMelting = p.getNumberMelting();
		alloy = p.getAlloy();
		alloyPol = p.getAlloyPol();
		alloyFootage = p.getAlloyFootage();
		partnerId = p.getPartnerId();
		partnerName = p.getPartnerName();
		typeTransaction = p.getTypeTransaction();
		machineId = p.getMachineId();
		machineName = p.getMachineName();
		startOfFurnaceCharging = p.getStartOfFurnaceCharging();
		endOfFurnaceCharging = p.getEndOfFurnaceCharging();
		startOfFurnaceToFurnaceMetalTransfer = p.getStartOfFurnaceToFurnaceMetalTransfer();
		endOfFurnaceToFurnaceMetalTransfer = p.getEndOfFurnaceToFurnaceMetalTransfer();
		totalChargingTime = p.getTotalChargingTime();
		totalTransferTime = p.getTotalTransferTime();
		totalCycleTime = p.getTotalCycleTime();
		observation = p.getObservation();
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

	public List<EmployeeMeltingReportDTO> getEmployees() {
		return employees;
	}

}
