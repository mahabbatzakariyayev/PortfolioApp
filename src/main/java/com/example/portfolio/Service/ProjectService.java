package com.example.portfolio.Service;

import com.example.portfolio.Repository.ProjectRepository;
import com.example.portfolio.Repository.UserRepository;
import com.example.portfolio.entity.ProjectEntity;
import com.example.portfolio.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    // Simulated logged-in user (bob = ID 2)
    public UserEntity getCurrentUser() {
        return userRepository.findById(2L).orElseThrow();
    }

    // Get all projects
    public List<ProjectEntity> getAllProjects() {
        return projectRepository.findAll();
    }

    // Get one project by ID
    public ProjectEntity getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow();
    }

    // Create a new project
    public ProjectEntity createProject(ProjectEntity project) {
        project.setId(null); // Ensure it's treated as a new entity
        return projectRepository.save(project);
    }

    // Like a project
    public String likeProjects(Long projectId) {
        UserEntity user = getCurrentUser();
        ProjectEntity project = projectRepository.findById(projectId).orElseThrow();

        // Avoid duplicate likes (optional)
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
}
