package com.smartprocessrefusao.erprefusao.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.Product;
import com.smartprocessrefusao.erprefusao.projections.ReportProductProjection;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query(value = """
			SELECT
				m.id, 
				m.type_material, 
				pr.description, 
				pr.alloy, 
				pr.billet_diameter,
				pr.billet_length,
				u.acronym as unit, 
				t.description as Tax_Classification, 
				t.number, 
				mg.id As matGroupId,
				mg.description as material_Group
			FROM tb_material m
			INNER JOIN tb_product pr ON pr.id = m.id
			INNER JOIN tb_uom u ON u.id = m.uom_material_id
			INNER JOIN tb_tax_classification t ON t.id = m.tax_class_material_id
			INNER JOIN tb_material_group mg ON mg.id = m.material_group_id
			WHERE (:productId IS NULL OR pr.id = :productId)
			AND (:alloy IS NULL OR LOWER(pr.alloy) LIKE LOWER(CONCAT('%', :alloy, '%')))
			ORDER BY pr.alloy
		""", 
		countQuery = """
			SELECT COUNT(m.id)
				FROM tb_material m
				INNER JOIN tb_product pr ON pr.id = m.id
				INNER JOIN tb_uom u ON u.id = m.uom_material_id
				INNER JOIN tb_tax_classification t ON t.id = m.tax_class_material_id
			INNER JOIN tb_material_group mg ON mg.id = m.material_group_id
				WHERE (:productId IS NULL OR m.id = :productId)
				AND (:alloy IS NULL OR LOWER(pr.alloy) LIKE LOWER(CONCAT('%', :alloy, '%')))
		""",
		nativeQuery = true)
	Page<ReportProductProjection> searchProductByNameOrId(
			@Param("alloy") Integer alloy, @Param("productId") Long productId, Pageable pageable);
}
