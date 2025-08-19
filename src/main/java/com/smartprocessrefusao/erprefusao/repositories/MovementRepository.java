package com.smartprocessrefusao.erprefusao.repositories;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.Movement;
import com.smartprocessrefusao.erprefusao.projections.MovementProjection;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

	@Query(value = """
				SELECT 
					m.id,
			        m.num_ticket_id, 
			        p.id As partnerId, p.name As partnerName, 
			        tra.id As transactionId, tra.description as transactionDescription,
					m.expenses,
			        mat.id As materialId, mat.description As materialDescription, 
			        m.amount_material, m.unit_value, m.total_value, m.metal_yield, m.metal_weight, m.slag
				FROM tb_movement m
				JOIN tb_ticket t ON t.num_ticket = m.num_ticket_id
				JOIN tb_people p ON p.id = m.partner_id
				JOIN tb_material mat ON mat.id = m.material_id
				JOIN tb_type_transaction tra ON tra.id = m.transaction_id
					WHERE (:numTicketId IS NULL OR m.num_ticket_id = :numTicketId)
					ORDER BY m.num_ticket_id
			""", countQuery = """
				SELECT COUNT(m.num_ticket_id)
				FROM tb_movement m
				JOIN tb_ticket t ON t.num_ticket = m.num_ticket_id
				JOIN tb_people p ON p.id = m.partner_id
				JOIN tb_material mat ON mat.id = m.material_id
				JOIN tb_type_transaction tra ON tra.id = m.transaction_id
					WHERE (:numTicketId IS NULL OR m.numTicketId = :numTicketId)
					ORDER BY m.numTicketId
			""", nativeQuery = true)
	Page<MovementProjection> searchMovementByNumberTicket(@Param("numTicketId") Integer numberTicketId,
			Pageable pageable);
	
	@Query("SELECT SUM(m.amountMaterial) FROM Movement m WHERE m.numTicket.numTicket = :numTicket")
	BigDecimal sumAmountMaterialByNumTicket(@Param("numTicket") Integer numTicket);

	@Query("SELECT SUM(m.amountMaterial) FROM Movement m WHERE m.numTicket.numTicket = :numTicket AND m.id <> :excludedId")
	BigDecimal sumAmountMaterialByNumTicketExcludingId(@Param("numTicket") Integer numTicket, @Param("excludedId") Long excludedId);
}
