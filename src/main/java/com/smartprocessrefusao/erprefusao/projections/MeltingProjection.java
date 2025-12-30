package com.smartprocessrefusao.erprefusao.projections;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface MeltingProjection {

	Long getMeltingId();
	
	LocalDate getDateMelting();

	Long getNumberMelting();

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

	Duration getTotalChargingTime();

	Duration getTotalTransferTime();

	Duration getTotalTappingTime();

	Duration getTotalCycleTime();

	String getObservation();
}
