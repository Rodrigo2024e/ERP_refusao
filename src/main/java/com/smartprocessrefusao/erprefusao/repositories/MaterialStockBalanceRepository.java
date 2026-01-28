package com.smartprocessrefusao.erprefusao.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartprocessrefusao.erprefusao.entities.Inventory;
import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.entities.MaterialStockBalance;

public interface MaterialStockBalanceRepository extends JpaRepository<MaterialStockBalance, Long> {
    // Busca o último saldo inserido para um material específico
    Optional<MaterialStockBalance> findFirstByMaterialOrderByInventoryDateInventoryDescIdDesc(Material material);
    
    void deleteByInventory(Inventory inventory);

    Optional<MaterialStockBalance> findFirstByMaterialAndInventoryNotOrderByInventoryDateInventoryDescIdDesc(
            Material material, Inventory currentInventory);
}