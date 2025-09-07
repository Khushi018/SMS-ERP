package com.aristiec.schoolmanagementsystem.modal.assignment;

import java.time.LocalDate;

import com.aristiec.schoolmanagementsystem.constant.enums.AssignmentStatusEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.SubmissionModeEnum;
import com.aristiec.schoolmanagementsystem.modal.admission.Course;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="assignments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDate dueDate;

    private Boolean attempted=false;

      @Enumerated(EnumType.STRING)
    private SubmissionModeEnum submissionMode;


    private Integer sem;
    
    @ManyToOne
    @JoinColumn(name = "subject_id")
    @JsonBackReference
    private Subject subject;

     @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    
}
