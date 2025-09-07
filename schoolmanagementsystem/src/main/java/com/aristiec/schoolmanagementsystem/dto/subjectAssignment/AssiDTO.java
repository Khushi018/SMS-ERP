package com.aristiec.schoolmanagementsystem.dto.subjectAssignment;

import com.aristiec.schoolmanagementsystem.constant.enums.AssignmentStatusEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.SubmissionModeEnum;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssiDTO {
     private Long id;
    private String title;
    private String description;
    private String dueDate;
     private Boolean attempted;
    private SubmissionModeEnum submissionMode;
    private AssignmentStatusEnum status;
    private String subjectName;
}
