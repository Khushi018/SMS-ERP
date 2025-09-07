package com.aristiec.schoolmanagementsystem.modal.recheck;

import com.aristiec.schoolmanagementsystem.constant.enums.RecheckEnum;
import com.aristiec.schoolmanagementsystem.modal.admission.StudentDetails;
import com.aristiec.schoolmanagementsystem.modal.assignment.Subject;
import com.aristiec.schoolmanagementsystem.modal.exam.Exam;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentDetails student;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;
    private Double originalScore;
    private Double expectedScore;
    private String recheckReason;

    @Lob
    private byte[] proof;
    private Integer recheckFee = 500;

    @Enumerated(EnumType.STRING)
    private RecheckEnum status = RecheckEnum.PENDING;

    private String remarks="PENDING";

}
