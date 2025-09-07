package com.aristiec.schoolmanagementsystem.dto.exam;

import com.aristiec.schoolmanagementsystem.constant.enums.GradeEnum;
import lombok.Data;

@Data
public class MarksDTO {
    private long studentId;
    private long examId;
    private int marksObtained;
    private GradeEnum grade;
}
