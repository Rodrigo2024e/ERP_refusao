package com.smartprocessrefusao.erprefusao.repositories;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.ScrapReceipt;
import com.smartprocessrefusao.erprefusao.projections.ScrapReceiptProjection;

@Repository
public interface ScrapReceiptRepository extends JpaRepository<ScrapReceipt, Long> {

	@Query(value = """
			SELECT
			    m.id,
			    m.type_material,
			    i.id As InputId,
			    i.description AS InputDescription,
			    t.num_ticket,
			    sr.id,
			    sr.num_ticket_id,
			    p.id AS partnerId, p.name AS partnerName,
			    sr.transaction As transactionDescription, sr.costs, sr.amount_scrap, sr.unit_value,
			    sr.total_value, sr.metal_yield, sr.metal_weight, sr.slag
			FROM tb_scrap_receipt sr
			JOIN tb_input i ON i.id = sr.input_id
			JOIN tb_material m ON m.id = i.id
			JOIN tb_ticket t ON t.num_ticket = sr.num_ticket_id
			JOIN tb_people p ON p.id = sr.partner_id
			WHERE (:numTicketId IS NULL OR sr.num_ticket_id = :numTicketId)
			ORDER BY sr.num_ticket_id
			""", countQuery = """
			SELECT COUNT(*)
			FROM tb_scrap_receipt sr
			JOIN tb_input i ON i.id = sr.input_id
			JOIN tb_material m ON m.id = i.id
			JOIN tb_ticket t ON t.num_ticket = sr.num_ticket_id
			JOIN tb_people p ON p.id = sr.partner_id
			WHERE (:numTicketId IS NULL OR sr.num_ticket_id = :numTicketId)
			""", nativeQuery = true)
	Page<ScrapReceiptProjection> searchScrapReceiptByNumberTicket(@Param("numTicketId") Integer numTicketId,
			Pageable pageable);

	@Query("SELECT COALESCE(SUM(sr.amountScrap), 0) " + "FROM ScrapReceipt sr "
			+ "WHERE sr.numTicket.numTicket = :numTicket")
	BigDecimal sumAmountScrapByNumTicket(@Param("numTicket") Integer numTicket);

	@Query("SELECT COALESCE(SUM(sr.amountScrap), 0) " + "FROM ScrapReceipt sr "
			+ "WHERE sr.numTicket.numTicket = :numTicket " + "AND sr.id <> :excludedId")
	BigDecimal sumAmountScrapByNumTicketExcludingId(@Param("numTicket") Integer numTicket,
			@Param("excludedId") Long excludedId);
}
