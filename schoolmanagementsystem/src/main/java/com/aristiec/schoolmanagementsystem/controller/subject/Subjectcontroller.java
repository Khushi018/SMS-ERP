package com.aristiec.schoolmanagementsystem.controller.subject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.constant.enums.SectionEnum;
import com.aristiec.schoolmanagementsystem.dto.SubjectDTO;
import com.aristiec.schoolmanagementsystem.dto.SubjectMinimalDTO;
import com.aristiec.schoolmanagementsystem.dto.faculty.FacultySubjectDTO;
import com.aristiec.schoolmanagementsystem.service.subject.SubjectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

/**
 * Controller for managing Subject-related operations in the School Management System.
 * Provides endpoints for CRUD operations, file upload/download, and subject queries by student, course, and semester.
 *
 * <p>
 * Endpoints:
 * <ul>
 *   <li>Create a new subject</li>
 *   <li>Retrieve subject by ID</li>
 *   <li>Retrieve all subjects</li>
 *   <li>Update subject details</li>
 *   <li>Delete a subject</li>
 *   <li>Get subjects by student, course, and semester</li>
 *   <li>Get faculties by subject and section</li>
 *   <li>Upload subject-related files</li>
 *   <li>Download subject-related files</li>
 * </ul>
 * </p>
 *
 * @Operation(summary = "Subject Management Controller",
 *            description = "Handles CRUD operations, file management, and subject queries for students, courses, and semesters.")
 */
@RestController
@RequestMapping("/api/v1/subjects")

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Subject Management", description = "Manage subjects and their related details")
public class Subjectcontroller {

    @Autowired
    private SubjectService subjectService;

    @PostMapping
    public ResponseEntity<SubjectDTO> create(@RequestBody SubjectDTO subject) {
        SubjectDTO savedSubject = subjectService.createSubject(subject);
        return ResponseEntity.ok(savedSubject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(subjectService.getSubjectById(id));
    }

    @GetMapping
    public ResponseEntity<List<SubjectDTO>> getAll() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectDTO> update(@PathVariable Long id, @RequestBody SubjectDTO subject) {
        return ResponseEntity.ok(subjectService.updateSubject(id, subject));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }
    
@Operation(summary = " Get all subjects of student by student id, course id, semester ", description = "Get subjects by student course and semester")
    @GetMapping("/{studentId}/subjects")
    public ResponseEntity<List<SubjectMinimalDTO>> getSubjectsByCourseAndSemester(
            @PathVariable Long studentId,
            @RequestParam Long courseId,
            @RequestParam Integer semester) {

        List<SubjectMinimalDTO> subjects = subjectService.getSubjectsByStudentCourseAndSemester(studentId, courseId,
                semester);
        return ResponseEntity.ok(subjects);
    }

@Operation(summary = "Get faculties by subject ID and section", description = "Retrieve faculties associated with a subject based on the section")
    @GetMapping("/faculties-by-subject-section")
    public ResponseEntity<List<FacultySubjectDTO>> getFacultiesBySubjectIdAndSection(@RequestParam Long subjectId,
            @RequestParam SectionEnum section) {
        return ResponseEntity.ok(subjectService.getFacultiesBySubjectIdAndSection(subjectId, section));

    }

    @Operation(summary = "Upload subject file, Syllabus", description = "Uploads a file associated with a subject")
    @PostMapping("/{subjectId}/upload")
    public ResponseEntity<String> uploadFile(@PathVariable Long subjectId, @RequestParam("file") MultipartFile file) {
        subjectService.uploadSubjectFile(subjectId, file);
        return ResponseEntity.ok("File uploaded successfully.");
    }

    // Download endpoint
    @Operation(summary = "Download subject file, Syllabus", description = "Downloads a file associated with a subject")
    @GetMapping("/{subjectId}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long subjectId,
            @RequestParam("filename") String filename) {
        Resource file = subjectService.downloadSubjectFile(subjectId, filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(file);
    }

}
