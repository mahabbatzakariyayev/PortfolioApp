package com.example.portfolio.Service;

import com.example.portfolio.Repository.CommentRepository;
import com.example.portfolio.Repository.ProjectRepository;
import com.example.portfolio.Repository.UserRepository;
import com.example.portfolio.entity.CommentEntity;
import com.example.portfolio.entity.ProjectEntity;
import com.example.portfolio.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public CommentService(CommentRepository commentRepository,
                          UserRepository userRepository,
                          ProjectRepository projectRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    private UserEntity getCurrentUser() {
        return userRepository.findById(2L).orElseThrow(); // bob
    }

    public String postComment(Long projectId, String text) {
        ProjectEntity project = projectRepository.findById(projectId).orElseThrow();
        UserEntity user = getCurrentUser();

        CommentEntity comment = new CommentEntity();
        comment.setText(text);
        comment.setPostedAt(LocalDateTime.now());
        comment.setUser(user);
        comment.setProject(project);

        commentRepository.save(comment);
        return "✅ Comment posted.";
    }

    public List<CommentEntity> getCommentsForProject(Long projectId) {
        ProjectEntity project = projectRepository.findById(projectId).orElseThrow();
        return commentRepository.findByProject(project);
    }
}
