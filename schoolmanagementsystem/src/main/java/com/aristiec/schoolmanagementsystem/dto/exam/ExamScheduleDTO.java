package com.aristiec.schoolmanagementsystem.dto.exam;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamTypeEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExamScheduleDTO {
    private long examScheduleId;
    private LocalDate startDate;
    private String description;
    private ExamTypeEnum examType;
    private int count;
    private long courseId;
    private int sem;
    private List<LocalDateTime> localDateTimes;
}
