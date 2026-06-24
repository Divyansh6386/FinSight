package com.midas.watchlist.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WatchlistItemResponse {

    private Long id;

    private String symbol;

    private String companyName;
}