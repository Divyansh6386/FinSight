package com.midas.stock.entity;

import com.midas.common.entity.BaseEntity;
import com.midas.portfolio.entity.Portfolio;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "stocks")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock extends BaseEntity {

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal averageBuyPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;
}