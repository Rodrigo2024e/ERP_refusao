package com.smartprocessrefusao.erprefusao.cadastros.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.cadastros.entities.Sector;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
	
/*
	@Query(nativeQuery = true, value = """
			SELECT * FROM (
			SELECT DISTINCT s.id, s.setor_nome
			FROM tb_setor s
			INNER JOIN tb_funcionario f ON f.setor_id = s.id
			WHERE (:funcionarioIds IS NULL OR f.setor_id IN (:funcionarioIds))
			AND (LOWER(s.setor_nome) LIKE LOWER(CONCAT('%',:setor_nome,'%')))
			) AS tb_result
			""",
			countQuery = """
			SELECT COUNT(*) FROM (
			SELECT DISTINCT s.id, s.setor_nome
			FROM tb_setor s
			INNER JOIN tb_funcionario f ON f.setor_id = s.id
			WHERE (:funcionarioIds IS NULL OR f.setor_id IN (:funcionarioIds))
			AND (LOWER(s.setor_nome) LIKE LOWER(CONCAT('%',:setor_nome,'%')))
			) AS tb_result
			""")

		Page<SetorProjection> searchSetor(List<Long> funcionarioIds, String setor_nome, Pageable pageable);

		@Query("SELECT obj FROM Setor obj JOIN FETCH obj.funcionarios "
				+ "WHERE obj.id IN :setorIds")
		List<Setor> searchSetorWithFuncionario(List<Long> setorIds);
*/
}
