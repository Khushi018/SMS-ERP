package com.aristiec.schoolmanagementsystem.dto.subjectFeedback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackrequestDTO {
     private Long studentId;
    private Long subjectId;
    private String feedbackText;
    private int rating;
}
