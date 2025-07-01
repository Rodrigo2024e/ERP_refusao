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
				pr.id, 
				pr.description, 
				pr.alloy, 
				pr.inch,
				pr.length,
				u.acronym as unit, 
				t.description as Tax_Classification, 
				t.number, 
				p.description as Product_Group
			FROM tb_product pr
			INNER JOIN tb_uom u ON u.id = pr.uom_id
			INNER JOIN tb_tax_classification t ON t.id = pr.taxclass_id
			INNER JOIN tb_product_group p ON p.id = pr.prod_group_id
			WHERE (:productId IS NULL OR pr.id = :productId)
			AND (:description IS NULL OR LOWER(pr.description) LIKE LOWER(CONCAT('%', :description, '%')))
			ORDER BY pr.description
		""", 
		countQuery = """
			SELECT COUNT(m.id)
				FROM tb_product pr
				INNER JOIN tb_uom u ON u.id = pr.uom_id
				INNER JOIN tb_tax_classification t ON t.id = pr.taxclass_id
				INNER JOIN tb_product_group p ON p.id = pr.prod_group_id
				WHERE (:productId IS NULL OR m.id = :productId)
				AND (:description IS NULL OR LOWER(pr.description) LIKE LOWER(CONCAT('%', :description, '%')))
		""",
		nativeQuery = true)
	Page<ReportProductProjection> searchProductByNameOrId(
			@Param("description") String description, @Param("productId") Long productId, Pageable pageable);
}
