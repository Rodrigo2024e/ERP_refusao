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

    @Query("""
        SELECT 
            i.dateInventory            AS dateInventory,
            m.materialCode             AS materialCode,
            m.description              AS materialDescription,
            ii.typeReceipt             AS typeReceipt,
            ii.quantity                AS quantity,
            ii.quantityMco             AS quantityMco,
            ii.totalValue              AS totalValue,
            p.name                     AS partnerName
        FROM Inventory i
        JOIN i.items ii
        JOIN ii.material m
        JOIN ii.partner p
        WHERE (:startDate IS NULL OR i.dateInventory >= :startDate)
          AND (:endDate   IS NULL OR i.dateInventory <= :endDate)
          AND (:materialCode IS NULL OR m.materialCode = :materialCode)
        ORDER BY i.dateInventory DESC, m.materialCode
    """)
    Page<InventoryReportProjection> reportInventory(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("materialCode") Long materialCode,
            Pageable pageable
    );

    Optional<Inventory> findByReceipt(Receipt receipt);

    boolean existsByReceipt(Receipt receipt);
    
}
