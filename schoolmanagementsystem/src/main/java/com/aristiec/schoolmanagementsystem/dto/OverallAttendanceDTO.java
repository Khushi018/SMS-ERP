package com.aristiec.schoolmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverallAttendanceDTO {
     private Long studentId;
    private String studentName; 
    private Long presentLectures;
    private Long totalLectures;
    private Long missedLectures;
    private double attendancePercentage;
}
