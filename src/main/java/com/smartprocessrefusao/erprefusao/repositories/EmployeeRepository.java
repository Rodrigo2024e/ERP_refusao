package com.smartprocessrefusao.erprefusao.repositories;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.projections.EmployeeDepartamentReportProjection;
import com.smartprocessrefusao.erprefusao.projections.EmployeeReportProjection;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query(value = """
            SELECT
                p.id AS id,
                p.name AS name,
                p.email As email,
                p.cell_phone As cellPhone,
                p.telephone As telephone,
                e.cpf As cpf,
                e.date_of_birth AS dateOfBirth,
                e.departament_id AS departamentId,
                d.name As departament, 
                e.position,
                d.process AS process
                        
            FROM tb_people p
            INNER JOIN tb_employee e ON e.id = p.id
            INNER JOIN tb_departament d ON d.id = e.departament_id
            WHERE (:departamentId IS NULL OR d.id = :departamentId)
            AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
            ORDER BY p.name
        """,
        countQuery = """
            SELECT COUNT(p.id)
            FROM tb_people p
        	INNER JOIN tb_employee e ON e.id = p.id
            INNER JOIN tb_departament d ON d.id = e.departament_id
            WHERE (:departamentId IS NULL OR s.id = :departamentId)
            AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
        """,
        nativeQuery = true)
    Page<EmployeeDepartamentReportProjection> searchEmployeeByDepartament(
            @Param("name") String name, @Param("departamentId") Long departamentId, Pageable pageable);


	@Query(value = """
            SELECT
                p.id AS id,
                p.name AS name,
                e.cpf AS cpf,
                e.date_of_birth AS dateOfBirth,
                p.email AS email,
                p.cell_phone AS cellPhone,
                p.telephone AS telephone,
                e.id AS departamentId,
                d.name AS departament,
                d.process AS process,
                e.position,
                a.id AS idAddress,
                a.street AS street,
                a.number AS numberAddress,
                a.complement AS complement,
                a.neighborhood AS neighborhood,
                a.zip_code AS zipCode,
                a.city_id AS cityId,
                c.name AS city,
                c.state AS state
            FROM tb_people p
            INNER JOIN tb_employee e ON e.id = p.id
            INNER JOIN tb_departament d ON d.id = e.departament_id
            INNER JOIN tb_address a ON a.people_id = p.id
            INNER JOIN tb_city c ON c.id = a.city_id
            WHERE (:peopleId IS NULL OR p.id = :peopleId)
            AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
            ORDER BY p.name
        """,
        countQuery = """
            SELECT COUNT(p.id)
            FROM tb_people p
            INNER JOIN tb_employee e ON e.id = p.id
            INNER JOIN tb_departament d ON d.id = e.departament_id
            INNER JOIN tb_address a ON a.people_id = p.id
            INNER JOIN tb_city c ON c.id = a.city_id
            WHERE (:peopleId IS NULL OR p.id = :peopleId)
            AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
        """,
        nativeQuery = true)
    Page<EmployeeReportProjection> searchPeopleNameByOrId(
            @Param("name") String name, @Param("peopleId") Long peopleId, Pageable pageable);
	
	  Optional<Employee> findByEmail(String email);
	  boolean existsByEmail(String email);
	  
	  Optional<Employee> findByCpf(String cpf);
	  boolean existsByCpf(String cpf);
	  

}
