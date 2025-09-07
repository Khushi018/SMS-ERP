package com.aristiec.schoolmanagementsystem.dto.attendance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectwiseAttendanceDTO {
     private Long subjectId;
    private String subjectName;
    private Long presentLectures;
    private Long totalLectures;
    private Long missedLectures;
    private double attendancePercentage;
}
