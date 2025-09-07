package com.aristiec.schoolmanagementsystem.modal.exam;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamStatusEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.ExamTypeEnum;
import com.aristiec.schoolmanagementsystem.modal.assignment.Subject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "exam")
@NoArgsConstructor
@AllArgsConstructor
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long examId;
    private String examName;
//    @Enumerated(EnumType.STRING)
//    private ExamTypeEnum examType;

    private LocalDateTime dateTime;
    private int duration; 
    private int marks;
    @Enumerated(EnumType.STRING)
    private ExamStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "exam_schedule_id", nullable = false)
    //@JsonBackReference
    private ExamSchedule examSchedule;

    @OneToMany(mappedBy = "exam")
    private List<Marks> marksList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @PrePersist
    public void prePersist() {
        if (examSchedule == null) {
            throw new IllegalStateException("Exam schedule must be assigned");
        }
       
    }

}
