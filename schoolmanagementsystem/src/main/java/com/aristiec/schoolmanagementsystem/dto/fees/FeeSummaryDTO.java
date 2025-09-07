package com.aristiec.schoolmanagementsystem.dto.fees;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

 @Data
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class FeeSummaryDTO {
    private Long studentId;
    private String studentName;
     private Double totalCourseFee;
    private Double totalPaid;
    private Double remainingFee;
}
