package com.midas.stock.repository;

import com.midas.portfolio.entity.Portfolio;
import com.midas.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findByPortfolio(Portfolio portfolio);

    Optional<Stock> findByPortfolioAndSymbol(
            Portfolio portfolio,
            String symbol
    );

    @Query("""
        SELECT s
        FROM Stock s
        JOIN FETCH s.portfolio p
        JOIN FETCH p.user
        WHERE s.id = :id
    """)
    Optional<Stock> findByIdWithPortfolioAndUser(@Param("id") Long id);
}