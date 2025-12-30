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
import com.smartprocessrefusao.erprefusao.projections.MeltingProjection;

import jakarta.transaction.Transactional;

@Repository
public interface MeltingRepository extends JpaRepository<Melting, Long> {

    @Query("""
        SELECT
    		m.id AS meltingId,
            m.dateMelting AS dateMelting,
            m.numberMelting AS numberMelting,
            
            p.id AS partnerId,
            p.name AS partnerName,

            m.typeTransaction AS typeTransaction,

            ma.id AS machineId,
            ma.description AS machineName,

            m.startOfFurnaceCharging AS startOfFurnaceCharging,
            m.endOfFurnaceCharging AS endOfFurnaceCharging,

            m.startOfFurnaceToFurnaceMetalTransfer AS startOfFurnaceToFurnaceMetalTransfer,
            m.endOfFurnaceToFurnaceMetalTransfer AS endOfFurnaceToFurnaceMetalTransfer,

            m.startOfFurnaceTapping AS startOfFurnaceTapping,
            m.endOfFurnaceTapping AS endOfFurnaceTapping,

            m.totalChargingTime AS totalChargingTime,
            m.totalTransferTime AS totalTransferTime,
            m.totalTappingTime AS totalTappingTime,
            m.totalCycleTime AS totalCycleTime,

            m.observation AS observation
        FROM Melting m
        JOIN m.partner p
        JOIN m.machine ma
        WHERE (:partnerId IS NULL OR p.id = :partnerId)
          AND (:numberMelting IS NULL OR m.numberMelting = :numberMelting)
          AND (:startDate IS NULL OR m.dateMelting >= :startDate)
          AND (:endDate IS NULL OR m.dateMelting <= :endDate)
        """)
    Page<MeltingProjection> findMeltingBase(
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
