package com.aristiec.schoolmanagementsystem.dto.recheck;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RecheckCreateDto {
    private Long studentId;
    private Long subjectId;
    private Long examId;
    private Double originalScore;
    private Double expectedScore;
    private String recheckReason;
    //private byte[] proof;
}
