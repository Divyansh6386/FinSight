package com.midas.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePortfolioRequest {

    @NotBlank(message = "Portfolio name is required")
    private String name;

    private String description;
}