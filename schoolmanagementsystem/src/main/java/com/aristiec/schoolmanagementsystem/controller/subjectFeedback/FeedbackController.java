package com.aristiec.schoolmanagementsystem.controller.subjectFeedback;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aristiec.schoolmanagementsystem.dto.subjectFeedback.FeedbackResponseDTO;
import com.aristiec.schoolmanagementsystem.dto.subjectFeedback.FeedbackrequestDTO;
import com.aristiec.schoolmanagementsystem.service.subjectFeedback.FeedbackService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/feedback")

@SecurityRequirement(name = "bearerAuth") 
public class FeedbackController {

     @Autowired
    private FeedbackService feedbackService;

      @PostMapping("/submit")
    public ResponseEntity<FeedbackResponseDTO>submitFeedback(@RequestBody FeedbackrequestDTO dto){
        FeedbackResponseDTO response=feedbackService.submitFeedback(dto);
        return ResponseEntity.ok(response);
    }


     @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<FeedbackResponseDTO>>getFeedbackBysubject(@PathVariable Long subjectId){
        List<FeedbackResponseDTO>feedbackList=feedbackService.getFeedbackBySubject(subjectId);
        return ResponseEntity.ok(feedbackList);
    }
     
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<FeedbackResponseDTO>> getFeedbackByStudent(@PathVariable Long studentId) {
        List<FeedbackResponseDTO> feedbackList = feedbackService.getFeedbackByStudent(studentId);
        return ResponseEntity.ok(feedbackList);
    }

    
    
}
