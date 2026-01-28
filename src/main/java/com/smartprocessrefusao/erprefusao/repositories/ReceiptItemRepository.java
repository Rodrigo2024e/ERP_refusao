package com.smartprocessrefusao.erprefusao.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.ReceiptItem;
import com.smartprocessrefusao.erprefusao.projections.ReceiptItemReportProjection;

@Repository
public interface ReceiptItemRepository extends JpaRepository<ReceiptItem, Long> {

	
	@Query("""
			select max(r.itemSequence)
			from ReceiptItem r
			where r.receipt.id = :receiptId
		""")
		Optional<Integer> findMaxSequenceByReceiptId(Long receiptId);

	
	
	@Query(value = """
			   SELECT
				    r.id AS receiptId,                   
				    t.num_ticket AS numTicketId,
				    t.date_ticket AS dateTicket,
				    ri.item_sequence AS itemSequence,
				    pt.id AS partnerId,
				    p.name AS partnerName,
				    m.material_code AS materialCode,
				    m.description AS materialDescription,
				    ri.recovery_yield AS recoveryYield,
				    ri.document_number AS documentNumber,
				    ri.type_receipt AS typeReceipt,
				    ri.type_costs AS typeCosts,
				    ri.quantity AS quantity,
				    ri.price AS price,
				    ri.total_value AS totalValue,
				    ri.quantity_mco AS quantityMco,
				    ri.observation AS observation
				FROM tb_receipt_item ri
				INNER JOIN tb_receipt r ON r.id = ri.receipt_id
				INNER JOIN tb_ticket t ON t.id = r.id
				INNER JOIN tb_partner pt ON pt.id = ri.partner_id
				INNER JOIN tb_people p ON p.id = pt.id
				INNER JOIN tb_material m ON m.id = ri.material_id
				WHERE ri.receipt_id IN (:receiptIds)
				AND (:numTicketId IS NULL OR t.num_ticket = :numTicketId)
				AND (:materialCode IS NULL OR m.material_code = :materialCode)
				AND (:materialDescription IS NULL OR LOWER(m.description) LIKE LOWER(CONCAT('%', :materialDescription, '%')))
				AND (:partnerId IS NULL OR pt.id = :partnerId)
				AND (:startDate IS NULL OR t.date_ticket >= :startDate)
				AND (:endDate IS NULL OR t.date_ticket <= :endDate)
				ORDER BY ri.item_sequence
			    """, nativeQuery = true)
			List<ReceiptItemReportProjection> findItemsByReceiptIds(
				    @Param("receiptIds") List<Long> receiptIds,
				    @Param("numTicketId") Long numTicketId,
				    @Param("startDate") LocalDate startDate,
				    @Param("endDate") LocalDate endDate,
				    @Param("partnerId") Long partnerId,
				    @Param("materialDescription") String materialDescription,
				    @Param("materialCode") Long materialCode);
}
