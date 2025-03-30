package com.example.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    // ✅ Bu user kimləri izləyir
    @ManyToMany
    @JoinTable(
            name = "user_following",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    @JsonIgnore
    private List<UserEntity> following = new ArrayList<>();

    // ✅ Bu useri kimlər izləyir
    @ManyToMany(mappedBy = "following")
    @JsonIgnore
    private List<UserEntity> followers = new ArrayList<>();

    // ✅ Bu userin like etdiyi portfolio-lar
    @ManyToMany
    @JoinTable(
            name = "users_liked_project",
            joinColumns = @JoinColumn(name = "user_entity_id"),
            inverseJoinColumns = @JoinColumn(name = "liked_project_id")
    )
    private List<ProjectEntity> likedProjects = new ArrayList<>();

    // ✅ Bu userin commentləri
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "user-comments")
    private List<CommentEntity> comments = new ArrayList<>();
}