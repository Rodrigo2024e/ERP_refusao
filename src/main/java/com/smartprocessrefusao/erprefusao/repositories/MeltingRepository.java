package com.smartprocessrefusao.erprefusao.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.Melting;
import com.smartprocessrefusao.erprefusao.projections.MeltingReportProjection;

import jakarta.transaction.Transactional;

@Repository
public interface MeltingRepository extends JpaRepository<Melting, Long> {
	
	    @Query(value = """
			     SELECT 
				    m.id AS meltingId,
				    m.date_melting AS dateMelting,
				    m.number_melting AS numberMelting,
				
				    p.id AS partnerId,
				    pp.name AS partnerName,
				
				    m.type_transaction AS typeTransaction,
				
				    ma.id AS machineId,
				    ma.description AS machineName,
				
				    m.start_of_furnace_charging AS startOfFurnaceCharging,
				    m.end_of_furnace_charging AS endOfFurnaceCharging,
				    m.start_of_furnace_to_furnace_metal_transfer AS startOfFurnaceToFurnaceMetalTransfer,
				    m.end_of_furnace_to_furnace_metal_transfer AS endOfFurnaceToFurnaceMetalTransfer,
				    m.start_of_furnace_tapping AS startOfFurnaceTapping,
				    m.end_of_furnace_tapping AS endOfFurnaceTapping,
				
				    m.total_charging_time AS totalChargingTime,
				    m.total_transfer_time AS totalTransferTime,
				    m.total_tapping_time AS totalTappingTime,
				    m.total_cycle_time AS totalCycleTime,
				
				    m.observation AS observation,
				
				    e.id AS employeeId,
				    pe.name AS employeeName,
				
				    d.id AS departamentId,
				    d.name AS departamentName,
				    d.process AS departamentProcess,
				    d.position AS employeePosition
				
				FROM tb_melting m
				
				JOIN tb_partner p 
				    ON p.id = m.partner_id
				
				JOIN tb_people pp 
				    ON pp.id = p.id   
				
				JOIN tb_machine ma 
				    ON ma.id = m.machine_id
				
				LEFT JOIN tb_employee_melting em 
				    ON em.melting_id = m.id
				
				LEFT JOIN tb_employee e 
				    ON e.id = em.employee_id
				
				LEFT JOIN tb_people pe 
				    ON pe.id = e.id   
				
				LEFT JOIN tb_departament d 
				    ON d.id = e.departament_id

	        WHERE 1=1
	            AND (:partnerId IS NULL OR m.partner_id = :partnerId)
	            AND (:numberMelting IS NULL OR m.number_melting = :numberMelting)
	            AND (:startDate IS NULL OR m.date_melting >= :startDate)
	            AND (:endDate IS NULL OR m.date_melting <= :endDate)

	        ORDER BY m.date_melting DESC, m.number_melting ASC
	        """,
	        nativeQuery = true)
	    Page<MeltingReportProjection> searchMeltingReport(
	            @Param("partnerId") Long partnerId,
	            @Param("numberMelting") Long numberMelting,
	            @Param("startDate") LocalDate startDate,
	            @Param("endDate") LocalDate endDate,  
	            Pageable pageable
	    );
	    


	Optional<Melting> findByNumberMelting(Long numberMelting);
	
	@Transactional
    void deleteByNumberMelting(Long numberMelting);
	
	boolean existsByNumberMelting(Long numberMelting);
}
