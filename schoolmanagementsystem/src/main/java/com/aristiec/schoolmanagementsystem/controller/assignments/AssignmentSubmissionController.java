package com.aristiec.schoolmanagementsystem.controller.assignments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aristiec.schoolmanagementsystem.dto.assignment.AssignmentSubmissionDTO;
import com.aristiec.schoolmanagementsystem.dto.assignment.OfflineSubmissionRequest;
import com.aristiec.schoolmanagementsystem.service.assignment.AssignmentSubmissionService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/submission")

@SecurityRequirement(name = "bearerAuth") 
public class AssignmentSubmissionController {
     @Autowired
    private AssignmentSubmissionService submissionService;

    //  Offline Submission
    @PostMapping("/submit-offline")
    public ResponseEntity<AssignmentSubmissionDTO> submitOffline(@RequestBody OfflineSubmissionRequest request) {
        AssignmentSubmissionDTO dto = submissionService.submitOffline(request.getAssignmentId(), request.getStudentId());
        return ResponseEntity.ok(dto);
    }

    //  Online Submission
    @PostMapping("/submit-online")
    public ResponseEntity<AssignmentSubmissionDTO> submitOnline(@RequestParam Long assignmentId,
        @RequestParam Long studentId,
        @RequestParam(required = false) String answer,
        @RequestParam(required = false) MultipartFile file) {
        AssignmentSubmissionDTO saved = submissionService.submitOnline(assignmentId, studentId, answer, file);
        return ResponseEntity.ok(saved);
    }

     @GetMapping("/download/{submissionId}")
    public ResponseEntity<byte[]> downloadSubmittedFile(@PathVariable Long submissionId) {
        return submissionService.downloadFile(submissionId);
    }
}
