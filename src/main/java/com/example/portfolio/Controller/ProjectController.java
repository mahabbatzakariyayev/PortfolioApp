package com.example.portfolio.Controller;

import com.example.portfolio.Service.ProjectService;
import com.example.portfolio.entity.ProjectEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // ✅ Get all projects
    @GetMapping
    public ResponseEntity<List<ProjectEntity>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    // ✅ Get a single project by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProjectEntity> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    // ✅ Create a new project (optional for admin usage)
    @PostMapping
    public ResponseEntity<ProjectEntity> createProject(@RequestBody ProjectEntity project) {
        return ResponseEntity.ok(projectService.createProject(project));
    }

    // ✅ Like a project (simulating user ID 2)
    @PostMapping("/{id}/like")
    public ResponseEntity<String> likeProjects(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.likeProjects(id));
    }

    @PostMapping("/{id}/media")
    public ResponseEntity<String> attachMedia(@PathVariable Long id,
                                              @RequestParam(required = false) String imageUrl,
                                              @RequestParam(required = false) String videoUrl) {
        return ResponseEntity.ok(projectService.attachMediaToProject(id, imageUrl, videoUrl));
    }
}
