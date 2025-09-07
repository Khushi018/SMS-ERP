package com.aristiec.schoolmanagementsystem.dto.library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DueBooksDTO {
    private int overdueCount;
    private double totalFine;
}
