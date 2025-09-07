package com.aristiec.schoolmanagementsystem.dto.exam;

import java.time.LocalDateTime;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamStatusEnum;
import com.aristiec.schoolmanagementsystem.modal.assignment.Subject;
import lombok.Data;

@Data
public class ExamDTO {

    private long examId;
    private String examName;
//    private String examType;
    private Long subjectId;
    private LocalDateTime dateTime;
    private int duration;
    private int marks;
    private long examScheduleId; // Only the ID, not full object
    private ExamStatusEnum status;

}
