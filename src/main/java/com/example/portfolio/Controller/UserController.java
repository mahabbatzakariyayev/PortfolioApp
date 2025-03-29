package com.example.portfolio.Controller;

import com.example.portfolio.Service.UserService;
import com.example.portfolio.entity.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ Follow another user
    @PostMapping("/{id}/follow")
    public ResponseEntity<String> follow(@PathVariable Long id) {
        return ResponseEntity.ok(userService.followUser(id));
    }

    // ✅ Unfollow a user
    @PostMapping("/{id}/unfollow")
    public ResponseEntity<String> unfollow(@PathVariable Long id) {
        return ResponseEntity.ok(userService.unfollowUser(id));
    }

    // ✅ Get users the current user is following
    @GetMapping("/following")
    public ResponseEntity<List<UserEntity>> getFollowing() {
        return ResponseEntity.ok(userService.getFollowing());
    }

    // ✅ Get users who follow the current user
    @GetMapping("/followers")
    public ResponseEntity<List<UserEntity>> getFollowers() {
        return ResponseEntity.ok(userService.getFollowers());
    }

    // ✅ Check if you're following a specific user
    @GetMapping("/check-following/{id}")
    public ResponseEntity<Boolean> isFollowing(@PathVariable Long id) {
        return ResponseEntity.ok(userService.isFollowing(id));
    }
}
