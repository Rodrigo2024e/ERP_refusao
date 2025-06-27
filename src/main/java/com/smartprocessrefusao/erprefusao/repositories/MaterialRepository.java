package com.smartprocessrefusao.erprefusao.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.projections.ReportMaterialProjection;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
	
	@Query(value = """
			SELECT
				m.id, 
				m.description, 
				u.acronym as Unit, 
				t.description as Tax_Classification, 
				t.number, 
				p.description as Product_Group
			FROM tb_material m
			INNER JOIN tb_uom u ON u.id = m.uom_id
			INNER JOIN tb_tax_classification t ON t.id = m.taxclass_id
			INNER JOIN tb_product_group p ON p.id = m.prod_group_id
			WHERE (:materialId IS NULL OR m.id = :materialId)
			AND (:description IS NULL OR LOWER(m.description) LIKE LOWER(CONCAT('%', :description, '%')))
			ORDER BY m.description
		""", 
		countQuery = """
			SELECT COUNT(m.id)
				FROM tb_material m
				INNER JOIN tb_uom u ON u.id = m.uom_id
				INNER JOIN tb_tax_classification t ON t.id = m.taxclass_id
				INNER JOIN tb_product_group p ON p.id = m.prod_group_id
				WHERE (:materialId IS NULL OR m.id = :materialId)
				AND (:description IS NULL OR LOWER(m.description) LIKE LOWER(CONCAT('%', :description, '%')))
		""",
		nativeQuery = true)
	Page<ReportMaterialProjection> searchMaterialByNameOrId(
			@Param("description") String description, @Param("materialId") Long materialId, Pageable pageable);
}
