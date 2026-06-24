package com.midas.stock.controller;

import com.midas.auth.security.CustomUserDetails;
import com.midas.common.response.ApiResponse;
import com.midas.stock.dto.request.AddStockRequest;
import com.midas.stock.dto.response.StockResponse;
import com.midas.stock.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping
    public ApiResponse<StockResponse> addStock(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody AddStockRequest request) {

        return ApiResponse.<StockResponse>builder()
                .success(true)
                .message("Stock added successfully.")
                .data(stockService.addStock(
                        userDetails.getUsername(),
                        request))
                .build();
    }

    @GetMapping("/portfolio/{portfolioId}")
    public ApiResponse<List<StockResponse>> getStocks(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long portfolioId) {

        return ApiResponse.<List<StockResponse>>builder()
                .success(true)
                .message("Stocks fetched successfully.")
                .data(stockService.getStocksByPortfolio(
                        userDetails.getUsername(),
                        portfolioId))
                .build();
    }
}