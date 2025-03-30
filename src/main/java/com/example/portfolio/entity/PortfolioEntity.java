package com.example.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class PortfolioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore // Prevents portfolio from exposing user info in response
    private UserEntity user;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<ProjectEntity> projects = new ArrayList<>();
}
