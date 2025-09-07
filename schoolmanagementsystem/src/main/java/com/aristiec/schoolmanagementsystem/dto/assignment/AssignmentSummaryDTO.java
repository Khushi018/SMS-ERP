package com.aristiec.schoolmanagementsystem.dto.assignment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentSummaryDTO {
     private int totalAssignments;
    private int totalSubmitted;
    private int totalNotSubmitted;
    private int totalOverdue;
}
