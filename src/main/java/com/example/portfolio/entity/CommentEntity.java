package com.example.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private LocalDateTime postedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user-comments")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    @JsonBackReference(value = "portfolio-comments")
    private PortfolioEntity portfolio;

}
