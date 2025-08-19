package com.smartprocessrefusao.erprefusao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.TaxClassification;

@Repository
public interface TaxClassificationRepository extends JpaRepository<TaxClassification, Long> {

	
}
