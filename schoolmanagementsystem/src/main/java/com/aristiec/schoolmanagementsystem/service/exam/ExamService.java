package com.aristiec.schoolmanagementsystem.service.exam;

import java.util.Arrays;
import java.util.List;

import com.aristiec.schoolmanagementsystem.constant.enums.ExamStatusEnum;
import com.aristiec.schoolmanagementsystem.constant.enums.ExamTypeEnum;
import com.aristiec.schoolmanagementsystem.dto.exam.ExamDTO;
import com.aristiec.schoolmanagementsystem.dto.exam.ExamHistoryDTO;
import com.aristiec.schoolmanagementsystem.modal.exam.Exam;

public interface ExamService {
    Exam createExam(Exam exam);

    List<Exam> getAllExams();

    Exam getExamById(long id);

    void deleteById(long id);

    List<Exam> getScheduleExams(Long id);

    List<Exam> getExamsByCourseId(Long courseId);

    List<Exam> getExamsByExamType(ExamTypeEnum examType);

    List<ExamDTO> getExamsByStudentId(Long studentId);

    List<ExamDTO> getExamsByStudentIdAndType(Long studentId, ExamTypeEnum examType);

    List<ExamHistoryDTO> getExamHistory(Long studentId);

    List<Exam> getExamsByExamStatus(ExamStatusEnum examStatus);

    List<Exam> getExamsByScheduleIdAndType(Long scheduleId, ExamTypeEnum examType);

    ExamDTO updateExamStatus(long examId, ExamStatusEnum status);

    List<ExamDTO> getUpcomingExams();

    boolean markExamAsCompletedAndStudentsPresent(Long examId, List<Long> studentIds);

    List<ExamDTO> updateExamStatusIfExpired();

    List<ExamDTO> getCompletedExams();
}
