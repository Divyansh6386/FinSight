package com.midas.watchlist.service;

import com.midas.watchlist.dto.request.AddWatchlistItemRequest;
import com.midas.watchlist.dto.request.CreateWatchlistRequest;
import com.midas.watchlist.dto.request.UpdateWatchlistRequest;
import com.midas.watchlist.dto.response.WatchlistDetailsResponse;
import com.midas.watchlist.dto.response.WatchlistItemResponse;
import com.midas.watchlist.dto.response.WatchlistResponse;

import java.util.List;

public interface WatchlistService {

    WatchlistResponse createWatchlist(
            String email,
            CreateWatchlistRequest request
    );

    List<WatchlistResponse> getMyWatchlists(
            String email
    );

    WatchlistDetailsResponse getWatchlist(
            String email,
            Long watchlistId
    );

    WatchlistResponse updateWatchlist(
            String email,
            Long watchlistId,
            UpdateWatchlistRequest request
    );

    void deleteWatchlist(
            String email,
            Long watchlistId
    );

    WatchlistItemResponse addItem(
            String email,
            Long watchlistId,
            AddWatchlistItemRequest request
    );

    void removeItem(
            String email,
            Long watchlistId,
            Long itemId
    );
}