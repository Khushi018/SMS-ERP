package com.aristiec.schoolmanagementsystem.modal.exam;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.aristiec.schoolmanagementsystem.constant.enums.GradeEnum;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "marks")
@NoArgsConstructor
@AllArgsConstructor
public class Marks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long marksId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private StudentDetails student;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    private int marksObtained;
    @Enumerated(EnumType.STRING)
    private GradeEnum grade;

    @PrePersist
    public void prePersist() {
        if (student == null || exam == null) {
            throw new IllegalStateException("Student and Exam must be assigned");
        }
    }
}
