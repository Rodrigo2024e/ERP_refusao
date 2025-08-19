package com.smartprocessrefusao.erprefusao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.MaterialGroup;

@Repository
public interface MaterialGroupRepository extends JpaRepository<MaterialGroup, Long> {
	

}
