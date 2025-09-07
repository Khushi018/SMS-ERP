package com.aristiec.schoolmanagementsystem.dto.announcement;

import java.time.LocalDateTime;

import com.aristiec.schoolmanagementsystem.constant.enums.AnnouncementType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentAnnouncementResponseDTO {
     private String title;
    private String message;
    private LocalDateTime createdAt;
    private String postedBy;
    private AnnouncementType type;
}
