package com.example.portfolio.Repository;

import com.example.portfolio.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // Find user by username (optional helper)
    UserEntity findByUsername(String username);

    // List of users following a specific user (reverse relation)
    @Query("SELECT u FROM UserEntity u JOIN u.following f WHERE f.id = :userId")

    List<UserEntity> findFollowersOfUser(Long userId);

    // List of users the given user is following
    @Query("SELECT u FROM UserEntity u JOIN u.following f WHERE f.id = :userId")

    List<UserEntity> findFollowingOfUser(Long userId);

    // Check if a user is following another user
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM UserEntity u JOIN u.following f WHERE u.id = :followerId AND f.id = :followedId")
    boolean isFollowing(Long followerId, Long followedId);
}
