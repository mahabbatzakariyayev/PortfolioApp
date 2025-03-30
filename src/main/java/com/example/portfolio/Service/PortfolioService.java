package com.example.portfolio.Service;

import com.example.portfolio.Repository.PortfolioRepository;
import com.example.portfolio.Repository.UserRepository;
import com.example.portfolio.entity.PortfolioEntity;
import com.example.portfolio.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;

    public PortfolioService(PortfolioRepository portfolioRepository, UserRepository userRepository) {
        this.portfolioRepository = portfolioRepository;
        this.userRepository = userRepository;
    }

    // Simulated logged-in user (bob = ID 2)
    public UserEntity getCurrentUser() {
        return userRepository.findById(2L).orElseThrow();
    }

    // Get all portfolios
    public List<PortfolioEntity> getAllPortfolios() {
        return portfolioRepository.findAll();
    }

    // Get one portfolio by ID
    public PortfolioEntity getPortfolioById(Long id) {
        return portfolioRepository.findById(id).orElseThrow();
    }

    // Create a new portfolio
    public PortfolioEntity createPortfolio(PortfolioEntity portfolio) {
        portfolio.setId(null); // Ensure it's treated as a new entity
        return portfolioRepository.save(portfolio);
    }

    // Like a portfolio
    public String likePortfolios(Long portfolioId) {
        UserEntity user = getCurrentUser();
        PortfolioEntity portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        // Avoid duplicate likes (optional)
        if (!user.getLikedPortfolios().contains(portfolio)) {
            user.getLikedPortfolios().add(portfolio);
            portfolio.setLikes(portfolio.getLikes() + 1);
            userRepository.save(user);
            portfolioRepository.save(portfolio);
            return "✅ Portfolio liked!";
        }

        return "⚠️ You already liked this portfolio.";
    }

    public String attachMediaToPortfolio(Long portfolioId, String imageUrl, String videoUrl) {
        PortfolioEntity portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        if (imageUrl != null && !imageUrl.isEmpty()) {
            portfolio.setImageUrl(imageUrl);
        }

        if (videoUrl != null && !videoUrl.isEmpty()) {
            portfolio.setVideoUrl(videoUrl);
        }

        portfolioRepository.save(portfolio);
        return "✅ Media attached to portfolio!";
    }
}