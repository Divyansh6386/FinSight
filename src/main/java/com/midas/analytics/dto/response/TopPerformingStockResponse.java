package com.midas.analytics.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopPerformingStockResponse {

    private String symbol;

    private String companyName;

    private BigDecimal investment;

    private BigDecimal currentValue;

    private BigDecimal profit;
}