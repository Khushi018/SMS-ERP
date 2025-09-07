package com.aristiec.schoolmanagementsystem.dto.exam;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamStatusEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.ExamTypeEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExamCreateDto {
    @NotBlank
    private String examName;

//    @NotNull
//    private ExamTypeEnum examType;

    @NotBlank
    private Long subjectId;

    @NotNull
    private LocalDateTime dateTime;

    @Min(1)
    private int duration;

    @Min(1)
    private int marks;

    @NotNull
    private Long examScheduleId;

    @NotNull
    private ExamStatusEnum status;
}
