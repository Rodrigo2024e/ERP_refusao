package com.smartprocessrefusao.erprefusao.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.Inventory;
import com.smartprocessrefusao.erprefusao.entities.Receipt;
import com.smartprocessrefusao.erprefusao.projections.ReportInventoryProjection;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	@Query(value = """
			    SELECT
					m.code, m.description, 
					
					sb.total_adjustment_entries, 
					sb.recovery_yield_adjustment_entries,
					sb.total_adjustment_entries_mco,
					
					sb.total_purchase, 
					sb.recovery_yield_purchase, 
					sb.total_purchase_mco, 
					sb.total_value,
					sb.average_cost,
					sb.average_cost_mco,
					
					sb.total_sent_for_processing, 
					sb.recovery_yield_sent_for_processing,
					sb.total_sent_for_processing_mco,
					
					sb.total_sales_scrap, 
					sb.recovery_yield_sales_scrap,
					sb.total_sales_scrap_mco, 
					
					sb.total_scrap_sales_return, 
					sb.recovery_yield_scrap_sales_return,
					sb.total_scrap_sales_return_mco, 
					
					sb.total_adjustment_exit, 
					sb.recovery_yield_adjustment_exit,
					sb.total_adjustment_exit_mco,
				    
					sb.final_balance, 
					sb.recovery_yield_final_balance,
					sb.final_balance_mco
					
				FROM tb_stock_balance sb
				INNER JOIN tb_material m ON m.stock_balance_id = sb.id
			    WHERE
			        (:startDate IS NULL OR sb.date_stock >= :startDate)
			        AND (:endDate IS NULL OR sb.date_stock <= :endDate)
			        AND (:code IS NULL OR m.code = :code)

			    GROUP BY
			        m.code,
			        m.description

			    ORDER BY
			        m.code
			""", nativeQuery = true)
	Page<ReportInventoryProjection> reportInventory(@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate, @Param("code") Long code, Pageable pageable);

	Optional<Inventory> findByReceipt(Receipt receipt);

}
