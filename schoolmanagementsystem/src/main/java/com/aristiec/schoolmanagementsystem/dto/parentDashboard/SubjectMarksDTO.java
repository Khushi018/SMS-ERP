package com.aristiec.schoolmanagementsystem.dto.parentDashboard;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectMarksDTO {
    private String subjectName;
    private int marksObtained;
    private String grade;
}
