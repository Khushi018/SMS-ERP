package com.aristiec.schoolmanagementsystem.dto.faculty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectSemesterDTO {
    private String subjectName;
    private int semester;
}
