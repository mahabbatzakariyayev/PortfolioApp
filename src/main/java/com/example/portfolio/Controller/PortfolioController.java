package com.example.portfolio.Controller;

import com.example.portfolio.Service.PortfolioService;
import com.example.portfolio.Service.UserService;
import com.example.portfolio.entity.PortfolioEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final UserService userService;

    public PortfolioController(PortfolioService portfolioService, UserService userService) {
        this.portfolioService = portfolioService;
        this.userService = userService;
    }

    // ✅ Get the current user's portfolio
    @GetMapping("/me")
    public ResponseEntity<PortfolioEntity> getMyPortfolio() {
        return ResponseEntity.ok(portfolioService.getPortfolioForUser(userService.getCurrentUser()));
    }
}
