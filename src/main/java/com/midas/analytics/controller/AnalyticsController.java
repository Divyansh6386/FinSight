package com.midas.analytics.controller;

import com.midas.analytics.dto.response.AssetAllocationResponse;
import com.midas.analytics.dto.response.DashboardResponse;
import com.midas.analytics.dto.response.PortfolioSummaryResponse;
import com.midas.analytics.dto.response.TopPerformingStockResponse;
import com.midas.analytics.service.AnalyticsService;
import com.midas.auth.security.CustomUserDetails;
import com.midas.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/portfolio")
    public ApiResponse<PortfolioSummaryResponse> getPortfolioSummary(
            @AuthenticationPrincipal CustomUserDetails user) {

        return ApiResponse.<PortfolioSummaryResponse>builder()
                .success(true)
                .message("Portfolio summary fetched successfully.")
                .data(analyticsService.getPortfolioSummary(user.getUsername()))
                .build();
    }

    @GetMapping("/allocation")
    public ApiResponse<List<AssetAllocationResponse>> getAssetAllocation(
            @AuthenticationPrincipal CustomUserDetails user) {

        return ApiResponse.<List<AssetAllocationResponse>>builder()
                .success(true)
                .message("Asset allocation fetched successfully.")
                .data(analyticsService.getAssetAllocation(user.getUsername()))
                .build();
    }

    @GetMapping("/top-performing")
    public ApiResponse<TopPerformingStockResponse> getTopPerformingStock(
            @AuthenticationPrincipal CustomUserDetails user) {

        return ApiResponse.<TopPerformingStockResponse>builder()
                .success(true)
                .message("Top performing stock fetched successfully.")
                .data(analyticsService.getTopPerformingStock(user.getUsername()))
                .build();
    }

    @GetMapping("/dashboard")
    public ApiResponse<DashboardResponse> getDashboard(
            @AuthenticationPrincipal CustomUserDetails user) {

        return ApiResponse.<DashboardResponse>builder()
                .success(true)
                .message("Dashboard fetched successfully.")
                .data(analyticsService.getDashboard(user.getUsername()))
                .build();
    }
}