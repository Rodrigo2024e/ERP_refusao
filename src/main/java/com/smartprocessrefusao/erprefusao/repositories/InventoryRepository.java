package com.smartprocessrefusao.erprefusao.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.Inventory;
import com.smartprocessrefusao.erprefusao.entities.InventoryItem;
import com.smartprocessrefusao.erprefusao.entities.Receipt;
import com.smartprocessrefusao.erprefusao.projections.InventoryReportProjection;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    /* ======================================================
       ITENS DE INVENTÁRIO (BASE DO ESTOQUE)
       ====================================================== */

    @Query("""
        SELECT ii
        FROM InventoryItem ii
        JOIN ii.material m
        WHERE m.id = :materialId
        ORDER BY ii.inventory.dateInventory, ii.id
    """)
    List<InventoryItem> findAllItemsByMaterial(@Param("materialId") Long materialId);

    /* ======================================================
       RELATÓRIO DE INVENTÁRIO
       ====================================================== */

    @Query(
    	    value = """
    	       SELECT
    	    		i.date_inventory                                AS dateInventory,
    	    		m.material_code                                 AS materialCode,
    	    		m.description                                   AS description,

    	    		SUM(sb.total_adjustment_entries)                 AS totalAdjustmentEntries,
    	    		AVG(sb.recovery_yield_adjustment_entries)        AS recoveryYieldAdjustmentEntries,
    	    		SUM(sb.total_adjustment_entries_mco)             AS totalAdjustmentEntriesMco,

    	    		SUM(sb.total_purchase)                           AS totalPurchase,
    	    		AVG(sb.recovery_yield_purchase)                  AS recoveryYieldPurchase,
    	    		SUM(sb.total_purchase_mco)                       AS totalPurchaseMco,

    	    		SUM(sb.total_value)                              AS totalValue,
    	    		AVG(sb.average_price)                            AS averagePrice,
    	    		AVG(sb.average_price_mco)                        AS averagePriceMco,

    	    		SUM(sb.total_sent_for_processing)                AS totalSentForProcessing,
    	    		AVG(sb.recovery_yield_sent_for_processing)       AS recoveryYieldSentForProcessing,
    	    		SUM(sb.total_sent_for_processing_mco)            AS totalSentForProcessingMco,

    	    		SUM(sb.total_sales_scrap)                        AS totalSalesScrap,
    	    		AVG(sb.recovery_yield_sales_scrap)               AS recoveryYieldSalesScrap,
    	    		SUM(sb.total_sales_scrap_mco)                    AS totalSalesScrapMco,

    	    		SUM(sb.total_scrap_sales_return)                 AS totalScrapSalesReturn,
    	    		AVG(sb.recovery_yield_scrap_sales_return)        AS recoveryYieldScrapSalesReturn,
    	    		SUM(sb.total_scrap_sales_return_mco)             AS totalScrapSalesReturnMco,

    	    		SUM(sb.total_adjustment_exit)                    AS totalAdjustmentExit,
    	    		AVG(sb.recovery_yield_adjustment_exit)           AS recoveryYieldAdjustmentExit,
    	    		SUM(sb.total_adjustment_exit_mco)                AS totalAdjustmentExitMco,

    /* ==========================
       SALDO FINAL (REGRA CORRETA)
       ========================== */

		    (
		        SUM(sb.total_purchase)
		      + SUM(sb.total_adjustment_entries)
		      + SUM(sb.total_scrap_sales_return)
		      - SUM(sb.total_sales_scrap)
		      - SUM(sb.total_adjustment_exit)
		      - SUM(sb.total_sent_for_processing)
		    )                                               AS finalBalance,
		
		    (
		        SUM(sb.total_purchase_mco)
		      + SUM(sb.total_adjustment_entries_mco)
		      + SUM(sb.total_scrap_sales_return_mco)
		      - SUM(sb.total_sales_scrap_mco)
		      - SUM(sb.total_adjustment_exit_mco)
		      - SUM(sb.total_sent_for_processing_mco)
		    )                                               AS finalBalanceMco,
		
		    AVG(sb.recovery_yield_final_balance)              AS recoveryYieldFinalBalance

		FROM tb_material_stock_balance sb
		JOIN tb_material m ON m.id = sb.material_id
		JOIN tb_inventory i On i.id = sb.inventory_id
		
		WHERE (:startDate IS NULL OR i.date_inventory >= :startDate)
		  AND (:endDate   IS NULL OR i.date_inventory <= :endDate)
		  AND (:materialCode IS NULL OR m.material_code = :materialCode)
		
		GROUP BY
		    i.date_inventory,
		    m.material_code,
		    m.description
		
		ORDER BY
		    m.material_code

    	    """,
    	    countQuery = """
		SELECT COUNT(*) FROM (
		    SELECT 1
		   FROM tb_material_stock_balance sb
    	    JOIN tb_material m ON m.id = sb.material_id
    	    JOIN tb_inventory i On i.id = sb.inventory_id
		    WHERE (:startDate IS NULL OR i.date_inventory >= :startDate)
		      AND (:endDate   IS NULL OR i.date_inventory <= :endDate)
		      AND (:materialCode IS NULL OR m.material_code = :materialCode)
		    GROUP BY i.date_inventory, m.material_code, m.description
    	    """,
    	    nativeQuery = true
    	)
    	Page<InventoryReportProjection> reportInventory(
    	        LocalDate startDate,
    	        LocalDate endDate,
    	        Long materialCode,
    	        Pageable pageable
    	);


    Optional<Inventory> findByReceipt(Receipt receipt);

    boolean existsByReceipt(Receipt receipt);
    
}
