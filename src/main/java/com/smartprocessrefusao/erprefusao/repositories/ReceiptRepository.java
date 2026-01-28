package com.smartprocessrefusao.erprefusao.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.Receipt;
import com.smartprocessrefusao.erprefusao.projections.ReceiptReportProjection;

import jakarta.transaction.Transactional;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
	@Query(value = """
		    SELECT
		        r.id AS receiptId,
		        t.num_ticket AS numTicket,
		        t.date_ticket AS dateTicket,
		        t.number_plate AS numberPlate,
		        t.net_weight AS netWeight
		    FROM tb_receipt r
		    INNER JOIN tb_ticket t
		       ON t.id = r.id
		    WHERE (:numTicket IS NULL OR t.num_ticket = :numTicket)
		      AND (:startDate IS NULL OR t.date_ticket >= :startDate)
		      AND (:endDate IS NULL OR t.date_ticket <= :endDate)
		    """,
		    countQuery = """
		    SELECT COUNT(*)
		    FROM tb_receipt r
		    INNER JOIN tb_ticket t
		        ON t.id = r.id
		    WHERE (:numTicket IS NULL OR t.num_ticket = :numTicket)
		      AND (:startDate IS NULL OR t.date_ticket >= :startDate)
		      AND (:endDate IS NULL OR t.date_ticket <= :endDate)
		    """,
		    nativeQuery = true)
		Page<ReceiptReportProjection> reportReceipt(
		        @Param("numTicket") Long numTicket,
		        @Param("startDate") LocalDate startDate,
		        @Param("endDate") LocalDate endDate,
		        Pageable pageable
		);

	@Transactional
	void deleteByNumTicket(Long numTicket);

	boolean existsByNumTicket(Long numTicket);

	Optional<Receipt> findByNumTicket(Long numTicket);

}
