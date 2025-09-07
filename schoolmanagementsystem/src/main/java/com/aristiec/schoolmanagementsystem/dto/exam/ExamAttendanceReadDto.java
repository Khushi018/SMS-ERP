package com.aristiec.schoolmanagementsystem.dto.exam;

import com.aristiec.schoolmanagementsystem.constant.enums.AttendanceStatus;
import lombok.Data;

@Data
public class ExamAttendanceReadDto {
    private Long id;
    private Long studentId;
    private Long examId;
    private AttendanceStatus status;
}
