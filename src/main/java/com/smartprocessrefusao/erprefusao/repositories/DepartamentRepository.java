package com.smartprocessrefusao.erprefusao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.Departament;
import com.smartprocessrefusao.erprefusao.enumerados.EmployeePosition;

@Repository
public interface DepartamentRepository extends JpaRepository<Departament, Long> {
	List<Departament> findAllByOrderByNameAsc();
	
	  boolean existsByNameAndProcessAndPosition(
		        String name,
		        String process,
		        EmployeePosition position
		    );

		    boolean existsByNameAndProcessAndPositionAndIdNot(
		        String name,
		        String process,
		        EmployeePosition position,
		        Long id
		    );

}
