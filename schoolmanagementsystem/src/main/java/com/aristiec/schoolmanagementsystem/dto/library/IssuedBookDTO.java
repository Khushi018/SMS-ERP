package com.aristiec.schoolmanagementsystem.dto.library;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssuedBookDTO {
    private String bookTitle;
    private String author;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private String imageUrl;
}
