package com.midas.analytics.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private Integer totalPortfolios;

    private Integer totalStocks;

    private Integer totalTransactions;

    private Integer totalWatchlists;

    private BigDecimal portfolioValue;

    private BigDecimal totalProfit;
}