package com.aristiec.schoolmanagementsystem.dto.onlineexam;

import lombok.Data;
import java.util.List;

@Data
public class ExamContentDto {
    private Long id;
    private String question;
    private Integer marks;
    private String questionType;
    private List<String> options;
    private Integer correctIndex;
}
