package com.aristiec.schoolmanagementsystem.modal.onlineexam;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamViolation {
    @Id
    @GeneratedValue
    private Long id;
    private Long studentId;
    private String type; // TAB_SWITCH, MULTIPLE_FACE, etc.
    private LocalDateTime timestamp;
    private Integer multipleFaceCount; // Optional, for MULTIPLE_FACE violations
}
