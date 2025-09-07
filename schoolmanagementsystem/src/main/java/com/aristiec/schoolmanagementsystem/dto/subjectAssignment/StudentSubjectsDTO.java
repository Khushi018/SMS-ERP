package com.aristiec.schoolmanagementsystem.dto.subjectAssignment;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentSubjectsDTO {
    private List<StudentMinimalDTO> subjects;
}
