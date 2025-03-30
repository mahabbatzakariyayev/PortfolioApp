package com.example.portfolio.Repository;

import com.example.portfolio.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // Kullanıcıyı kullanıcı adına göre bul
    UserEntity findByUsername(String username);

    // Bir kullanıcı kimleri takip ediyor?
    @Query("SELECT f FROM UserEntity u JOIN u.following f WHERE u.id = :userId")
    List<UserEntity> findFollowingOfUser(Long userId);

    // Kimler bu kullanıcıyı takip ediyor?
    @Query("SELECT u FROM UserEntity u JOIN u.following f WHERE f.id = :userId")
    List<UserEntity> findFollowersOfUser(Long userId);

    // Belirli bir kullanıcı başka bir kullanıcıyı takip ediyor mu?
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM UserEntity u JOIN u.following f " +
            "WHERE u.id = :followerId AND f.id = :followedId")
    boolean isFollowing(Long followerId, Long followedId);
}
