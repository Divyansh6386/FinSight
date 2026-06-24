package com.midas.watchlist.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateWatchlistRequest {

    @NotBlank(message = "Watchlist name is required")
    @Size(max = 100, message = "Watchlist name cannot exceed 100 characters")
    private String name;
}