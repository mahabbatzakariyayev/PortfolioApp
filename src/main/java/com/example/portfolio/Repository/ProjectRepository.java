package com.example.portfolio.Repository;

import com.example.portfolio.entity.ProjectEntity;
import com.example.portfolio.entity.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    List<ProjectEntity> findByPortfolio(PortfolioEntity portfolio); // ✅ Get all projects from a portfolio
}
