package com.example.portfolio.Repository;


import com.example.portfolio.entity.CommentEntity;
import com.example.portfolio.entity.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByPortfolio(PortfolioEntity portfolio); // get all comments for a project
}
