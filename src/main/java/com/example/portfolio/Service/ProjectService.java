package com.example.portfolio.Service;

import com.example.portfolio.Repository.PortfolioRepository;
import com.example.portfolio.Repository.ProjectRepository;
import com.example.portfolio.Repository.UserRepository;
import com.example.portfolio.entity.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectService {

    private final PortfolioRepository portfolioRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(PortfolioRepository portfolioRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.portfolioRepository = portfolioRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public UserEntity getCurrentUser() {
        return userRepository.findById(2L).orElseThrow();
    }

    public ProjectEntity addProjectToMyPortfolio(ProjectEntity project) {
        UserEntity user = getCurrentUser();
        PortfolioEntity portfolio = user.getPortfolio();

        if (portfolio == null) {
            portfolio = new PortfolioEntity();
            portfolio.setUser(user);
            portfolio = portfolioRepository.save(portfolio);
            user.setPortfolio(portfolio);
            userRepository.save(user);
        }

        project.setPortfolio(portfolio);
        return projectRepository.save(project);
    }

    public ProjectEntity getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public List<ProjectEntity> getMyProjects() {
        UserEntity user = getCurrentUser();
        PortfolioEntity portfolio = user.getPortfolio();
        if (portfolio == null) return List.of();
        return projectRepository.findByPortfolio(portfolio);
    }

    public String likeProject(Long projectId) {
        UserEntity user = getCurrentUser();
        ProjectEntity project = projectRepository.findById(projectId).orElseThrow();

        if (!user.getLikedProjects().contains(project)) {
            user.getLikedProjects().add(project);
            project.setLikes(project.getLikes() + 1);
            userRepository.save(user);
            projectRepository.save(project);
            return "✅ Project liked!";
        }
        return "⚠️ You already liked this project.";
    }

    public String attachMediaToProject(Long projectId, String imageUrl, String videoUrl) {
        ProjectEntity project = projectRepository.findById(projectId).orElseThrow();

        if (imageUrl != null && !imageUrl.isEmpty()) {
            project.setImageUrl(imageUrl);
        }
        if (videoUrl != null && !videoUrl.isEmpty()) {
            project.setVideoUrl(videoUrl);
        }

        projectRepository.save(project);
        return "✅ Media attached to project!";
    }

    public String addCommentToProject(Long projectId, String text) {
        UserEntity user = getCurrentUser();
        ProjectEntity project = projectRepository.findById(projectId).orElseThrow();

        ProjectComment comment = new ProjectComment();
        comment.setUsername(user.getUsername());
        comment.setText(text);
        comment.setPostedAt(LocalDateTime.now());

        project.getComments().add(comment);
        projectRepository.save(project);

        return "✅ Comment added to project!";
    }

    public String saveProject(Long projectId) {
        UserEntity user = getCurrentUser();
        ProjectEntity project = projectRepository.findById(projectId).orElseThrow();

        if (!user.getSavedProjects().contains(project)) {
            user.getSavedProjects().add(project);
            userRepository.save(user);
            return "✅ Project saved!";
        }
        return "⚠️ You already saved this project.";
    }

    public List<ProjectEntity> getSavedProjects() {
        return getCurrentUser().getSavedProjects();
    }

}