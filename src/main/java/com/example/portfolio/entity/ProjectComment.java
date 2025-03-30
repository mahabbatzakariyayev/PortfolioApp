package com.example.portfolio.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Embeddable
public class ProjectComment {
    private String username;
    private String text;
    private LocalDateTime postedAt;
}