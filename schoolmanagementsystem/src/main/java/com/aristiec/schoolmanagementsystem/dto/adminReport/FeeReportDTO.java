package com.aristiec.schoolmanagementsystem.dto.adminReport;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeeReportDTO {
     private BigDecimal totalFeeAmount;
}
