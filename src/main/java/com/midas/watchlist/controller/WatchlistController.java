package com.midas.watchlist.controller;

import com.midas.auth.security.CustomUserDetails;
import com.midas.common.response.ApiResponse;
import com.midas.watchlist.dto.request.AddWatchlistItemRequest;
import com.midas.watchlist.dto.request.CreateWatchlistRequest;
import com.midas.watchlist.dto.request.UpdateWatchlistRequest;
import com.midas.watchlist.dto.response.WatchlistDetailsResponse;
import com.midas.watchlist.dto.response.WatchlistItemResponse;
import com.midas.watchlist.dto.response.WatchlistResponse;
import com.midas.watchlist.service.WatchlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/watchlists")
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;

    @PostMapping
    public ApiResponse<WatchlistResponse> createWatchlist(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody CreateWatchlistRequest request) {

        return ApiResponse.<WatchlistResponse>builder()
                .success(true)
                .message("Watchlist created successfully.")
                .data(watchlistService.createWatchlist(user.getUsername(), request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<WatchlistResponse>> getMyWatchlists(
            @AuthenticationPrincipal CustomUserDetails user) {

        return ApiResponse.<List<WatchlistResponse>>builder()
                .success(true)
                .message("Watchlists fetched successfully.")
                .data(watchlistService.getMyWatchlists(user.getUsername()))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<WatchlistDetailsResponse> getWatchlist(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long id) {

        return ApiResponse.<WatchlistDetailsResponse>builder()
                .success(true)
                .message("Watchlist fetched successfully.")
                .data(watchlistService.getWatchlist(user.getUsername(), id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<WatchlistResponse> updateWatchlist(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long id,
            @Valid @RequestBody UpdateWatchlistRequest request) {

        return ApiResponse.<WatchlistResponse>builder()
                .success(true)
                .message("Watchlist updated successfully.")
                .data(watchlistService.updateWatchlist(user.getUsername(), id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteWatchlist(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long id) {

        watchlistService.deleteWatchlist(user.getUsername(), id);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Watchlist deleted successfully.")
                .build();
    }

    @PostMapping("/{id}/items")
    public ApiResponse<WatchlistItemResponse> addItem(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long id,
            @Valid @RequestBody AddWatchlistItemRequest request) {

        return ApiResponse.<WatchlistItemResponse>builder()
                .success(true)
                .message("Stock added to watchlist successfully.")
                .data(watchlistService.addItem(user.getUsername(), id, request))
                .build();
    }

    @DeleteMapping("/{watchlistId}/items/{itemId}")
    public ApiResponse<Void> removeItem(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long watchlistId,
            @PathVariable Long itemId) {

        watchlistService.removeItem(
                user.getUsername(),
                watchlistId,
                itemId
        );

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Stock removed from watchlist successfully.")
                .build();
    }
}