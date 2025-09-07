package com.aristiec.schoolmanagementsystem.service.exam;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamTypeEnum;
import com.aristiec.schoolmanagementsystem.dto.exam.ExamDTO;
import com.aristiec.schoolmanagementsystem.dto.exam.StudentResultDTO;

import java.util.List;

public interface ExamResultsService {
    List<StudentResultDTO> getStudentResults(Long studentId, Integer semester, ExamTypeEnum examType);

    List<ExamDTO> getExamsAbsentForStudent(Long studentId, int sem);
}
