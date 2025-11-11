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
                a.id AS id,
                a.street AS street,
                a.number AS number,
                a.complement AS complement,
                a.neighborhood AS neighborhood,
                a.zip_code AS zipCode,
                c.id AS CityId,
                c.name As name,
                c.state AS state,
                a.people_id AS peopleId
            FROM tb_address a
            INNER JOIN tb_city c ON a.city_id = c.id
            LEFT JOIN tb_people p ON a.people_id = p.id
            WHERE (:addressId IS NULL OR a.id = :addressId) 
            AND (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) 
            ORDER BY c.name
        """,
        countQuery = """
            SELECT COUNT(a.id)
            FROM tb_address a
            INNER JOIN tb_city c ON a.city_id = c.id
            WHERE (:addressId IS NULL OR a.id = :addressId)
            AND (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))
        """,
        nativeQuery = true)
    Page<AddressProjection> searchAddressesByCityNameOrId( 
            @Param("name") String name,
            @Param("addressId") Long addressId, 
            Pageable pageable);

			@Query(value = """
			SELECT
			    a.id AS idAddress,
			    a.street AS street,
			    a.number AS numberAddress,
			    a.complement AS complement,
			    a.neighborhood AS neighborhood,
			    a.zip_code AS zipCode,
			    c.id AS cityId,
			    c.name,
			    c.state AS ufState,  
			    a.people_id AS peopleId
			FROM tb_address a
			INNER JOIN tb_city c ON a.city_id = c.id
			LEFT JOIN tb_people p ON a.people_id = p.id
			WHERE a.id = :id
			""", nativeQuery = true)
			Optional<AddressProjection> findProjectionById(@Param("id") Long id);
	
			  @Query(value = """
			            SELECT
			                a.id AS idAddress,
			                a.street AS street,
			                a.number AS numberAddress,
			                a.complement AS complement,
			                a.neighborhood AS neighborhood,
			                a.zip_code AS zipCode,
			                c.id AS cityId,
			                c.name,
			                c.state AS ufState,
			                a.people_id AS peopleId
			            FROM tb_address a
			            INNER JOIN tb_city c ON a.city_id = c.id
			            LEFT JOIN tb_people p ON a.people_id = p.id
			            ORDER BY c.name
			        """,
			        countQuery = """
			            SELECT COUNT(a.id)
			            FROM tb_address a
			        """,
			        nativeQuery = true)
			    Page<AddressProjection> findAllProjections(Pageable pageable);

			
}
	