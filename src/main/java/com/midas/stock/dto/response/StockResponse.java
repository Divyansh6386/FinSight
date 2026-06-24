package com.midas.stock.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockResponse {

    private Long id;

    private String symbol;

    private String companyName;

    private Integer quantity;

    private BigDecimal averageBuyPrice;
}