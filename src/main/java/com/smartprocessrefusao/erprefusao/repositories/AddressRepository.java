package com.smartprocessrefusao.erprefusao.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.Address;
import com.smartprocessrefusao.erprefusao.projections.AddressProjection;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
	@Query(value = """
            SELECT
                a.id_address AS idAddress,
                a.street AS street,
                a.number_address AS numberAddress,
                a.complement AS complement,
                a.neighborhood AS neighborhood,
                a.zip_code AS zipCode,
                c.id AS cityId,
                c.name_city AS nameCity,
                c.uf_state AS ufState,
                a.people_id AS peopleId
            FROM tb_address a
            INNER JOIN tb_city c ON a.city_id = c.id
            LEFT JOIN tb_people p ON a.people_id = p.id
            WHERE (:addressId IS NULL OR a.id_address = :addressId) 
            AND (:nameCity IS NULL OR LOWER(c.name_city) LIKE LOWER(CONCAT('%', :nameCity, '%'))) 
            ORDER BY c.name_city
        """,
        countQuery = """
            SELECT COUNT(a.id_address)
            FROM tb_address a
            INNER JOIN tb_city c ON a.city_id = c.id
            WHERE (:addressId IS NULL OR a.id_address = :addressId)
            AND (:nameCity IS NULL OR LOWER(c.name_city) LIKE LOWER(CONCAT('%', :nameCity, '%')))
        """,
        nativeQuery = true)
    Page<AddressProjection> searchAddressesByCityNameOrId(
            @Param("nameCity") String nameCity,
            @Param("addressId") Long addressId, 
            Pageable pageable);

			@Query(value = """
			SELECT
			    a.id_address AS idAddress,
			    a.street AS street,
			    a.number_address AS numberAddress,
			    a.complement AS complement,
			    a.neighborhood AS neighborhood,
			    a.zip_code AS zipCode,
			    c.id AS cityId,
			    c.name_city AS nameCity,
			    c.uf_state AS ufState,  
			    a.people_id AS peopleId
			FROM tb_address a
			INNER JOIN tb_city c ON a.city_id = c.id
			LEFT JOIN tb_people p ON a.people_id = p.id
			WHERE a.id_address = :id
			""", nativeQuery = true)
			Optional<AddressProjection> findProjectionById(@Param("id") Long id);
	
			  @Query(value = """
			            SELECT
			                a.id_address AS idAddress,
			                a.street AS street,
			                a.number_address AS numberAddress,
			                a.complement AS complement,
			                a.neighborhood AS neighborhood,
			                a.zip_code AS zipCode,
			                c.id AS cityId,
			                c.name_city AS nameCity,
			                c.uf_state AS ufState,
			                a.people_id AS peopleId
			            FROM tb_address a
			            INNER JOIN tb_city c ON a.city_id = c.id
			            LEFT JOIN tb_people p ON a.people_id = p.id
			            ORDER BY c.name_city
			        """,
			        countQuery = """
			            SELECT COUNT(a.id_address)
			            FROM tb_address a
			        """,
			        nativeQuery = true)
			    Page<AddressProjection> findAllProjections(Pageable pageable);

			
}
	