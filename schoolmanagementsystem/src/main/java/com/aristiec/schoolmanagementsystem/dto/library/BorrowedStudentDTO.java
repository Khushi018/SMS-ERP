package com.aristiec.schoolmanagementsystem.dto.library;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowedStudentDTO {
     private Long studentId;
    private String studentName;
    private LocalDate issueDate;
    private LocalDate returnDate;
    private Double fine;
}
