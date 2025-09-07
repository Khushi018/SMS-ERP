package com.aristiec.schoolmanagementsystem.dto.announcement;

import com.aristiec.schoolmanagementsystem.constant.enums.AnnouncementType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AnnouncementRequestDTO {
     private String title;
    private String message;

    private AnnouncementType type;

    private Long courseId;
    private String year;

    private String createdBy;
}
