package com.aristiec.schoolmanagementsystem.service.exam;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamTypeEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.GradeEnum;
import com.aristiec.schoolmanagementsystem.dto.exam.*;

import java.util.List;

public interface MarksService {

    List<MarksReturnDto> getMarksForExam(long examId);

    MarksReturnDto getMarksForStudentAndExam(Long studentId, long examId);

    MarksReturnDto saveMarksForStudent(Long studentId, long examId, int marksObtained, GradeEnum grade);

    List<Long> getStudentWithGrade(GradeEnum grade);

    void deleteMarksById(Long marksId);

    MarksReturnDto updateMarksAndGrade(Long marksId, GradeEnum grade, int marksObtained);

    List<MarksReturnDto> getMarksForStudentAndExamSch(Long studentId, long schId);
}
