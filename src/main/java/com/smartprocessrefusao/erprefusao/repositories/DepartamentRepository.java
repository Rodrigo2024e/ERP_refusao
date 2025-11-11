package com.smartprocessrefusao.erprefusao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.Departament;

@Repository
public interface DepartamentRepository extends JpaRepository<Departament, Long> {
	List<Departament> findAllByOrderByNameAsc();

}
