package com.aristiec.schoolmanagementsystem.dto.exam;

import java.time.LocalDateTime;

import com.aristiec.schoolmanagementsystem.constant.enums.AttendanceStatus;
import com.aristiec.schoolmanagementsystem.constant.enums.GradeEnum;

import lombok.Data;

@Data
public class ExamHistoryDTO {
    private String subject;
//    private String examType;
    private LocalDateTime dateTime;
    private int marksObtained;
    private GradeEnum grade;
    private AttendanceStatus attendanceStatus;
}
