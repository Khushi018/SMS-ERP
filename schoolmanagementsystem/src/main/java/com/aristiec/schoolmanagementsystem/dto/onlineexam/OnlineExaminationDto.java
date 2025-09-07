package com.aristiec.schoolmanagementsystem.dto.onlineexam;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

@Data
public class OnlineExaminationDto {
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
    private Integer semester;
    private List<ExamContentDto> contents = new ArrayList<>();
}
