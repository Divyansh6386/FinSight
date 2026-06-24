package com.midas.transaction.controller;

import com.midas.auth.security.CustomUserDetails;
import com.midas.common.response.ApiResponse;
import com.midas.transaction.dto.request.BuyStockRequest;
import com.midas.transaction.dto.request.SellStockRequest;
import com.midas.transaction.dto.response.TransactionResponse;
import com.midas.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/buy")
    public ApiResponse<TransactionResponse> buyStock(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody BuyStockRequest request) {

        return ApiResponse.<TransactionResponse>builder()
                .success(true)
                .message("Stock purchased successfully.")
                .data(transactionService.buyStock(user.getUsername(), request))
                .build();
    }

    @PostMapping("/sell")
    public ApiResponse<TransactionResponse> sellStock(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody SellStockRequest request) {

        return ApiResponse.<TransactionResponse>builder()
                .success(true)
                .message("Stock sold successfully.")
                .data(transactionService.sellStock(user.getUsername(), request))
                .build();
    }

    @GetMapping("/stock/{stockId}")
    public ApiResponse<List<TransactionResponse>> getTransactions(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long stockId) {

        return ApiResponse.<List<TransactionResponse>>builder()
                .success(true)
                .message("Transaction history fetched successfully.")
                .data(transactionService.getTransactions(user.getUsername(), stockId))
                .build();
    }
}