package com.smartprocessrefusao.erprefusao.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.Ticket;
import com.smartprocessrefusao.erprefusao.projections.ReportTicketProjection;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
	Optional<Ticket> findByNumTicket(Integer numTicket);

	@Query(value = """
				SELECT
					t.moment, t.num_ticket, t.date_ticket, t.number_plate, t.net_weight,
					sr.num_ticket_id, p.id As PartnerId, p.name As NamePartner, i.id As ScrapId, 
					i.description As ScrapDescription, sr.amount_scrap As AmountScrap
				FROM tb_ticket t
				INNER JOIN tb_scrap_receipt sr ON sr.num_ticket_id = t.num_ticket
                INNER JOIN tb_input i ON i.id = sr.input_id
                INNER JOIN tb_people p ON p.id = sr.partner_id
				WHERE (:numTicketId IS NULL OR sr.num_ticket_id = :numTicketId)
				ORDER BY t.num_ticket
			""", countQuery = """
				SELECT COUNT(t.num_ticket)
				FROM tb_ticket t
				INNER JOIN tb_scrap_receipt sr ON sr.num_ticket_id = t.num_ticket
                INNER JOIN tb_input i ON i.id = sr.input_id
                INNER JOIN tb_people p ON p.id = sr.partner_id
				WHERE (:numTicketId IS NULL OR sr.num_ticket_id = :numTicketId)
				ORDER BY t.num_ticket
			""", nativeQuery = true)
	Page<ReportTicketProjection> searchTicketWithScrapReceipt(@Param("numTicketId") Integer numTicketId,
			Pageable pageable);

}
