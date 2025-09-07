package com.aristiec.schoolmanagementsystem.dto.library;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibrarySummaryDTO {
    private int totalBooksIssued;
    private int dueSoonCount;
    private LocalDate nextDueDate;
    private double totalFine;
}
