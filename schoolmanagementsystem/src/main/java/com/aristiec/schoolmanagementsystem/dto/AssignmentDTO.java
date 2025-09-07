package com.aristiec.schoolmanagementsystem.dto;

import java.time.LocalDate;

import com.aristiec.schoolmanagementsystem.constant.enums.SubmissionModeEnum;
import com.aristiec.schoolmanagementsystem.dto.subjectAssignment.SubjectDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Data
public class AssignmentDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
     private Boolean attempted;
    private SubmissionModeEnum submissionMode;
    private SubjectDto subject;
    
}
