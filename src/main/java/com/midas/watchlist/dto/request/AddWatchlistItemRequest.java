package com.midas.watchlist.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddWatchlistItemRequest {

    @NotBlank(message = "Stock symbol is required")
    @Size(max = 20, message = "Stock symbol cannot exceed 20 characters")
    private String symbol;

    @NotBlank(message = "Company name is required")
    @Size(max = 150, message = "Company name cannot exceed 150 characters")
    private String companyName;
}