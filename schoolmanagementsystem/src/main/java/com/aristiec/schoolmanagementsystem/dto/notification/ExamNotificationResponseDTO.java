package com.aristiec.schoolmanagementsystem.dto.notification;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamNotificationResponseDTO {
     private Long id;
    private String title;
    private String description;
    private Integer semester;
    private String courseName;
    private LocalDateTime createdAt;
}
