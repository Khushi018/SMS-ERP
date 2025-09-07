package com.aristiec.schoolmanagementsystem.dto.notification;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseDTO {
    private String title;
    private String description;
    private LocalDateTime createdAt;
}
