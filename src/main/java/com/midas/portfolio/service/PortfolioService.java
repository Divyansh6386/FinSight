package com.midas.portfolio.service;

import com.midas.portfolio.dto.request.CreatePortfolioRequest;
import com.midas.portfolio.dto.request.UpdatePortfolioRequest;
import com.midas.portfolio.dto.response.PortfolioResponse;

import java.util.List;

public interface PortfolioService {

    PortfolioResponse createPortfolio(
            String email,
            CreatePortfolioRequest request
    );

    List<PortfolioResponse> getMyPortfolios(String email);

    PortfolioResponse getPortfolioById(
            String email,
            Long id
    );

    PortfolioResponse updatePortfolio(
            String email,
            Long id,
            UpdatePortfolioRequest request
    );

    void deletePortfolio(
            String email,
            Long id
    );
}