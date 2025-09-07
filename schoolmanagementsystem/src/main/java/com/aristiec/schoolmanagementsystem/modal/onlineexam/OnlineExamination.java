// src/main/java/com/aristiec/schoolmanagementsystem/modal/exam/OnlineExamination.java
package com.aristiec.schoolmanagementsystem.modal.onlineexam;

import java.time.LocalDate;

import com.aristiec.schoolmanagementsystem.modal.admission.Course;
import com.aristiec.schoolmanagementsystem.modal.assignment.Subject;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnlineExamination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Integer totalMarks;
    private Integer totalQuestions;
    private Integer durationInMinutes;
    private LocalDateTime startTime;

    @Column(name = "exam_date")
    private LocalDate examDate;

    @Lob
    private String instructions;

    @ManyToOne
    private Course course;

    @ManyToOne
    private Subject subject;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamContent> contents = new ArrayList<>();

    private Integer semester;

}
