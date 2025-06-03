package com.smartprocessrefusao.erprefusao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.Sector;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
	List<Sector> findAllByOrderByNameSectorAsc();

}
