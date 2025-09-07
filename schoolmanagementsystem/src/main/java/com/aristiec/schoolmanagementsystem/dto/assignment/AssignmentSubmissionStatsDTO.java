package com.aristiec.schoolmanagementsystem.dto.assignment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentSubmissionStatsDTO {
    private long totalAssignments;
    private long submittedAssignments;
    private double submissionPercentage;
}
