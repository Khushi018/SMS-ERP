package com.aristiec.schoolmanagementsystem.dto.exam;

import com.aristiec.schoolmanagementsystem.constant.enums.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExamAttendanceCreateDto {
    @NotNull(message = "Student ID cannot be null")
    private Long studentId;

    @NotNull(message = "Exam ID cannot be null")
    private Long examId;

    @NotNull(message = "Attendance Status cannot be null")
    private AttendanceStatus status;
}
