package com.midas.analytics.service;

import com.midas.analytics.dto.response.AssetAllocationResponse;
import com.midas.analytics.dto.response.DashboardResponse;
import com.midas.analytics.dto.response.PortfolioSummaryResponse;
import com.midas.analytics.dto.response.TopPerformingStockResponse;

import java.util.List;

public interface AnalyticsService {

    /**
     * Returns portfolio summary including
     * total investment, current value and profit.
     */
    PortfolioSummaryResponse getPortfolioSummary(
            String email
    );

    /**
     * Returns asset allocation of all stocks.
     */
    List<AssetAllocationResponse> getAssetAllocation(
            String email
    );

    /**
     * Returns the top performing stock.
     */
    TopPerformingStockResponse getTopPerformingStock(
            String email
    );

    /**
     * Returns dashboard statistics.
     */
    DashboardResponse getDashboard(
            String email
    );
}