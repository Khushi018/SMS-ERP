package com.aristiec.schoolmanagementsystem.modal.subjectFeedback;

import java.time.LocalDateTime;

import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.assignment.Subject;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {
    
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     @ManyToOne
    private StudentDetails studentDetails;

     @ManyToOne
    private Subject subject;

    private String feedbackText;
    private int rating;
    private LocalDateTime submittedAt;
}
