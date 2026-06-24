package com.midas.transaction.repository;

import com.midas.stock.entity.Stock;
import com.midas.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    List<Transaction> findByStockOrderByCreatedAtDesc(Stock stock);
}