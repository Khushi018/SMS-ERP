package com.aristiec.schoolmanagementsystem.dto.subjectAssignment;

import java.util.List;

import com.aristiec.schoolmanagementsystem.dto.FacultyMinimalDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentMinimalDTO {
    private Long id;
    private String name;
    private String code;
    private Integer credit;
    private Integer semester;
    private List<FacultyMinimalDTO> faculties;
}
