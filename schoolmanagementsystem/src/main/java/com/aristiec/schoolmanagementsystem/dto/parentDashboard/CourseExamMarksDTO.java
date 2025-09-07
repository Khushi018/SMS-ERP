package com.aristiec.schoolmanagementsystem.dto.parentDashboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseExamMarksDTO {
     private String courseName;
    private Map<ExamTypeEnum, List<SubjectMarksDTO>> examTypes = new HashMap<>();

}
