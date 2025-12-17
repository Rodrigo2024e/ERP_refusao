package com.smartprocessrefusao.erprefusao.projections;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface MeltingReportProjection {

	Long getMeltingId();

	LocalDate getDateMelting();

	Integer getNumberMelting();
	Long getPartnerId();

	String getPartnerName();

	String getTypeTransaction();
	
	Long getMachineId();
	
	String getMachineName();

	LocalDateTime getStartOfFurnaceCharging();

	LocalDateTime getEndOfFurnaceCharging();

	LocalDateTime getStartOfFurnaceToFurnaceMetalTransfer();

	LocalDateTime getEndOfFurnaceToFurnaceMetalTransfer();

	LocalDateTime getStartOfFurnaceTapping();

	LocalDateTime getEndOfFurnaceTapping();

	Long getTotalChargingTime();
	Long getTotalTransferTime();

	Long getTotalTappingTime();

	Long getTotalCycleTime();

	String getObservation();

	Long getEmployeeId();

	String getEmployeeName();

	Long getDepartamentId();

	String getDepartamentName();

	String getDepartamentProcess();
}
