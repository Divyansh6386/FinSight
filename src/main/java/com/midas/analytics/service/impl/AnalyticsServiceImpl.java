package com.midas.analytics.service.impl;

import com.midas.analytics.dto.response.AssetAllocationResponse;
import com.midas.analytics.dto.response.DashboardResponse;
import com.midas.analytics.dto.response.PortfolioSummaryResponse;
import com.midas.analytics.dto.response.TopPerformingStockResponse;
import com.midas.analytics.service.AnalyticsService;
import com.midas.portfolio.entity.Portfolio;
import com.midas.portfolio.repository.PortfolioRepository;
import com.midas.stock.entity.Stock;
import com.midas.stock.repository.StockRepository;
import com.midas.transaction.repository.TransactionRepository;
import com.midas.user.entity.User;
import com.midas.user.service.UserService;
import com.midas.watchlist.repository.WatchlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnalyticsServiceImpl implements AnalyticsService {

    private final UserService userService;
    private final PortfolioRepository portfolioRepository;
    private final StockRepository stockRepository;
    private final TransactionRepository transactionRepository;
    private final WatchlistRepository watchlistRepository;

    @Override
    public PortfolioSummaryResponse getPortfolioSummary(String email) {

        User user = userService.findByEmail(email)
                .orElseThrow(() ->
                        new NoSuchElementException("User not found"));

        List<Portfolio> portfolios = portfolioRepository.findByUser(user);

        BigDecimal totalInvestment = BigDecimal.ZERO;
        BigDecimal currentValue = BigDecimal.ZERO;

        for (Portfolio portfolio : portfolios) {

            List<Stock> stocks = stockRepository.findByPortfolio(portfolio);

            for (Stock stock : stocks) {

                BigDecimal investment =
                        stock.getAverageBuyPrice()
                                .multiply(BigDecimal.valueOf(stock.getQuantity()));

                totalInvestment = totalInvestment.add(investment);

                // For now, assume current price = average buy price.
                // Later we'll replace this with a Market Data API.
                currentValue = currentValue.add(investment);
            }
        }

        BigDecimal profit = currentValue.subtract(totalInvestment);

        Double profitPercentage = totalInvestment.compareTo(BigDecimal.ZERO) == 0
                ? 0.0
                : profit.multiply(BigDecimal.valueOf(100))
                        .divide(totalInvestment, 2, RoundingMode.HALF_UP)
                        .doubleValue();

        return PortfolioSummaryResponse.builder()
                .totalInvestment(totalInvestment)
                .currentValue(currentValue)
                .profit(profit)
                .profitPercentage(profitPercentage)
                .build();
    }
    @Override
    public List<AssetAllocationResponse> getAssetAllocation(String email) {

    User user = userService.findByEmail(email)
            .orElseThrow(() ->
                    new NoSuchElementException("User not found"));

    List<Portfolio> portfolios = portfolioRepository.findByUser(user);

    BigDecimal totalInvestment = BigDecimal.ZERO;

    // Calculate total investment
    for (Portfolio portfolio : portfolios) {

        List<Stock> stocks = stockRepository.findByPortfolio(portfolio);

        for (Stock stock : stocks) {

            BigDecimal investment = stock.getAverageBuyPrice()
                    .multiply(BigDecimal.valueOf(stock.getQuantity()));

            totalInvestment = totalInvestment.add(investment);
        }
    }

    List<AssetAllocationResponse> response = new java.util.ArrayList<>();

    for (Portfolio portfolio : portfolios) {

        List<Stock> stocks = stockRepository.findByPortfolio(portfolio);

        for (Stock stock : stocks) {

            BigDecimal investment = stock.getAverageBuyPrice()
                    .multiply(BigDecimal.valueOf(stock.getQuantity()));

            Double allocation = totalInvestment.compareTo(BigDecimal.ZERO) == 0
                    ? 0.0
                    : investment.multiply(BigDecimal.valueOf(100))
                            .divide(totalInvestment, 2, RoundingMode.HALF_UP)
                            .doubleValue();

            response.add(
                    AssetAllocationResponse.builder()
                            .symbol(stock.getSymbol())
                            .quantity(stock.getQuantity())
                            .investment(investment)
                            .allocationPercentage(allocation)
                            .build()
            );
        }
    }

    return response;
}
@Override
public TopPerformingStockResponse getTopPerformingStock(String email) {

    User user = userService.findByEmail(email)
            .orElseThrow(() ->
                    new NoSuchElementException("User not found"));

    List<Portfolio> portfolios = portfolioRepository.findByUser(user);

    Stock topStock = null;
    BigDecimal highestInvestment = BigDecimal.ZERO;

    for (Portfolio portfolio : portfolios) {

        List<Stock> stocks = stockRepository.findByPortfolio(portfolio);

        for (Stock stock : stocks) {

            BigDecimal investment = stock.getAverageBuyPrice()
                    .multiply(BigDecimal.valueOf(stock.getQuantity()));

            if (topStock == null || investment.compareTo(highestInvestment) > 0) {
                topStock = stock;
                highestInvestment = investment;
            }
        }
    }

    if (topStock == null) {
        throw new NoSuchElementException("No stocks found");
    }

    // Currently currentValue = investment.
    // Later this will use the live Market Data module.
    BigDecimal currentValue = highestInvestment;

    BigDecimal profit = currentValue.subtract(highestInvestment);

    return TopPerformingStockResponse.builder()
            .symbol(topStock.getSymbol())
            .companyName(topStock.getCompanyName())
            .investment(highestInvestment)
            .currentValue(currentValue)
            .profit(profit)
            .build();
}
@Override
public DashboardResponse getDashboard(String email) {

    User user = userService.findByEmail(email)
            .orElseThrow(() ->
                    new NoSuchElementException("User not found"));

    List<Portfolio> portfolios = portfolioRepository.findByUser(user);

    int totalPortfolios = portfolios.size();
    int totalStocks = 0;

    BigDecimal portfolioValue = BigDecimal.ZERO;

    for (Portfolio portfolio : portfolios) {

        List<Stock> stocks = stockRepository.findByPortfolio(portfolio);

        totalStocks += stocks.size();

        for (Stock stock : stocks) {

            portfolioValue = portfolioValue.add(
                    stock.getAverageBuyPrice()
                            .multiply(BigDecimal.valueOf(stock.getQuantity()))
            );
        }
    }

    int totalTransactions = transactionRepository.findAll().size();

    int totalWatchlists = watchlistRepository.findByUser(user).size();

    return DashboardResponse.builder()
            .totalPortfolios(totalPortfolios)
            .totalStocks(totalStocks)
            .totalTransactions(totalTransactions)
            .totalWatchlists(totalWatchlists)
            .portfolioValue(portfolioValue)
            .totalProfit(BigDecimal.ZERO)
            .build();
}
}