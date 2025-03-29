package com.example.portfolio.Service;

import com.example.portfolio.Repository.UserRepository;
import com.example.portfolio.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Simulated logged-in user (bob = ID 2)
    public UserEntity getCurrentUser() {
        return userRepository.findById(2L).orElseThrow();
    }

        public String followUser(Long targetId) {
            UserEntity me = getCurrentUser();
            UserEntity target = userRepository.findById(targetId).orElseThrow();

            if (me.getId().equals(targetId)) return "❌ You can't follow yourself.";

            if (!userRepository.isFollowing(me.getId(), targetId)) {
                me.getFollowing().add(target);
                userRepository.save(me);
                return "✅ Followed " + target.getUsername();
            }
            return "⚠️ Already following " + target.getUsername();
        }

        public String unfollowUser(Long targetId) {
            UserEntity me = getCurrentUser();
            UserEntity target = userRepository.findById(targetId).orElseThrow();

            if (userRepository.isFollowing(me.getId(), targetId)) {
                me.getFollowing().remove(target);
                userRepository.save(me);
                return "✅ Unfollowed " + target.getUsername();
            }
            return "⚠️ You're not following " + target.getUsername();
        }

    public List<UserEntity> getFollowing() {
        return userRepository.findFollowingOfUser(getCurrentUser().getId());
    }

    public List<UserEntity> getFollowers() {
        return userRepository.findFollowersOfUser(getCurrentUser().getId());
    }

    public boolean isFollowing(Long userId) {
        return userRepository.isFollowing(getCurrentUser().getId(), userId);
    }
}
