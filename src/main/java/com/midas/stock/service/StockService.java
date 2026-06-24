package com.midas.stock.service;

import com.midas.stock.dto.request.AddStockRequest;
import com.midas.stock.dto.response.StockResponse;

import java.util.List;

public interface StockService {

    StockResponse addStock(
            String email,
            AddStockRequest request
    );

    List<StockResponse> getStocksByPortfolio(
            String email,
            Long portfolioId
    );
}