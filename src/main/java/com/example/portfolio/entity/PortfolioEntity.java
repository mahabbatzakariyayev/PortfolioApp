package com.example.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    private String title;
    private String description;
    private String githubLink;
    private String imageUrl;
    private String videoUrl;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int likes;

    @Version
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer version;

    // ✅ Bu portfolio-ya gələn commentlər
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "portfolio-comments")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<CommentEntity> comments = new ArrayList<>();
}
