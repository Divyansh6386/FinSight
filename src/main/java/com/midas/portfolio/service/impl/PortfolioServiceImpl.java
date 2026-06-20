package com.midas.portfolio.service.impl;

import com.midas.portfolio.dto.request.CreatePortfolioRequest;
import com.midas.portfolio.dto.request.UpdatePortfolioRequest;
import com.midas.portfolio.dto.response.PortfolioResponse;
import com.midas.portfolio.entity.Portfolio;
import com.midas.portfolio.repository.PortfolioRepository;
import com.midas.portfolio.service.PortfolioService;
import com.midas.user.entity.User;
import com.midas.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final UserService userService;

    @Override
    public PortfolioResponse createPortfolio(String email, CreatePortfolioRequest request) {

        User user = userService.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Portfolio portfolio = Portfolio.builder()
                .name(request.getName())
                .description(request.getDescription())
                .totalValue(BigDecimal.ZERO)
                .user(user)
                .build();

        Portfolio saved = portfolioRepository.save(portfolio);

        return map(saved);
    }

    @Override
    public List<PortfolioResponse> getMyPortfolios(String email) {

        User user = userService.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        return portfolioRepository.findByUser(user)
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public PortfolioResponse getPortfolioById(String email, Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PortfolioResponse updatePortfolio(String email, Long id, UpdatePortfolioRequest request) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deletePortfolio(String email, Long id) {
        throw new UnsupportedOperationException();
    }

    private PortfolioResponse map(Portfolio portfolio) {

        return PortfolioResponse.builder()
                .id(portfolio.getId())
                .name(portfolio.getName())
                .description(portfolio.getDescription())
                .totalValue(portfolio.getTotalValue())
                .build();
    }
}