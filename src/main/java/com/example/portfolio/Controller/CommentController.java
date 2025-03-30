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
    @PostMapping("/portfolio/{portfolioId}")
    public ResponseEntity<String> postComment(@PathVariable Long portfolioId,
                                              @RequestParam String text) {
        return ResponseEntity.ok(commentService.postComment(portfolioId, text));
    }

    // ✅ Get all comments for a specific project
    @GetMapping("/portfolio/{portfolioId}")
    public ResponseEntity<List<CommentEntity>> getComments(@PathVariable Long portfolioId) {
        return ResponseEntity.ok(commentService.getCommentsForPortfolio(portfolioId));
    }
}
