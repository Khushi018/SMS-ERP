package com.aristiec.schoolmanagementsystem.dto.studentSupport;

import java.time.LocalDateTime;

import com.aristiec.schoolmanagementsystem.constant.enums.QueryCategory;
import com.aristiec.schoolmanagementsystem.constant.enums.QuerySendTo;
import com.aristiec.schoolmanagementsystem.constant.enums.QueryStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Data
public class StudentQueryResponseDTO {
     private Long id;
    private QuerySendTo sendTo;
    private QueryCategory category;
    private String subject;
    private String description;
    private QueryStatus status;
    private LocalDateTime submittedAt;
    private LocalDateTime repliedAt;
    private Long studentId;
    private String studentName;
}
