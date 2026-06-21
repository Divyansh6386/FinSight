package com.midas.stock.repository;

import com.midas.portfolio.entity.Portfolio;
import com.midas.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findByPortfolio(Portfolio portfolio);

}