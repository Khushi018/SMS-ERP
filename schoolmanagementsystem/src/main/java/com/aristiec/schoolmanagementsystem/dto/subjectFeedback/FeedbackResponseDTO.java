package com.aristiec.schoolmanagementsystem.dto.subjectFeedback;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponseDTO {
    private Long id;
    private String studentCode;
    private String subjectName;
    private String feedbackText;
    private int rating;
    private LocalDateTime submittedAt;
}
