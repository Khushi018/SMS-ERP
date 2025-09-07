package com.aristiec.schoolmanagementsystem.dto.onlineexam;

import java.time.LocalDateTime;
import java.time.LocalDate;

import lombok.Data;

@Data
public class OnlineExaminationStudentDto {
    private Long id;
    private String title;
    private Integer totalMarks;

    private LocalDate examDate;

    private Integer totalQuestions;
    private Integer durationInMinutes;
    private LocalDateTime startTime;
    private String instructions;
    private Long courseId;
    private Long subjectId;
}
