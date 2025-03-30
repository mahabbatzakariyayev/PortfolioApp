package com.example.portfolio.Service;

import com.example.portfolio.Repository.UserRepository;
import com.example.portfolio.entity.PortfolioEntity;
import com.example.portfolio.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class PortfolioService {

    private final UserRepository userRepository;

    public PortfolioService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public PortfolioEntity getPortfolioForUser(UserEntity user) {
        return userRepository.findById(user.getId())
                .map(UserEntity::getPortfolio)
                .orElse(null);
    }
}
