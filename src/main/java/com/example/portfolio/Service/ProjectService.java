package com.example.portfolio.Service;

import com.example.portfolio.Repository.ProjectRepository;
import com.example.portfolio.Repository.UserRepository;
import com.example.portfolio.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final String uploadDir = "resources/media";

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        createUploadDirectory();
    }

    private void createUploadDirectory() {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    public UserEntity getCurrentUser() {
        return userRepository.findById(2L).orElseThrow();
    }

    public ProjectEntity addProjectToMyPortfolio(ProjectEntity project) {
        return addProjectToUserPortfolio(getCurrentUser().getId(), project);
    }

    public ProjectEntity addProjectToUserPortfolio(Long userId, ProjectEntity project) {
        UserEntity user = userRepository.findById(userId).orElseThrow();
        PortfolioEntity portfolio = user.getPortfolio();

        if (portfolio == null) {
            portfolio = new PortfolioEntity();
            portfolio.setUser(user);
            user.setPortfolio(portfolio);
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

    public List<ProjectEntity> getProjectsByUser(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow();
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

    public String uploadProjectImage(Long projectId, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to upload empty file");
            }

            ProjectEntity project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new RuntimeException("Project not found"));

            // Validate file type
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new RuntimeException("Only image files are allowed");
            }

            // Get original filename and clean it
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();

            // Validate extension
            if (!extension.matches("\\.(jpg|jpeg|png|gif)$")) {
                throw new RuntimeException("Only JPG, PNG and GIF files are allowed");
            }

            // Generate unique filename
            String newFilename = UUID.randomUUID().toString() + extension;

            // Save file
            Path filePath = Paths.get(uploadDir, newFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Update project with new image URL
            String imageUrl = "/media/" + newFilename;
            project.setImageUrl(imageUrl);
            projectRepository.save(project);

            return "✅ Image uploaded successfully!";
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image: " + e.getMessage());
        }
    }
}
