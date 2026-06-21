package com.midas.stock.service.impl;

import com.midas.portfolio.entity.Portfolio;
import com.midas.portfolio.repository.PortfolioRepository;
import com.midas.stock.dto.request.AddStockRequest;
import com.midas.stock.dto.response.StockResponse;
import com.midas.stock.entity.Stock;
import com.midas.stock.repository.StockRepository;
import com.midas.stock.service.StockService;
import com.midas.user.entity.User;
import com.midas.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final PortfolioRepository portfolioRepository;
    private final UserService userService;

    @Override
    public StockResponse addStock(String email, AddStockRequest request) {

        User user = userService.findByEmail(email)
                .orElseThrow(() ->
                        new NoSuchElementException("User not found"));

        Portfolio portfolio = portfolioRepository
                .findByIdAndUser(request.getPortfolioId(), user)
                .orElseThrow(() ->
                        new NoSuchElementException("Portfolio not found"));

        Stock stock = Stock.builder()
                .symbol(request.getSymbol().toUpperCase())
                .companyName(request.getCompanyName())
                .quantity(request.getQuantity())
                .averageBuyPrice(request.getAverageBuyPrice())
                .portfolio(portfolio)
                .build();

        Stock savedStock = stockRepository.save(stock);

        return map(savedStock);
    }

    @Override
    public List<StockResponse> getStocksByPortfolio(
            String email,
            Long portfolioId) {

        User user = userService.findByEmail(email)
                .orElseThrow(() ->
                        new NoSuchElementException("User not found"));

        Portfolio portfolio = portfolioRepository
                .findByIdAndUser(portfolioId, user)
                .orElseThrow(() ->
                        new NoSuchElementException("Portfolio not found"));

        return stockRepository.findByPortfolio(portfolio)
                .stream()
                .map(this::map)
                .toList();
    }

    private StockResponse map(Stock stock) {

        return StockResponse.builder()
                .id(stock.getId())
                .symbol(stock.getSymbol())
                .companyName(stock.getCompanyName())
                .quantity(stock.getQuantity())
                .averageBuyPrice(stock.getAverageBuyPrice())
                .build();
    }
}