package com.smartprocessrefusao.erprefusao.repositories;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.ProductDispatch;
import com.smartprocessrefusao.erprefusao.projections.ProductDispatchProjection;

@Repository
public interface ProductDispatchRepository extends JpaRepository<ProductDispatch, Long> {

	@Query(value = """
			SELECT
			       m.id, m.type_material,
			       t.num_ticket As numTicketId,
			       pp.id AS partnerId, pp.name AS partnerName,
			       pd.transaction As transactionDescription,
			       pr.id As product_Id, pr.description As productDescription, pr.alloy, pr.billet_diameter, pr.billet_length,
                   pd.amount_product, pd.unit_value, pd.total_value
			    FROM tb_product_dispatch pd
			    JOIN tb_material m ON m.id = pd.product_id
			    JOIN tb_product pr ON pr.id = pd.product_id
			    JOIN tb_ticket t ON t.num_ticket = pd.num_ticket_id
			    JOIN tb_people pp ON pp.id = pd.partner_id
				WHERE (:numTicketId IS NULL OR pd.num_ticket_id = :numTicketId)
				ORDER BY pd.num_ticket_id
				""", countQuery = """
			SELECT COUNT(*)
			FROM tb_product_dispatch pd
			    JOIN tb_material m ON m.id = pd.product_id
			    JOIN tb_product pr ON pr.id = pd.product_id
			    JOIN tb_ticket t ON t.num_ticket = pd.num_ticket_id
			    JOIN tb_people pp ON pp.id = pd.partner_id
			WHERE (:numTicketId IS NULL OR pd.num_ticket_id = :numTicketId)
			""", nativeQuery = true)
	Page<ProductDispatchProjection> searchProductDispatchByNumberTicket(@Param("numTicketId") Integer numTicketId,
			Pageable pageable);

	@Query("SELECT COALESCE(SUM(pd.amountProduct), 0) " + "FROM ProductDispatch pd "
			+ "WHERE pd.numTicket.numTicket = :numTicket")
	BigDecimal sumAmountProductByNumTicket(@Param("numTicket") Integer numTicket);

	@Query("SELECT COALESCE(SUM(pd.amountProduct), 0) " + "FROM ProductDispatch pd "
			+ "WHERE pd.numTicket.numTicket = :numTicket " + "AND pd.id <> :excludedId")
	BigDecimal sumAmountProductByNumTicketExcludingId(@Param("numTicket") Integer numTicket,
			@Param("excludedId") Long excludedId);
}
