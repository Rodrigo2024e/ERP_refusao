package com.smartprocessrefusao.erprefusao.cadastros.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.cadastros.entities.Parceiro;

@Repository
public interface ParceiroRepository extends JpaRepository<Parceiro, Long> {

}
