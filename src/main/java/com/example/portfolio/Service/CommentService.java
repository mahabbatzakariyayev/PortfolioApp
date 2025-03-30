package com.example.portfolio.Service;

import com.example.portfolio.Repository.CommentRepository;
import com.example.portfolio.Repository.PortfolioRepository;
import com.example.portfolio.Repository.UserRepository;
import com.example.portfolio.entity.CommentEntity;
import com.example.portfolio.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;

    public CommentService(CommentRepository commentRepository,
                          UserRepository userRepository,
                          PortfolioRepository portfolioRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.portfolioRepository = portfolioRepository;
    }

    private UserEntity getCurrentUser() {
        return userRepository.findById(2L).orElseThrow(); // bob
    }

    public String postComment(Long portfolioId, String text) {
        com.example.portfolio.entity.PortfolioEntity portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        UserEntity user = getCurrentUser();

        CommentEntity comment = new CommentEntity();
        comment.setText(text);
        comment.setPostedAt(LocalDateTime.now());
        comment.setUser(user);
        comment.setPortfolio(portfolio);

        commentRepository.save(comment);
        return "✅ Comment posted.";
    }

    public List<CommentEntity> getCommentsForPortfolio(Long portfolioId) {
        com.example.portfolio.entity.PortfolioEntity portfolio = portfolioRepository.findById(portfolioId).orElseThrow();
        return commentRepository.findByPortfolio(portfolio);
    }
}
