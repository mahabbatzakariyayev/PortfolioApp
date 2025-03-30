package com.example.portfolio.Controller;

import com.example.portfolio.Service.CommentService;
import com.example.portfolio.entity.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // ✅ Post a comment on a project
    @PostMapping("/portfolio/{projectId}")
    public ResponseEntity<String> postComment(@PathVariable Long projectId,
                                              @RequestParam String text) {
        return ResponseEntity.ok(commentService.postComment(projectId, text));
    }

    // ✅ Get all comments for a specific project
    @GetMapping("/portfolio/{projectId}")
    public ResponseEntity<List<CommentEntity>> getComments(@PathVariable Long projectId) {
        return ResponseEntity.ok(commentService.getCommentsForProject(projectId));
    }
}
