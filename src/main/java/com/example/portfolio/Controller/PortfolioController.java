package com.example.portfolio.Controller;

import com.example.portfolio.Service.PortfolioService;
import com.example.portfolio.entity.PortfolioEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    // ✅ Get all portfolios
    @GetMapping
    public ResponseEntity<List<PortfolioEntity>> getAllPortfolios() {
        return ResponseEntity.ok(portfolioService.getAllPortfolios());
    }

    // ✅ Get a single portfolio by ID
    @GetMapping("/{id}")
    public ResponseEntity<PortfolioEntity> getPortfolioById(@PathVariable Long id) {
        return ResponseEntity.ok(portfolioService.getPortfolioById(id));
    }

    // ✅ Create a new portfolio (optional for admin usage)
    @PostMapping
    public ResponseEntity<PortfolioEntity> createPortfolio(@RequestBody PortfolioEntity portfolio) {
        return ResponseEntity.ok(portfolioService.createPortfolio(portfolio));
    }

    // ✅ Like a portfolio (simulating user ID 2)
    @PostMapping("/{id}/like")
    public ResponseEntity<String> likePortfolios(@PathVariable Long id) {
        return ResponseEntity.ok(portfolioService.likePortfolios(id));
    }

    @PostMapping("/{id}/media")
    public ResponseEntity<String> attachMedia(@PathVariable Long id,
                                              @RequestParam(required = false) String imageUrl,
                                              @RequestParam(required = false) String videoUrl) {
        return ResponseEntity.ok(portfolioService.attachMediaToPortfolio(id, imageUrl, videoUrl));
    }
}