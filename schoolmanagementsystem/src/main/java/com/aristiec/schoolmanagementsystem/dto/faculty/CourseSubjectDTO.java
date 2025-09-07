package com.aristiec.schoolmanagementsystem.dto.faculty;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSubjectDTO {
     private String courseName;
     private List<SubjectSemesterDTO> subjects;
}
