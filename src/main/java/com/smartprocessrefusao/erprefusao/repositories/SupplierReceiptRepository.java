package com.smartprocessrefusao.erprefusao.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.SupplierReceipt;
import com.smartprocessrefusao.erprefusao.projections.SupplierReceiptReportProjection;

@Repository
public interface SupplierReceiptRepository extends JpaRepository<SupplierReceipt, Long> {

	@Query(value = """
			SELECT
			    m.id,
			    m.type_material As typeMaterial,
			    sr.date_receipt As dateReceipt,
			    i.id As supplierId,
			    i.description AS supplierDescription,
			    sr.id,
			    p.id AS partnerId, p.name AS partnerName,
			    sr.transaction As transactionDescription, sr.costs, sr.amount_supplier As amountSupplier, sr.unit_value,
			    sr.total_value
			FROM tb_supplier_receipt sr
			JOIN tb_input i ON i.id = sr.input_id
			JOIN tb_material m ON m.id = i.id
			JOIN tb_people p ON p.id = sr.partner_id
			WHERE (:inputId IS NULL OR sr.input_id = :inputId)
			ORDER BY sr.input_id
			""", countQuery = """
			SELECT COUNT(*)
			FROM tb_supplier_receipt sr
			JOIN tb_input i ON i.id = sr.input_id
			JOIN tb_material m ON m.id = i.id
			JOIN tb_people p ON p.id = sr.partner_id
				WHERE (:inputId IS NULL OR sr.input_id = :inputId)
			""", nativeQuery = true)
	Page<SupplierReceiptReportProjection> searchSupplierReceiptByinputId(@Param("inputId") Long input,
			Pageable pageable);

}
