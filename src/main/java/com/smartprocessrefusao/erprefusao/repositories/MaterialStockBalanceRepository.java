package com.smartprocessrefusao.erprefusao.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.MaterialStockBalance;

@Repository
public interface MaterialStockBalanceRepository extends JpaRepository<MaterialStockBalance, Long> {
	Optional<MaterialStockBalance> findByMaterials_MaterialCode(Long materialCode);

}
