package com.aristiec.schoolmanagementsystem.dto.exam;

import com.aristiec.schoolmanagementsystem.constant.enums.GradeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StudentResultDTO {
    private String examName;
    private String subject;
    private LocalDateTime dateTime;
    private int duration;
    private int marksObtained;
    private GradeEnum grade;
}
