package com.aristiec.schoolmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SemesterPerformanceDTO {
     private Long studentId;
    private Integer semester;

    private Double attendancePercentage;
    private Double assignmentSubmissionPercentage;

    private Integer midtermObtained;
    private Integer midtermTotal;

    private Integer finalObtained;
    private Integer finalTotal;

    private Integer miniTestObtained;
    private Integer miniTestTotal;
}
