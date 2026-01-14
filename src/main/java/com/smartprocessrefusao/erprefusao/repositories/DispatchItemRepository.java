package com.smartprocessrefusao.erprefusao.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.DispatchItem;
import com.smartprocessrefusao.erprefusao.entities.PK.DispatchItemPK;
import com.smartprocessrefusao.erprefusao.projections.DispatchItemReportProjection;

@Repository
public interface DispatchItemRepository extends JpaRepository<DispatchItem, DispatchItemPK> {
	@Query(value = """
			   SELECT
				    di.dispatch_id AS dispatchId,
				    d.id,                   
				    t.num_ticket AS numTicketId,
				    t.date_ticket AS dateTicket,
				    di.item_sequence AS itemSequence,
				    pt.id AS partnerId,
				    p.name AS partnerName,
				    di.document_number AS documentNumber,
				    pr.product_code AS productCode,
				    pr.description AS productDescription,
				    di.type_dispatch AS typeDispatch,
				    di.alloy AS alloy,
				    di.alloy_pol AS alloyPol,
				    di.alloy_footage AS AlloyFootage,
				    di.quantity AS quantity,
				    di.price AS price,
				    di.total_value AS totalValue,
				    di.observation AS observation
				FROM tb_dispatch_item di
				INNER JOIN tb_dispatch d ON d.id = di.dispatch_id
				INNER JOIN tb_ticket t ON t.id = d.id
				INNER JOIN tb_partner pt ON pt.id = di.partner_id
				INNER JOIN tb_people p ON p.id = pt.id
				INNER JOIN tb_product pr ON pr.id = di.product_id
				WHERE di.dispatch_id IN (:dispatchIds)
				AND (:numTicketId IS NULL OR t.num_ticket = :numTicketId)
				AND (:productCode IS NULL OR pr.product_code = :productCode)
				AND (:productDescription IS NULL OR LOWER(pr.description) LIKE LOWER(CONCAT('%', :productDescription, '%')))
				AND (:partnerId IS NULL OR pt.id = :partnerId)
				AND (:startDate IS NULL OR t.date_ticket >= :startDate)
				AND (:endDate IS NULL OR t.date_ticket <= :endDate)
				ORDER BY di.item_sequence
			    """, nativeQuery = true)
			List<DispatchItemReportProjection> findItemsByDispatchIds(
				    @Param("dispatchIds") List<Long> dispatchIds,
				    @Param("numTicketId") Long numTicketId,
				    @Param("startDate") LocalDate startDate,
				    @Param("endDate") LocalDate endDate,
				    @Param("partnerId") Long partnerId,
				    @Param("productDescription") String productDescription,
				    @Param("productCode") Long productCode);
}
