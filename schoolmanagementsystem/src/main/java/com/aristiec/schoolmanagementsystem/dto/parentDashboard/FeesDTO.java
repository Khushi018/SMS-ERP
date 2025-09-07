package com.aristiec.schoolmanagementsystem.dto.parentDashboard;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Data
public class FeesDTO {
    private BigDecimal amount;
    private String paymentStatus;
    private LocalDate paymentDate;
}
