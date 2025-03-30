package com.example.portfolio.Controller;

import com.example.portfolio.Service.ProjectService;
import com.example.portfolio.entity.ProjectEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // ✅ Add a new project to the current user's portfolio
    @PostMapping
    public ResponseEntity<ProjectEntity> addProject(@RequestBody ProjectEntity project) {
        return ResponseEntity.ok(projectService.addProjectToMyPortfolio(project));
    }

    // ✅ Get a single project by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProjectEntity> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    // ✅ Get all projects of the current user
    @GetMapping("/my")
    public ResponseEntity<List<ProjectEntity>> getMyProjects() {
        return ResponseEntity.ok(projectService.getMyProjects());
    }

    // ✅ Like a project
    @PostMapping("/{id}/like")
    public ResponseEntity<String> likeProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.likeProject(id));
    }

    // ✅ Attach media to a project
    @PostMapping("/{id}/media")
    public ResponseEntity<String> attachMedia(@PathVariable Long id,
                                              @RequestParam(required = false) String imageUrl,
                                              @RequestParam(required = false) String videoUrl) {
        return ResponseEntity.ok(projectService.attachMediaToProject(id, imageUrl, videoUrl));
    }

    // ✅ Add comment to a project
    @PostMapping("/{id}/comment")
    public ResponseEntity<String> comment(@PathVariable Long id, @RequestParam String text) {
        return ResponseEntity.ok(projectService.addCommentToProject(id, text));
    }

    @PostMapping("/{id}/save")
    public ResponseEntity<String> saveProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.saveProject(id));
    }

    @GetMapping("/saved")
    public ResponseEntity<List<ProjectEntity>> getSavedProjects() {
        return ResponseEntity.ok(projectService.getSavedProjects());
    }

}
