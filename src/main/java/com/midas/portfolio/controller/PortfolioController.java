package com.midas.portfolio.controller;

import com.midas.auth.security.CustomUserDetails;
import com.midas.common.response.ApiResponse;
import com.midas.portfolio.dto.request.CreatePortfolioRequest;
import com.midas.portfolio.dto.request.UpdatePortfolioRequest;
import com.midas.portfolio.dto.response.PortfolioResponse;
import com.midas.portfolio.service.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping
    public ApiResponse<PortfolioResponse> createPortfolio(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CreatePortfolioRequest request) {

        return ApiResponse.<PortfolioResponse>builder()
                .success(true)
                .message("Portfolio created successfully.")
                .data(portfolioService.createPortfolio(
                        userDetails.getUsername(),
                        request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<PortfolioResponse>> getMyPortfolios(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        return ApiResponse.<List<PortfolioResponse>>builder()
                .success(true)
                .message("Portfolios fetched successfully.")
                .data(portfolioService.getMyPortfolios(
                        userDetails.getUsername()))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<PortfolioResponse> getPortfolioById(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id) {

        return ApiResponse.<PortfolioResponse>builder()
                .success(true)
                .message("Portfolio fetched successfully.")
                .data(portfolioService.getPortfolioById(
                        userDetails.getUsername(),
                        id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<PortfolioResponse> updatePortfolio(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id,
            @Valid @RequestBody UpdatePortfolioRequest request) {

        return ApiResponse.<PortfolioResponse>builder()
                .success(true)
                .message("Portfolio updated successfully.")
                .data(portfolioService.updatePortfolio(
                        userDetails.getUsername(),
                        id,
                        request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePortfolio(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id) {

        portfolioService.deletePortfolio(
                userDetails.getUsername(),
                id);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Portfolio deleted successfully.")
                .build();
    }
}