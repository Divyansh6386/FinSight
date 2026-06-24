package com.midas.transaction.service.impl;

import com.midas.portfolio.entity.Portfolio;
import com.midas.portfolio.repository.PortfolioRepository;
import com.midas.stock.entity.Stock;
import com.midas.stock.repository.StockRepository;
import com.midas.transaction.dto.request.BuyStockRequest;
import com.midas.transaction.dto.request.SellStockRequest;
import com.midas.transaction.dto.response.TransactionResponse;
import com.midas.transaction.entity.Transaction;
import com.midas.transaction.entity.TransactionType;
import com.midas.transaction.repository.TransactionRepository;
import com.midas.transaction.service.TransactionService;
import com.midas.user.entity.User;
import com.midas.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final StockRepository stockRepository;
    private final PortfolioRepository portfolioRepository;
    private final UserService userService;

    @Override
    public TransactionResponse buyStock(String email, BuyStockRequest request) {

        User user = userService.findByEmail(email)
                .orElseThrow(() ->
                        new NoSuchElementException("User not found"));

        // BuyStockRequest identifies a stock by portfolioId + symbol (not stockId)
        Portfolio portfolio = portfolioRepository
                .findByIdAndUser(request.getPortfolioId(), user)
                .orElseThrow(() ->
                        new NoSuchElementException("Portfolio not found"));

        String symbol = request.getSymbol().toUpperCase();

        // Find existing stock in this portfolio or create a new one
        Stock stock = stockRepository
                .findByPortfolioAndSymbol(portfolio, symbol)
                .orElseGet(() -> Stock.builder()
                        .symbol(symbol)
                        .companyName(request.getCompanyName())
                        .quantity(0)
                        .averageBuyPrice(BigDecimal.ZERO)
                        .portfolio(portfolio)
                        .build());

        Transaction transaction = Transaction.builder()
                .stock(stock)
                .type(TransactionType.BUY)
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .build();

        int oldQty = stock.getQuantity();
        int newQty = oldQty + request.getQuantity();

        BigDecimal oldInvestment =
                stock.getAverageBuyPrice()
                        .multiply(BigDecimal.valueOf(oldQty));

        BigDecimal newInvestment =
                request.getPrice()
                        .multiply(BigDecimal.valueOf(request.getQuantity()));

        BigDecimal newAverage =
                oldInvestment.add(newInvestment)
                        .divide(BigDecimal.valueOf(newQty), 2, RoundingMode.HALF_UP);

        stock.setQuantity(newQty);
        stock.setAverageBuyPrice(newAverage);

        Stock savedStock = stockRepository.save(stock);

        // Set stock on transaction after stock is persisted (ensures stock_id is set)
        transaction = Transaction.builder()
                .stock(savedStock)
                .type(TransactionType.BUY)
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionResponse.builder()
                .id(savedTransaction.getId())
                .symbol(savedStock.getSymbol())
                .type(savedTransaction.getType())
                .quantity(savedTransaction.getQuantity())
                .price(savedTransaction.getPrice())
                .createdAt(savedTransaction.getCreatedAt())
                .build();
    }

    @Override
    public TransactionResponse sellStock(String email, SellStockRequest request) {

        User user = userService.findByEmail(email)
                .orElseThrow(() ->
                        new NoSuchElementException("User not found"));

        Stock stock = stockRepository
        .findByIdWithPortfolioAndUser(request.getStockId())
        .orElseThrow(() ->
                new NoSuchElementException("Stock not found"));
        if (!stock.getPortfolio().getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Stock does not belong to this user");
        }

        if (stock.getQuantity() < request.getQuantity()) {
            throw new IllegalArgumentException("Insufficient stock quantity to sell");
        }

        Transaction transaction = Transaction.builder()
                .stock(stock)
                .type(TransactionType.SELL)
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .build();

        stock.setQuantity(stock.getQuantity() - request.getQuantity());
        stockRepository.save(stock);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionResponse.builder()
                .id(savedTransaction.getId())
                .symbol(stock.getSymbol())
                .type(savedTransaction.getType())
                .quantity(savedTransaction.getQuantity())
                .price(savedTransaction.getPrice())
                .createdAt(savedTransaction.getCreatedAt())
                .build();
    }

    @Override
    public List<TransactionResponse> getTransactions(String email, Long stockId) {

        User user = userService.findByEmail(email)
                .orElseThrow(() ->
                        new NoSuchElementException("User not found"));

        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() ->
                        new NoSuchElementException("Stock not found"));

        if (!stock.getPortfolio().getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Stock does not belong to this user");
        }

        return transactionRepository.findByStockOrderByCreatedAtDesc(stock)
                .stream()
                .map(t -> TransactionResponse.builder()
                        .id(t.getId())
                        .symbol(stock.getSymbol())
                        .type(t.getType())
                        .quantity(t.getQuantity())
                        .price(t.getPrice())
                        .createdAt(t.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}