package com.midas.watchlist.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WatchlistResponse {

    private Long id;

    private String name;

    private LocalDateTime createdAt;
}