package com.smartprocessrefusao.erprefusao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.ReceiptItem;
import com.smartprocessrefusao.erprefusao.entities.PK.ReceiptItemPK;

@Repository
public interface ReceiptItemRepository extends JpaRepository<ReceiptItem, ReceiptItemPK> {

}
