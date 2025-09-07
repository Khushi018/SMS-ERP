package com.aristiec.schoolmanagementsystem.dto.announcement;

import java.time.LocalDateTime;

import com.aristiec.schoolmanagementsystem.constant.enums.AnnouncementType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementResponseDTO {
     private Long id;
    private String title;
    private String message;
    private AnnouncementType type;
    private LocalDateTime createdAt;

    private String courseName;
    private String year;

    private String createdBy;
}
