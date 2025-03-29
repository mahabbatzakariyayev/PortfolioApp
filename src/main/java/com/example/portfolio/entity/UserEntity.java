package com.example.portfolio.entity;

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

    // FOLLOWING RELATIONSHIP
    @ManyToMany
    @JoinTable(
            name = "user_following",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    private List<UserEntity> following = new ArrayList<>();

    // 👇 Required Getters and Setters
    public List<UserEntity> getFollowing() {
        return following;
    }

    public void setFollowing(List<UserEntity> following) {
        this.following = following;
    }

    // Other getters/setters...
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @ManyToMany
    private List<ProjectEntity> likedProjects = new ArrayList<>();


    // You can use Lombok to avoid writing boilerplate
}
