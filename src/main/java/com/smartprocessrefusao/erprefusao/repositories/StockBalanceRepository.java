package com.smartprocessrefusao.erprefusao.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.StockBalance;

@Repository
public interface StockBalanceRepository extends JpaRepository<StockBalance, Long> {
	Optional<StockBalance> findByMaterials_MaterialCode(Long materialCode);

}
