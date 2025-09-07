package com.aristiec.schoolmanagementsystem.dto.recheck;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class RecheckDto {
    private Long id;
    private String studentCode;
    private String subjectName;
    private String examName;
    private Double originalScore;
    private Double expectedScore;
    private String recheckReason;
    private String status;
    private String remarks;
    private Integer recheckFee;
}
