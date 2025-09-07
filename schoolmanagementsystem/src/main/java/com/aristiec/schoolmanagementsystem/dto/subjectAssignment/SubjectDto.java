package com.aristiec.schoolmanagementsystem.dto.subjectAssignment;

import com.aristiec.schoolmanagementsystem.dto.CourseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Data
public class SubjectDto {
    private Long id;
     private String name;
    private String code;
    private CourseDTO course;
}
