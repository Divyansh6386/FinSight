package com.midas.watchlist.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WatchlistDetailsResponse {

    private Long id;

    private String name;

    private LocalDateTime createdAt;

    private List<WatchlistItemResponse> items;
}