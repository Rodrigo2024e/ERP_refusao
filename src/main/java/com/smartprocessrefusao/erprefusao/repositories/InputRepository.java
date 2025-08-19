package com.smartprocessrefusao.erprefusao.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.Input;
import com.smartprocessrefusao.erprefusao.projections.ReportInputProjection;

@Repository
public interface InputRepository extends JpaRepository<Input, Long> {
	
	@Query(value = """
			SELECT
				m.id, 
				m.type_material, 
				i.description As Description,
				u.acronym As Unit, 
				t.id As taxClassId, 
				t.description as Tax_Classification,
				t.number, 
				mg.id As matGroupId,
				mg.description as material_Group
			FROM tb_material m
			INNER JOIN tb_input i ON i.id = m.id
			INNER JOIN tb_uom u ON u.id = m.uom_material_id
			INNER JOIN tb_tax_classification t ON t.id = m.tax_class_material_id
			INNER JOIN tb_material_group mg ON mg.id = m.material_group_id
			WHERE (:groupId IS NULL OR m.material_group_id = :groupId)
			AND (:description IS NULL OR LOWER(i.description) LIKE LOWER(CONCAT('%', :description, '%')))
			ORDER BY i.description
		""", 
		countQuery = """
			SELECT COUNT(m.id)
				FROM tb_material m
				INNER JOIN tb_input i ON i.id = m.id
				INNER JOIN tb_uom u ON u.id = m.uom_material_id
				INNER JOIN tb_tax_classification t ON t.id = m.tax_class_material_id
				INNER JOIN tb_material_group mg ON mg.id = m.material_group_id
				WHERE (:groupId IS NULL OR m.material_group_id = :groupId)
				AND (:description IS NULL OR LOWER(i.description) LIKE LOWER(CONCAT('%', :description, '%')))
		""",
		nativeQuery = true)
	Page<ReportInputProjection> searchMaterialByNameOrGroup(
			@Param("description") String description, @Param("groupId") Long groupId, Pageable pageable);
}
