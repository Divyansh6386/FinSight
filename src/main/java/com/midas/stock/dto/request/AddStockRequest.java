package com.midas.stock.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddStockRequest {

    @NotNull(message = "Portfolio ID is required")
    private Long portfolioId;

    @NotBlank(message = "Stock symbol is required")
    private String symbol;

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than 0")
    private Integer quantity;

    @NotNull(message = "Average buy price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal averageBuyPrice;
}