package com.aristiec.schoolmanagementsystem.dto.assignment;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentSubmissionDTO {
     private Long id;
    private String answer;
    private LocalDateTime submittedAt;
    private boolean attempted;
    private boolean submittedLate;
    private Long assignmentId;
    private Long studentId;
}
