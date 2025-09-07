package com.aristiec.schoolmanagementsystem.dto.studentSupport;

import java.time.LocalDateTime;

import com.aristiec.schoolmanagementsystem.constant.enums.QueryCategory;
import com.aristiec.schoolmanagementsystem.constant.enums.QueryStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentQueryFilterResponseDTO {
     private String subject;
    private QueryCategory category;
    private QueryStatus status;
    private LocalDateTime submittedAt;
}
