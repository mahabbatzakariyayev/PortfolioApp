package com.example.portfolio.Service;

import com.example.portfolio.Repository.PortfolioRepository;
import com.example.portfolio.entity.PortfolioEntity;
import com.example.portfolio.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public PortfolioEntity getPortfolioForUser(UserEntity user) {
        return portfolioRepository.findByUser(user);
    }
}
