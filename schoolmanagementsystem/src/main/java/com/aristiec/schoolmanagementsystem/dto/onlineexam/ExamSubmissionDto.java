package com.aristiec.schoolmanagementsystem.dto.onlineexam;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExamSubmissionDto {
    private Long id;
    private Long studentId;
    private Long examId;
    private Integer score;
    private LocalDateTime submittedAt;
    private List<AnswerDto> answers;
}
