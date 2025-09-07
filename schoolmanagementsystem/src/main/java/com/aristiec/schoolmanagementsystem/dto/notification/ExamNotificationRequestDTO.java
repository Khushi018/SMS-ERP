package com.aristiec.schoolmanagementsystem.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamNotificationRequestDTO {
    private String title;
    private String description;
    private Integer semester;
    private Long courseId;
}
