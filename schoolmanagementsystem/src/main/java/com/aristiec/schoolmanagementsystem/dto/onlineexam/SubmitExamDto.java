package com.aristiec.schoolmanagementsystem.dto.onlineexam;

import lombok.Data;
import java.util.List;

@Data
public class SubmitExamDto {
    private Long studentId;
    private List<AnswerDto> answers;
}
