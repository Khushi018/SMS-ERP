package com.aristiec.schoolmanagementsystem.service.subjectFeedback;

import java.util.List;

import com.aristiec.schoolmanagementsystem.dto.subjectFeedback.FeedbackResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.subjectFeedback.FeedbackrequestDTO;

public  interface FeedbackService {
    FeedbackResponseDTO submitFeedback(FeedbackrequestDTO feedbackDTO);
    List<FeedbackResponseDTO> getFeedbackBySubject(Long subjectId);
    List<FeedbackResponseDTO> getFeedbackByStudent(Long studentId);
    
} 