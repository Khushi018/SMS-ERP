package com.aristiec.schoolmanagementsystem.dto.faculty;

import com.aristiec.schoolmanagementsystem.constant.enums.SectionEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignedSubjectDTO {
     private Long subjectId;
    private String subjectName;
    private Integer semester;
    private SectionEnum section;
    private String courseName;
}
