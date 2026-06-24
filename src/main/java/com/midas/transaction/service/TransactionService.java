package com.midas.transaction.service;

import com.midas.transaction.dto.request.BuyStockRequest;
import com.midas.transaction.dto.request.SellStockRequest;
import com.midas.transaction.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

    TransactionResponse buyStock(
            String email,
            BuyStockRequest request
    );

    TransactionResponse sellStock(
            String email,
            SellStockRequest request
    );

    List<TransactionResponse> getTransactions(
            String email,
            Long stockId
    );
}