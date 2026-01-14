package com.smartprocessrefusao.erprefusao.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.Dispatch;
import com.smartprocessrefusao.erprefusao.projections.DispatchReportProjection;

import jakarta.transaction.Transactional;

@Repository
public interface DispatchRepository extends JpaRepository<Dispatch, Long> {

	@Query(value = """
			SELECT
			    d.id AS dispatchId,
			    t.num_ticket AS numTicketId,
			    t.date_ticket AS dateTicket,
			    t.number_plate AS numberPlate,
			    t.net_weight AS netWeight
			FROM tb_dispatch d
			INNER JOIN tb_ticket t ON t.id = d.id
			WHERE (:numTicketId IS NULL OR t.num_ticket = :numTicketId)
			  AND (:startDate IS NULL OR t.date_ticket >= :startDate)
			  AND (:endDate IS NULL OR t.date_ticket <= :endDate)
			""", countQuery = """
			SELECT COUNT(*)
			FROM tb_dispatch d
			INNER JOIN tb_ticket t ON t.id = d.id
			WHERE (:numTicketId IS NULL OR t.num_ticket = :numTicketId)
			  AND (:startDate IS NULL OR t.date_ticket >= :startDate)
			  AND (:endDate IS NULL OR t.date_ticket <= :endDate)
			""", nativeQuery = true)
	Page<DispatchReportProjection> reportDispatch(
			@Param("numTicketId") Long numTicketId,
			@Param("startDate") LocalDate startDate, 
			@Param("endDate") LocalDate endDate, Pageable pageable);

	@Transactional
	void deleteByNumTicket(Long numTicket);

	boolean existsByNumTicket(Long numTicket);

	Optional<Dispatch> findByNumTicket(Long numTicket);

}
