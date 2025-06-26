package com.smartprocessrefusao.erprefusao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.UnitOfMeasure;

@Repository
public interface UnitOfMeasureRepository extends JpaRepository<UnitOfMeasure, Long> {
	

}
