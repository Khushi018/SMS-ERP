package com.aristiec.schoolmanagementsystem.dto.assignment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfflineSubmissionRequest {
     private Long assignmentId;
    private Long studentId;
}

