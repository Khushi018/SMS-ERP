package com.aristiec.schoolmanagementsystem.dto.exam;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamTypeMarksDTO {
    private String examType;
    private List<SubjectMarkDTO> subjects;
      private int totalMarks;
    private int totalObtained;
}
