package com.midas.analytics.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioSummaryResponse {

    private BigDecimal totalInvestment;

    private BigDecimal currentValue;

    private BigDecimal profit;

    private Double profitPercentage;
}