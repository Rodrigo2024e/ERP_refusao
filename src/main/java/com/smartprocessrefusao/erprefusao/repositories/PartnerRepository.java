package com.smartprocessrefusao.erprefusao.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.Partner;
import com.smartprocessrefusao.erprefusao.projections.ReportPartnerProjection;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {

	@Query(value = """
			    SELECT
			        p.id AS id,
			        p.name AS name,
			        pa.cnpj AS cnpj,
			        pa.ie AS ie,
			        p.email AS email,
			        p.cell_phone AS cellPhone,
			        p.telephone AS telephone,
			        pa.supplier AS supplier,
			        pa.client AS client,
			        pa.active AS active,
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
			    INNER JOIN tb_partner pa ON pa.id = p.id
			    INNER JOIN tb_address a ON a.people_id = p.id
			    INNER JOIN tb_city c ON c.id = a.city_id
			    WHERE (:partnerId IS NULL OR p.id = :partnerId)
			    AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
			    ORDER BY p.name
			""", countQuery = """
			    SELECT COUNT(p.id)
			    FROM tb_people p
			    INNER JOIN tb_partner pa ON pa.id = p.id
			    INNER JOIN tb_address a ON a.people_id = p.id
			    INNER JOIN tb_city c ON c.id = a.city_id
			    WHERE (:partnerId IS NULL OR p.id = :partnerId)
			    AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
			""", nativeQuery = true)
	Page<ReportPartnerProjection> searchPeopleNameByOrId(@Param("name") String name, @Param("partnerId") Long partnerId,
			Pageable pageable);
}
