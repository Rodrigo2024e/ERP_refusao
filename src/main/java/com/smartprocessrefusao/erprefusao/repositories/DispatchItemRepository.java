package com.smartprocessrefusao.erprefusao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.DispatchItem;
import com.smartprocessrefusao.erprefusao.entities.PK.DispatchItemPK;

@Repository
public interface DispatchItemRepository extends JpaRepository<DispatchItem, DispatchItemPK> {

}
