package com.example.portfolio.Repository;

import com.example.portfolio.entity.PortfolioEntity;
import com.example.portfolio.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Long> {

    PortfolioEntity findByUser(UserEntity user); // ✅ Find portfolio by user
}
