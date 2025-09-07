package com.aristiec.schoolmanagementsystem.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseSubjectMinimalDTO {
    private String studentName;
    private String studentCode;

    private String courseName;
    private String courseCode;

    private List<SubjectMinimalDTO> subjects;
}
