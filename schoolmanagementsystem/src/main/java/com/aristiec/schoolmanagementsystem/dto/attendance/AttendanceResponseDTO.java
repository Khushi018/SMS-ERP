package com.aristiec.schoolmanagementsystem.dto.attendance;

import java.time.LocalDate;

import com.aristiec.schoolmanagementsystem.constant.enums.AttendanceStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceResponseDTO {
     private Long id;
    private Long studentId;
    private String studentName;
    private Long timetableId;
    private String subjectName;
    private LocalDate date;
    private AttendanceStatus attendanceStatus;
}
