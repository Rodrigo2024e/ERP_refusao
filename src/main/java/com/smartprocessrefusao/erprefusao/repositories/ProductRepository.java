package com.smartprocessrefusao.erprefusao.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.Product;
import com.smartprocessrefusao.erprefusao.projections.ProductReportProjection;

import jakarta.transaction.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query(value = """
				SELECT
					p.product_code,
					p.description As Description,
					p.alloy As Alloy,
					p.alloy_pol As AlloyPol,
					p.alloy_footage As AlloyFootage,
					u.id as unitId,
					u.acronym,
					t.id As taxClassId,
					t.description as descriptionTaxclass,
					t.ncm_code As ncmCode,
					mg.id As matGroupId,
					mg.description As descriptionMatGroup
				FROM tb_product p
				INNER JOIN tb_unit u ON u.id = p.unit_id
				INNER JOIN tb_tax_classification t ON t.id = p.tax_class_id
				INNER JOIN tb_material_group mg ON mg.id = p.material_group_id
				WHERE (:groupId IS NULL OR p.material_group_id = :groupId)
				AND (:description IS NULL OR LOWER(p.description) LIKE LOWER(CONCAT('%', :description, '%')))
				AND (:productCode IS NULL OR p.product_code = :productCode)
				AND (:alloy IS NULL OR LOWER(p.alloy) LIKE LOWER(CONCAT('%', :alloy, '%')))
				ORDER BY p.product_code
			""", countQuery = """
				SELECT COUNT(p.id)
				FROM tb_produtc p
				INNER JOIN tb_unit u ON u.id = p.unit_id
				INNER JOIN tb_tax_classification t ON t.id = p.tax_class_id
				INNER JOIN tb_material_group mg ON mg.id = p.material_group_id
				WHERE (:groupId IS NULL OR p.material_group_id = :groupId)
				AND (:description IS NULL OR LOWER(p.description) LIKE LOWER(CONCAT('%', :description, '%')))
				AND (:productCode IS NULL OR p.product_code = :productCode)
				AND (:alloy IS NULL OR LOWER(p.alloy) LIKE LOWER(CONCAT('%', :alloy, '%')))
			""", nativeQuery = true)
	Page<ProductReportProjection> searchProductByNameOrGroup(
			@Param("alloy") String alloy,
			@Param("productCode") Long productCode, 
			@Param("description") String description,
			@Param("groupId") Long groupId, 
			Pageable pageable);

	Optional<Product> findByProductCode(Long productCode);
	
	@Transactional
    void deleteByProductCode(Long productCode);
	
	boolean existsByProductCode(Long productCode);
}
