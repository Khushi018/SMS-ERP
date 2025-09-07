package com.aristiec.schoolmanagementsystem.dto.adminReport;

import java.math.BigDecimal;
import java.time.LocalDate;


import com.aristiec.schoolmanagementsystem.constant.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeeStatusDTO {
    private long paidCount;
    private long unpaidCount;
}
