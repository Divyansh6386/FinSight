package com.midas.portfolio.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioResponse {

    private Long id;

    private String name;

    private String description;

    private BigDecimal totalValue;

}