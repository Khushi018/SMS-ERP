package com.aristiec.schoolmanagementsystem.dto.assignment;

import java.time.LocalDate;

import com.aristiec.schoolmanagementsystem.constant.enums.SubmissionModeEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentRequestDTO {
     private String title;
    private String description;
    private LocalDate dueDate;
    private SubmissionModeEnum submissionMode;
    private Long subjectId;
    private Integer sem;         
    private Long courseId; 
}
