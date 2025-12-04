package com.smartprocessrefusao.erprefusao.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.Material;
import com.smartprocessrefusao.erprefusao.projections.MaterialReportProjection;

import jakarta.transaction.Transactional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

	@Query(value = """
				SELECT
					m.code,
					m.description As Description,
					m.type,
					u.id as unitId,
					u.acronym,
					t.id As taxClassId,
					t.description as TaxClassification,
					t.ncm_code As ncmCode,
					mg.id As matGroupId,
					mg.description as material_Group
				FROM tb_material m
				INNER JOIN tb_unit u ON u.id = m.unit_id
				INNER JOIN tb_tax_classification t ON t.id = m.tax_class_id
				INNER JOIN tb_material_group mg ON mg.id = m.material_group_id
				WHERE (:groupId IS NULL OR m.material_group_id = :groupId)
				AND (:description IS NULL OR LOWER(m.description) LIKE LOWER(CONCAT('%', :description, '%')))
				AND (:code IS NULL OR m.code = :code)
				AND (:type IS NULL OR LOWER(m.type) LIKE LOWER(CONCAT('%', :type, '%')))
				ORDER BY m.id
			""", countQuery = """
				SELECT COUNT(m.id)
					FROM tb_material m
					INNER JOIN tb_unit u ON u.id = m.unit_id
					INNER JOIN tb_tax_classification t ON t.id = m.tax_class_id
					INNER JOIN tb_material_group mg ON mg.id = m.material_group_id
					WHERE (:groupId IS NULL OR m.material_group_id = :groupId)
					AND (:description IS NULL OR LOWER(m.description) LIKE LOWER(CONCAT('%', :description, '%')))
					AND (:code IS NULL OR m.code = :code)
					AND (:type IS NULL OR LOWER(m.type) LIKE LOWER(CONCAT('%', :type, '%')))
			""", nativeQuery = true)
	Page<MaterialReportProjection> searchMaterialByNameOrGroup(@Param("type") String type, @Param("code") Long code, @Param("description") String description,
			@Param("groupId") Long groupId, Pageable pageable);

	Optional<Material> findByCode(Long code);
	
	@Transactional
    void deleteByCode(Long code);
	
	boolean existsByCode(Long code);
}
