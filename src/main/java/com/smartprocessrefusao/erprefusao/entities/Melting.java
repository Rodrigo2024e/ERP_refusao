package com.smartprocessrefusao.erprefusao.entities;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.smartprocessrefusao.erprefusao.audit.Auditable;
import com.smartprocessrefusao.erprefusao.enumerados.TypeTransactionOutGoing;
import com.smartprocessrefusao.erprefusao.projections.IdProjection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_melting")
public class Melting extends Auditable<String> implements IdProjection<Long> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate dateMelting;

	@Column(name = "numberMelting", unique = true)
	private Long numberMelting;
	

	@Enumerated(EnumType.STRING)
	private TypeTransactionOutGoing typeTransaction;
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

	@ManyToMany(mappedBy = "meltings", fetch = FetchType.LAZY)
	private Set<Employee> employees = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "partner_id")
	private Partner partner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "machine_id")
	private Machine machine;

	@OneToMany(mappedBy = "id.melting", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<MeltingItem> meltingItems = new HashSet<>();

	public Melting() {
	}

	public Melting(Long id, LocalDate dateMelting, Long numberMelting, TypeTransactionOutGoing typeTransaction,
			LocalDateTime startOfFurnaceCharging, LocalDateTime endOfFurnaceCharging,
			LocalDateTime startOfFurnaceToFurnaceMetalTransfer, LocalDateTime endOfFurnaceToFurnaceMetalTransfer,
			LocalDateTime startOfFurnaceTapping, LocalDateTime endOfFurnaceTapping, Duration totalChargingTime,
			Duration totalTransferTime, Duration totalTappingTime, Duration totalCycleTime, String observation,
			Partner partner, Machine machine) {
		this.id = id;
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
		this.observation = observation;
		this.partner = partner;
		this.machine = machine;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDateMelting() {
		return dateMelting;
	}

	public void setDateMelting(LocalDate dateMelting) {
		this.dateMelting = dateMelting;
	}

	public Long getNumberMelting() {
		return numberMelting;
	}

	public void setNumberMelting(Long numberMelting) {
		this.numberMelting = numberMelting;
	}

	public TypeTransactionOutGoing getTypeTransaction() {
		return typeTransaction;
	}

	public void setTypeTransaction(TypeTransactionOutGoing typeTransaction) {
		this.typeTransaction = typeTransaction;
	}

	public LocalDateTime getStartOfFurnaceCharging() {
		return startOfFurnaceCharging;
	}

	public void setStartOfFurnaceCharging(LocalDateTime startOfFurnaceCharging) {
		this.startOfFurnaceCharging = startOfFurnaceCharging;
	}

	public LocalDateTime getEndOfFurnaceCharging() {
		return endOfFurnaceCharging;
	}

	public void setEndOfFurnaceCharging(LocalDateTime endOfFurnaceCharging) {
		this.endOfFurnaceCharging = endOfFurnaceCharging;
	}

	public LocalDateTime getStartOfFurnaceToFurnaceMetalTransfer() {
		return startOfFurnaceToFurnaceMetalTransfer;
	}

	public void setStartOfFurnaceToFurnaceMetalTransfer(LocalDateTime startOfFurnaceToFurnaceMetalTransfer) {
		this.startOfFurnaceToFurnaceMetalTransfer = startOfFurnaceToFurnaceMetalTransfer;
	}

	public LocalDateTime getEndOfFurnaceToFurnaceMetalTransfer() {
		return endOfFurnaceToFurnaceMetalTransfer;
	}

	public void setEndOfFurnaceToFurnaceMetalTransfer(LocalDateTime endOfFurnaceToFurnaceMetalTransfer) {
		this.endOfFurnaceToFurnaceMetalTransfer = endOfFurnaceToFurnaceMetalTransfer;
	}

	public LocalDateTime getStartOfFurnaceTapping() {
		return startOfFurnaceTapping;
	}

	public void setStartOfFurnaceTapping(LocalDateTime startOfFurnaceTapping) {
		this.startOfFurnaceTapping = startOfFurnaceTapping;
	}

	public LocalDateTime getEndOfFurnaceTapping() {
		return endOfFurnaceTapping;
	}

	public void setEndOfFurnaceTapping(LocalDateTime endOfFurnaceTapping) {
		this.endOfFurnaceTapping = endOfFurnaceTapping;
	}

	public Duration getTotalChargingTime() {
		return totalChargingTime;
	}

	public void setTotalChargingTime(Duration totalChargingTime) {
		this.totalChargingTime = totalChargingTime;
	}

	public Duration getTotalTransferTime() {
		return totalTransferTime;
	}

	public void setTotalTransferTime(Duration totalTransferTime) {
		this.totalTransferTime = totalTransferTime;
	}

	public Duration getTotalTappingTime() {
		return totalTappingTime;
	}

	public void setTotalTappingTime(Duration totalTappingTime) {
		this.totalTappingTime = totalTappingTime;
	}

	public Duration getTotalCycleTime() {
		return totalCycleTime;
	}

	public void setTotalCycleTime(Duration totalCycleTime) {
		this.totalCycleTime = totalCycleTime;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	public Set<MeltingItem> getMeltingItems() {
		return meltingItems;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Melting other = (Melting) obj;
		return Objects.equals(id, other.id);
	}

}
