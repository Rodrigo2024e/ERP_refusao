package com.smartprocessrefusao.erprefusao.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.entities.Receipt;
import com.smartprocessrefusao.erprefusao.projections.ReportReceiptProjection;

import jakarta.transaction.Transactional;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

	@Query(value = """
			SELECT
			 	t.num_ticket As numTicket, t.date_ticket, t.number_plate,
			 	ri.document_number, t.net_weight, ri.type_receipt, ri.type_costs,
			 	ri.partner_id, pp.name, ri.item_sequence, ri.code, m.code, m.description,
			 	ri.quantity, ri.recovery_yield, ri.price, ri.total_value,
			 	ri.quantity_mco, ri.observation
			FROM tb_ticket t
			INNER JOIN tb_receipt r ON r.id = t.id
			INNER JOIN tb_receipt_item ri ON ri.receipt_id = r.id
			INNER JOIN tb_material m ON m.code = ri.code
			INNER JOIN tb_partner p ON p.id = ri.partner_id
			INNER JOIN tb_people pp ON pp.id = p.id
			WHERE
			 	(:startDate IS NULL OR t.date_ticket >= :startDate)
			 	AND (:endDate IS NULL OR t.date_ticket <= :endDate)
			 	AND (:code IS NULL OR m.code = :code)
				AND (:description IS NULL OR LOWER(m.description) LIKE LOWER(CONCAT('%', :description, '%')))
				AND (:numTicket IS NULL OR t.num_ticket = :numTicket)
				AND (:partner_id IS NULL OR LOWER(p.id) LIKE LOWER(CONCAT('%', :partner_id, '%')))
			ORDER BY
			 	t.num_ticket,
			 	ri.item_sequence
							""", countQuery = """
				SELECT COUNT(t.id)
				FROM tb_ticket t
				INNER JOIN tb_receipt r ON r.id = t.id
				INNER JOIN tb_receipt_item ri ON ri.receipt_id = r.id
			    INNER JOIN tb_material m ON m.code = ri.code
			    INNER JOIN tb_partner p ON p.id = ri.partner_id
			    INNER JOIN tb_people pp ON pp.id = p.id
			WHERE (:receiptId IS NULL OR r.id = :receiptId)
			    AND (:description IS NULL OR LOWER(m.description) LIKE LOWER(CONCAT('%', :description, '%')))
			    AND (:numTicket IS NULL OR t.num_ticket = :numTicket)
			    AND (:partner_id IS NULL OR LOWER(p.id) LIKE LOWER(CONCAT('%', :partner_id, '%')))
			""", nativeQuery = true)
	Page<ReportReceiptProjection> reportReceipt(@Param("description") String description,
			@Param("numTicket") Long numTicket, @Param("partner_id") Long partner_id,
			@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("code") Long code, Pageable pageble);

	@Transactional
	void deleteByNumTicket(Long numTicket);
	
//	Optional<Material> findByCode(Long code);

	boolean existsByNumTicket(Long numTicket);

	Optional<Receipt> findByNumTicket(Long numTicket);

}
