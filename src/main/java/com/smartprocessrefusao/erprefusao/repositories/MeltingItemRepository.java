package com.smartprocessrefusao.erprefusao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.MeltingItem;
import com.smartprocessrefusao.erprefusao.entities.PK.MeltingItemPK;
import com.smartprocessrefusao.erprefusao.projections.MeltingItemProjection;

@Repository
public interface MeltingItemRepository extends JpaRepository<MeltingItem, MeltingItemPK> {

	@Query("""
			   SELECT
			       mi.id.melting.id AS meltingId,
			       mi.id.itemSequence AS itemSequence,
			       m.materialCode AS materialCode,
			       m.description AS description,
			       mi.quantity AS quantity,
			       mi.averagePrice AS averagePrice,
			       mi.totalValue AS totalValue,
			       mi.averageRecoveryYield AS averageRecoveryYield,
			       mi.quantityMco AS quantityMco,
			       mi.slagWeight AS slagWeight
			   FROM MeltingItem mi
			   JOIN mi.id.material m
			   WHERE mi.id.melting.id IN :meltingIds
			""")
	List<MeltingItemProjection> findItemsByMeltingIds(@Param("meltingIds") List<Long> meltingIds);

}
