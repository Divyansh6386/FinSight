package com.midas.analytics.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetAllocationResponse {

    private String symbol;

    private Integer quantity;

    private BigDecimal investment;

    private Double allocationPercentage;
}