package com.smartprocessrefusao.erprefusao.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.Employee;
import com.smartprocessrefusao.erprefusao.projections.EmployeeSectorProjection;
import com.smartprocessrefusao.erprefusao.projections.EmployeeReportProjection;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query(value = """
            SELECT
                p.id AS idPessoa,
                p.name AS name,
                p.email As email,
                p.cell_phone As cellPhone,
                p.telephone As telephone,
                e.cpf As cpf,
                e.rg As rg,
                e.sys_user,
                e.sector_id AS sectorId,
                s.name_sector AS nameSector,
                s.process AS process
                        
            FROM tb_people p
            INNER JOIN tb_employee e ON e.id = p.id
            INNER JOIN tb_sector s ON s.id = e.sector_id
            WHERE (:sectorId IS NULL OR s.id = :sectorId)
            AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
            ORDER BY p.name
        """,
        countQuery = """
            SELECT COUNT(p.id)
            FROM tb_people p
        	INNER JOIN tb_employee e ON e.id = p.id
            INNER JOIN tb_sector s ON s.id = e.sector_id
            WHERE (:sectorId IS NULL OR s.id = :sectorId)
            AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
        """,
        nativeQuery = true)
    Page<EmployeeSectorProjection> searchEmployeeBySector(
            @Param("name") String name, @Param("sectorId") Long sectorId, Pageable pageable);


	@Query(value = """
            SELECT
                p.id AS idPessoa,
                p.name AS name,
                e.cpf AS cpf,
                e.rg AS rg,
                p.email AS email,
                p.cell_phone AS cellPhone,
                p.telephone AS telephone,
                e.sys_user AS sysUser,
                e.sector_id AS sectorId,
                s.name_sector AS nameSector,
                s.process AS process,
                a.id_address AS idAddress,
                a.street AS street,
                a.number_address AS numberAddress,
                a.complement AS complement,
                a.neighborhood AS neighborhood,
                a.zip_code AS zipCode,
                a.city_id AS cityId,
                c.name_city AS nameCity,
                c.uf_state AS ufState
            FROM tb_people p
            INNER JOIN tb_employee e ON e.id = p.id
            INNER JOIN tb_sector s ON s.id = e.sector_id
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
            INNER JOIN tb_sector s ON s.id = e.sector_id
            INNER JOIN tb_address a ON a.people_id = p.id
            INNER JOIN tb_city c ON c.id = a.city_id
            WHERE (:peopleId IS NULL OR p.id = :peopleId)
            AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
        """,
        nativeQuery = true)
    Page<EmployeeReportProjection> searchPeopleNameByOrId(
            @Param("name") String name, @Param("peopleId") Long peopleId, Pageable pageable);

}
