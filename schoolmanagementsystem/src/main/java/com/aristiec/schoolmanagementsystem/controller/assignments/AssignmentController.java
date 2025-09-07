package com.aristiec.schoolmanagementsystem.controller.assignments;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.dto.AssignmentDTO;
import com.aristiec.schoolmanagementsystem.dto.OverallAttendanceDTO;
import com.aristiec.schoolmanagementsystem.dto.assignment.AssignmentRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.assignment.AssignmentSubmissionStatsDTO;
import com.aristiec.schoolmanagementsystem.dto.assignment.AssignmentSummaryDTO;
import com.aristiec.schoolmanagementsystem.dto.subjectAssignment.AssiDTO;
import com.aristiec.schoolmanagementsystem.dto.subjectAssignment.SubjectAssiDTO;
import com.aristiec.schoolmanagementsystem.modal.assignment.Assignment;
import com.aristiec.schoolmanagementsystem.service.assignment.AssignmentService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/assignments")
@SecurityRequirement(name = "bearerAuth")
public class AssignmentController {
    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("/add")
    public ResponseEntity<AssiDTO> createAssignment(@RequestBody AssignmentRequestDTO requestDTO) {
        AssiDTO responseDTO = assignmentService.createAssignment(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssiDTO> getAssignmentById(@PathVariable Long id) {
        AssiDTO assignment = assignmentService.getAssignmentById(id);
        return ResponseEntity.ok(assignment);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<AssiDTO>> getAllAssignments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AssiDTO> assignments = assignmentService.getAllAssignments(pageable);
        return ResponseEntity.ok(assignments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssiDTO> updateAssignment(
            @PathVariable Long id,
            @RequestBody AssignmentRequestDTO requestDTO) {

        AssiDTO updatedAssignment = assignmentService.updateAssignment(id, requestDTO);
        return ResponseEntity.ok(updatedAssignment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/subject-wise")
    public ResponseEntity<List<SubjectAssiDTO>> getAssignmentsBySubject() {
        return ResponseEntity.ok(assignmentService.getAllAssignmentsSubjectWise());
    }

    @GetMapping("/subject/name/{subjectName}")
    public ResponseEntity<List<AssiDTO>> getAssignmentsBySubjectName(@PathVariable String subjectName) {
        List<AssiDTO> assignments = assignmentService.getAssignmentsBySubjectName(subjectName);
        return ResponseEntity.ok(assignments);
    }

    // This is for faculty-admin
    @PatchMapping("/{assignmentId}/attempted")
    public ResponseEntity<String> markAttempted(@PathVariable Long assignmentId,
            @RequestParam boolean attempted) {
        AssignmentDTO updated = assignmentService.markAttempted(assignmentId, attempted);
        return ResponseEntity.ok("Attempted updated successfully");
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<AssiDTO>> getOverdueAssignments() {
        List<AssiDTO> overdueAssignments = assignmentService.getOverdueAssignments();
        return ResponseEntity.ok(overdueAssignments);
    }

    @GetMapping("/students/{studentId}/subjects/{subjectId}")
    public ResponseEntity<List<AssiDTO>> getAssignmentsBySubjectAndStudent(
            @PathVariable Long studentId,
            @PathVariable Long subjectId) {
        List<AssiDTO> list = assignmentService.getAssignmentsBySubjectAndStudent(subjectId, studentId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/students/{studentId}/subjects/{subjectId}/filter")
    public ResponseEntity<List<AssiDTO>> getAssignmentsByAttemptStatus(
            @PathVariable Long studentId,
            @PathVariable Long subjectId,
            @RequestParam boolean attempted) {

        List<AssiDTO> list = assignmentService.getAssignmentsBySubjectAndAttemptStatus(subjectId, studentId, attempted);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/students/{studentId}/completed")
    public ResponseEntity<List<AssiDTO>> getCompletedAssignments(
            @PathVariable Long studentId) {
        List<AssiDTO> list = assignmentService.getAllCompletedAssignmentsByStudent(studentId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/students/{studentId}/incomplete")
    public ResponseEntity<List<AssiDTO>> getIncompleteAssignments(
            @PathVariable Long studentId) {
        List<AssiDTO> list = assignmentService.getAllIncompleteAssignmentsByStudent(studentId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/students/{studentId}/overdue")
    public ResponseEntity<List<AssiDTO>> getOverdueAssignments(
            @PathVariable Long studentId) {
        List<AssiDTO> list = assignmentService.getOverdueAssignmentsByStudent(studentId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/summary")
    public ResponseEntity<AssignmentSummaryDTO> getOverallSummary() {
        AssignmentSummaryDTO summary = assignmentService.getOverallAssignmentSummary();
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/student/{studentId}/assignment-submission-stats")
    public ResponseEntity<AssignmentSubmissionStatsDTO> getSubmissionStats(@PathVariable Long studentId) {
        return ResponseEntity.ok(assignmentService.getSubmissionStats(studentId));
    }

    @GetMapping("/by-due-date")
    public ResponseEntity<List<AssiDTO>> getAssignmentByDueDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate) {

        List<AssiDTO> dtos = assignmentService.getAssignmentsByDueDate(dueDate);

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/by-course-sem")
    public ResponseEntity<Page<AssiDTO>> getByCourseAndSem(
            @RequestParam Long courseId,
            @RequestParam Integer sem,
            @RequestParam int page,
            @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ResponseEntity.ok(assignmentService.getByCourseIdAndSem(courseId, sem, pageable));
    }

}
