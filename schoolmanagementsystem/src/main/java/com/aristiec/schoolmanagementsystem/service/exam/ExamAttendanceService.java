package com.aristiec.schoolmanagementsystem.service.exam;

import com.aristiec.schoolmanagementsystem.dto.StudentDetailsResponseDto;
import com.aristiec.schoolmanagementsystem.dto.exam.ExamAttendanceCreateDto;
import com.aristiec.schoolmanagementsystem.dto.exam.ExamAttendanceReadDto;
import com.aristiec.schoolmanagementsystem.modal.exam.Exam;
import com.aristiec.schoolmanagementsystem.modal.exam.ExamAttendance;
import jakarta.validation.Valid;

import java.util.List;

public interface ExamAttendanceService {

    ExamAttendanceReadDto createExamAttendance(@Valid ExamAttendanceCreateDto createDto);

    ExamAttendanceReadDto getExamAttendance(Long studentId, Long examId);

    List<ExamAttendanceReadDto> getAllExamAttendances();

    List<ExamAttendanceReadDto> getExamAttendanceByExam(Long examId);

    List<ExamAttendanceReadDto> getExamAttendanceByStudent(Long studentId);

    void deleteExamAttendance(Long studentId, Long examId);

    List<ExamAttendanceReadDto> getStudentStatus(Long studentId, int sem);
}
