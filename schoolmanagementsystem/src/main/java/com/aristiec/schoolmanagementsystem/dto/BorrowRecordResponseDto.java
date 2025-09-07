package com.aristiec.schoolmanagementsystem.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BorrowRecordResponseDto {
    private Long recordId;
    private Long bookCopyId;
    private String bookTitle;
    private String author;
    private String bookCondition;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private Double fine;
}
