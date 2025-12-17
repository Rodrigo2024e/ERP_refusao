package com.smartprocessrefusao.erprefusao.repositories;

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
			 	t.num_ticket As numTicket, t.date_ticket,
			 	di.item_sequence, di.partner_id, pp.name, 
			 	t.number_plate, t.net_weight,
			 	di.material_id, m.description, di.document_number,
			 	di.type_dispatch As transactionDescription, di.alloy,
			 	di.alloy_pol As alloyPol, di.alloy_footage As alloyFootage,
			 	di.quantity, di.price, di.total_value, di.observation
			FROM tb_ticket t
			INNER JOIN tb_dispatch d ON d.id = t.id
			INNER JOIN tb_dispatch_item di ON di.dispatch_id = d.id
			INNER JOIN tb_material m ON m.id = di.material_id
			INNER JOIN tb_partner p ON p.id = di.partner_id
			INNER JOIN tb_people pp ON pp.id = p.id
			WHERE (:numTicket IS NULL OR t.num_ticket = :numTicket)
			AND (:description IS NULL OR LOWER(m.description) LIKE LOWER(CONCAT('%', :description, '%')))
			AND (:numTicket IS NULL OR t.num_ticket = :numTicket)
			AND (:people_id IS NULL OR LOWER(pp.id) LIKE LOWER(CONCAT('%', :people_id, '%')))
			ORDER BY di.item_sequence
			""", countQuery = """
				SELECT COUNT(t.id)
				FROM tb_ticket t
				INNER JOIN tb_dispatch d ON d.id = t.id
				INNER JOIN tb_dispatch_item di ON di.dispatch_id = d.id
				INNER JOIN tb_material m ON m.id = di.material_id
				INNER JOIN tb_partner p ON p.id = di.partner_id
				INNER JOIN tb_people pp ON pp.id = p.id
			   WHERE (:numTicket IS NULL OR t.num_ticket = :numTicket)
			    AND (:description IS NULL OR LOWER(m.description) LIKE LOWER(CONCAT('%', :description, '%')))
			    AND (:numTicket IS NULL OR t.num_ticket = :numTicket)
			    AND (:people_id IS NULL OR LOWER(pp.id) LIKE LOWER(CONCAT('%', :people_id, '%')))
			""", nativeQuery = true)
	Page<DispatchReportProjection> searchDescriptionMaterialOrNumTicketPeople(@Param("description") String description,
			@Param("numTicket") Long numTicket, @Param("people_id") Long people_id, Pageable pageable);

	@Transactional
    void deleteByNumTicket(Long numTicket);
	
	boolean existsByNumTicket(Long numTicket);
	
	Optional<Dispatch> findByNumTicket(Long numTicket);
	
	
}
