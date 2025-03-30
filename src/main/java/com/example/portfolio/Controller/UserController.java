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

    @PostMapping("/{id}/follow")
    public ResponseEntity<String> follow(@PathVariable Long id) {
        return ResponseEntity.ok(userService.followUser(id));
    }

    @PostMapping("/{id}/unfollow")
    public ResponseEntity<String> unfollow(@PathVariable Long id) {
        return ResponseEntity.ok(userService.unfollowUser(id));
    }

    @GetMapping("/following")
    public ResponseEntity<List<UserEntity>> getFollowing() {
        return ResponseEntity.ok(userService.getFollowing());
    }

    @GetMapping("/followers")
    public ResponseEntity<List<UserEntity>> getFollowers() {
        return ResponseEntity.ok(userService.getFollowers());
    }

    @GetMapping("/check-following/{id}")
    public ResponseEntity<Boolean> isFollowing(@PathVariable Long id) {
        return ResponseEntity.ok(userService.isFollowing(id));
    }
}
