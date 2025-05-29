package com.smartprocessrefusao.erprefusao.cadastros.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.cadastros.entities.Employee;
import com.smartprocessrefusao.erprefusao.cadastros.entities.Sector;
import com.smartprocessrefusao.erprefusao.projections.ReportEmployeeProjection;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query("SELECT obj FROM Employee obj JOIN FETCH obj.sector " +
		       "WHERE (:sectorId IS NULL OR obj.sector = :sectorId) " +
		       "AND (:name IS NULL OR LOWER(obj.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
		       "ORDER BY obj.name")
		Page<Employee> searchBySector(Sector sectorId, String name, Pageable pageable);
	
	@Query(value = """
            SELECT
                p.id AS id,
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
    Page<ReportEmployeeProjection> searchPeopleNameByOrId(
            @Param("name") String name, @Param("peopleId") Long peopleId, Pageable pageable);
}

