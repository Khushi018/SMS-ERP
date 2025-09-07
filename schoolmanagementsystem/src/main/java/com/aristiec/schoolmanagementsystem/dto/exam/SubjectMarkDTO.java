package com.aristiec.schoolmanagementsystem.dto.exam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectMarkDTO {
     private String subjectName;
    private int marksObtained;
    private int totalMarks;
    private String grade;
}
