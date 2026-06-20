package com.midas.portfolio.repository;

import com.midas.portfolio.entity.Portfolio;
import com.midas.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;



public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    List<Portfolio> findByUser(User user);
    Optional<Portfolio> findByIdAndUser(Long id, User user);
    

}