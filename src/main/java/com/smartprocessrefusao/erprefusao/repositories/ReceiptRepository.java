package com.smartprocessrefusao.erprefusao.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.Receipt;
import com.smartprocessrefusao.erprefusao.projections.ReportReceiptProjection;

import jakarta.transaction.Transactional;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

	@Query(value = """
			SELECT
			 	r.id as receipt_id, ri.item_sequence,
			    t.num_ticket As numTicket, t.date_ticket,
			 	ri.partner_id, pp.name, t.number_plate, t.net_weight,
			 	ri.material_id, m.description, ri.document_number,
			 	ri.type_receipt, ri.type_costs, ri.quantity, ri.price,
			 	ri.total_value, ri.observation
			FROM tb_ticket t
			INNER JOIN tb_receipt r ON r.id = t.id
			INNER JOIN tb_receipt_item ri ON ri.receipt_id = r.id
			INNER JOIN tb_material m ON m.id = ri.material_id
			INNER JOIN tb_partner p ON p.id = ri.partner_id
			INNER JOIN tb_people pp ON pp.id = p.id
			WHERE (:receiptId IS NULL OR r.id = :receiptId)
			AND (:description IS NULL OR LOWER(m.description) LIKE LOWER(CONCAT('%', :description, '%')))
			AND (:numTicket IS NULL OR t.num_ticket = :numTicket)
			AND (:people_id IS NULL OR LOWER(pp.id) LIKE LOWER(CONCAT('%', :people_id, '%')))
			ORDER BY ri.item_sequence
			""", countQuery = """
				SELECT COUNT(t.id)
				FROM tb_ticket t
				INNER JOIN tb_receipt r ON r.id = t.id
				INNER JOIN tb_receipt_item ri ON ri.receipt_id = r.id
			    INNER JOIN tb_material m ON m.id = ri.material_id
			    INNER JOIN tb_partner p ON p.id = ri.partner_id
			    INNER JOIN tb_people pp ON pp.id = p.id
			   WHERE (:receiptId IS NULL OR r.id = :receiptId)
			    AND (:description IS NULL OR LOWER(m.description) LIKE LOWER(CONCAT('%', :description, '%')))
			    AND (:numTicket IS NULL OR t.num_ticket = :numTicket)
			    AND (:people_id IS NULL OR LOWER(pp.id) LIKE LOWER(CONCAT('%', :people_id, '%')))
			""", nativeQuery = true)
	Page<ReportReceiptProjection> searchDescriptionMaterialOrNumTicketPeople(@Param("receiptId") Long receiptId,
			@Param("description") String description, @Param("numTicket") Long numTicket,
			@Param("people_id") Long people_id, Pageable pageable);
	
	@Transactional
    void deleteByNumTicket(Long numTicket);
	
	boolean existsByNumTicket(Long numTicket);
	
	
	Optional<Receipt> findByNumTicket(Long numTicket);
	
}
