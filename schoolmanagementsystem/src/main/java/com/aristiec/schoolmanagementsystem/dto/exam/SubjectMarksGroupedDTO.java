package com.aristiec.schoolmanagementsystem.dto.exam;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectMarksGroupedDTO {
     private String subjectName;
    private List<ExamMarksDTO> exams;
    private int totalMarks;
    private int totalObtainedMarks;
}
