package com.smartprocessrefusao.erprefusao.projections;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface MeltingProjection {

	Long getMeltingId();
	
	LocalDate getDateMelting();

	Long getNumberMelting();
	
	String getAlloy();
	
	String getAlloyPol();
	
	String getAlloyFootage();

	Long getPartnerId();

	String getPartnerName();

	String getTypeTransaction();

	Long getMachineId();

	String getMachineName();

	LocalDateTime getStartOfFurnaceCharging();

	LocalDateTime getEndOfFurnaceCharging();

	LocalDateTime getStartOfFurnaceToFurnaceMetalTransfer();

	LocalDateTime getEndOfFurnaceToFurnaceMetalTransfer();

	Duration getTotalChargingTime();

	Duration getTotalTransferTime();

	Duration getTotalCycleTime();

	String getObservation();
}
