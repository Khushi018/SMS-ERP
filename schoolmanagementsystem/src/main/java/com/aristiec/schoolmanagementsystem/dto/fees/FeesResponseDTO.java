package com.aristiec.schoolmanagementsystem.dto.fees;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.aristiec.schoolmanagementsystem.constant.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class FeesResponseDTO {
    private Long feeId;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
    private LocalDate paymentDate;
    private Long studentId;
}
