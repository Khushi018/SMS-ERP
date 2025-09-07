package com.aristiec.schoolmanagementsystem.modal.exam;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamTypeEnum;
import com.aristiec.schoolmanagementsystem.modal.admission.Course;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "exam_sch")
@NoArgsConstructor
@AllArgsConstructor
public class ExamSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long examScheduleId;

    @Enumerated(EnumType.STRING)
    private ExamTypeEnum examType;

    private int sem;

    private LocalDate startDate;
    private String description;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int count = 0;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private List<LocalDateTime> dateTimeList = new ArrayList<>();

}
