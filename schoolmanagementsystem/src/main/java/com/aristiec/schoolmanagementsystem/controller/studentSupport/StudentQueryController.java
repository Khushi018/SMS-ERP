package com.aristiec.schoolmanagementsystem.controller.studentSupport;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.constant.enums.QueryCategory;
import com.aristiec.schoolmanagementsystem.constant.enums.QueryStatus;
import com.aristiec.schoolmanagementsystem.dto.studentSupport.ReplyRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.studentSupport.StudentQueryFilterResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.studentSupport.StudentQueryRequestDTO;
import com.aristiec.schoolmanagementsystem.dto.studentSupport.StudentQueryResponseDTO;
import com.aristiec.schoolmanagementsystem.service.studentSupport.StudentQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/support")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Student Query Management", description = "Manage student queries and responses")
public class StudentQueryController {

    @Autowired
    private StudentQueryService studentQueryService;

    @Operation(summary = "Submit a student query", description = "Allows students to submit queries with optional file attachments.")
    @PostMapping("/{studentId}")
    public ResponseEntity<StudentQueryResponseDTO> submitQuery(
            @PathVariable Long studentId,
            @ModelAttribute StudentQueryRequestDTO requestDTO) {
        StudentQueryResponseDTO response = studentQueryService.submitQuery(studentId, requestDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get queries by student ID", description = "Retrieves all queries submitted by a specific student.")
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentQueryResponseDTO>> getStudentQueries(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentQueryService.getQueriesByStudent(studentId));
    }

    @Operation(summary = "Get all queries", description = "Retrieves all student queries in the system.")
    @GetMapping
    public List<StudentQueryResponseDTO> getAllQueries() {
        return studentQueryService.getAllQueries();
    }

    @Operation(summary = "Reply to a student query", description = "Allows admin to reply to a student's query with a status update.")
    @PutMapping("/update-status/{queryId}")
    public ResponseEntity<StudentQueryResponseDTO> updateStatus(
            @PathVariable Long queryId,
            @RequestBody ReplyRequestDTO dto) {
        return ResponseEntity.ok(studentQueryService.replyToQuery(queryId, dto));
    }

    @Operation(summary = "Upload a file for a query", description = "Allows students to upload a file attachment for their query.")
    @GetMapping("/download-file/{queryId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long queryId) {
        byte[] fileData = studentQueryService.getQueryFile(queryId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"attachment\"")
                .body(fileData);
    }

    @Operation(summary = "Filter student queries", description = "Allows students to filter their queries by category and status.")
    @GetMapping("/student/{studentId}/filter")
    public ResponseEntity<List<StudentQueryFilterResponseDTO>> filterQueries(
            @PathVariable Long studentId,
            @RequestParam(required = false) QueryCategory category,
            @RequestParam(required = false) QueryStatus status) {
        return ResponseEntity.ok(studentQueryService.getStudentQueriesFiltered(studentId, category, status));
    }

    @Operation(summary = "Search student queries", description = "Allows students to search their queries by various parameters.")
    @GetMapping("/student/{studentId}/search")
    public ResponseEntity<List<StudentQueryFilterResponseDTO>> searchQueries(
            @PathVariable Long studentId,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) QueryCategory category,
            @RequestParam(required = false) QueryStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate submittedAt) {
        return ResponseEntity.ok(
                studentQueryService.searchStudentQueries(studentId, subject, category, status, submittedAt));
    }

    @Operation(summary = "Delete a student query", description = "Allows students to delete their submitted queries.")
    @DeleteMapping("/{queryId}")
    public ResponseEntity<Void> deleteQuery(@PathVariable Long queryId) {
        studentQueryService.deleteQuery(queryId);
        return ResponseEntity.noContent().build(); 
    }

}
