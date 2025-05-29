package com.smartprocessrefusao.erprefusao.cadastros.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.cadastros.entities.City;


@Repository
public interface CityRepository extends JpaRepository<City, Long> {
	List<City> findAllByOrderByNameCityAsc();
	
}
