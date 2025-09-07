package com.aristiec.schoolmanagementsystem.service.assignment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.aristiec.schoolmanagementsystem.dto.assignment.AssignmentSubmissionDTO;

public interface AssignmentSubmissionService {
     AssignmentSubmissionDTO submitOffline(Long assignmentId, Long studentId);
    AssignmentSubmissionDTO submitOnline(Long assignmentId, Long studentId, String answer, MultipartFile file);
    
    ResponseEntity<byte[]> downloadFile(Long submissionId);
}
