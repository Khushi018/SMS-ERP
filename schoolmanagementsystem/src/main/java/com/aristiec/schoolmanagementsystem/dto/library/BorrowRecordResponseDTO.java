package com.aristiec.schoolmanagementsystem.dto.library;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowRecordResponseDTO {
     private Long borrowId;

    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private Double fine;

    private Long studentId;
    private String studentName;

    private Long bookCopyId;
    private String bookTitle;
    private String author;
    private String isbn;
}
