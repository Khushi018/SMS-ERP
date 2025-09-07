package com.aristiec.schoolmanagementsystem.dto.onlineexam;

import lombok.Data;

@Data
public class AnswerDto {
    private Long questionId;
    private String answer;
    private String fileName;
    private String pdfUrl;
}
