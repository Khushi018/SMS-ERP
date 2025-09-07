package com.aristiec.schoolmanagementsystem.modal.exam;

import com.aristiec.schoolmanagementsystem.constant.enums.AttendanceStatus;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exam_attendance")
public class ExamAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentDetails student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    @PrePersist
    public void prePersist() {
        if (student == null || exam == null) {
            throw new IllegalStateException("Student and Exam must be assigned");
        }

    }
}
