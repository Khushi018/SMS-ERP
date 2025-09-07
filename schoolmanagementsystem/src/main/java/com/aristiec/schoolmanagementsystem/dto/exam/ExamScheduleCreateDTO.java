package com.aristiec.schoolmanagementsystem.dto.exam;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamTypeEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExamScheduleCreateDTO {
    private LocalDate startDate;
    private String description;
    private Long courseId;
    private ExamTypeEnum examType;
    private int sem;
}
