package com.aristiec.schoolmanagementsystem.controller.admin.details;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.dto.CourseDTO;
import com.aristiec.schoolmanagementsystem.service.details.CourseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/course")
@Tag(name = "Courses", description = "Manage courses offered in the institution")
@SecurityRequirement(name = "bearerAuth") 
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping(consumes = { "application/json", "application/json;charset=UTF-8" })
    @Operation(summary = "Create new course", description = "Adds a new course to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Course created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid course data", content = @Content)
    })
    public ResponseEntity<CourseDTO> createCourse(
            @RequestBody @Parameter(description = "Course details") CourseDTO courseDTO) {
        CourseDTO createdCourse = courseService.addCourse(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @GetMapping
    @Operation(summary = "Get all courses", description = "Fetches all available courses")
    @ApiResponse(responseCode = "200", description = "List of courses retrieved")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<CourseDTO> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by ID", description = "Fetches course details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course found"),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content)
    })
    public ResponseEntity<CourseDTO> getCourseById(
            @PathVariable @Parameter(description = "ID of the course") Long id) {
        CourseDTO course = courseService.getCourseById(id);
        return course != null ? ResponseEntity.ok(course) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update course", description = "Updates course details by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course updated"),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content)
    })
    public ResponseEntity<CourseDTO> updateCourse(
            @PathVariable @Parameter(description = "ID of the course to update") Long id,
            @RequestBody @Parameter(description = "Updated course details") CourseDTO courseDTO) {
        CourseDTO updatedCourse = courseService.updateCourse(id, courseDTO);
        return updatedCourse != null ? ResponseEntity.ok(updatedCourse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete course", description = "Deletes a course by its ID")
    public ResponseEntity<Void> deleteCourse(
            @PathVariable @Parameter(description = "ID of the course to delete") Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

}
