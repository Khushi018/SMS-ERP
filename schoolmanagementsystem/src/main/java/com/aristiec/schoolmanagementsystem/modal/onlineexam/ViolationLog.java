package com.aristiec.schoolmanagementsystem.modal.onlineexam;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "violation_logs")
@Data
public class ViolationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;
    private Long examId;
    private Long subjectId;

    private LocalDateTime timestamp;
    private Integer faceCount;
    private Boolean isReal; // face is real, it's a photo or video, multiple faces etc.
    @Column
    private String violationType; // e.g. TAB_SWITCH, WINDOW_MINIMIZE, etc.
    @Lob
    private String violationImageBase64;
}

