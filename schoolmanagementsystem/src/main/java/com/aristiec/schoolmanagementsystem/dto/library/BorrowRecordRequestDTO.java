package com.aristiec.schoolmanagementsystem.dto.library;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowRecordRequestDTO {
     private Long studentId;
    private Long bookId; // NOT bookCopyId — we'll auto-pick an available copy
    private LocalDate issueDate;
    private LocalDate dueDate;
}
