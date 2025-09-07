package com.aristiec.schoolmanagementsystem.dto.exam;

import com.aristiec.schoolmanagementsystem.constant.enums.GradeEnum;
import lombok.Data;

@Data
public class MarksReturnDto {
    private long marksId;
    private long studentId;
    private long examId;
    private int marksObtained;
    private GradeEnum grade;
    private String SubjectName;
    private int totalMarks;
}
