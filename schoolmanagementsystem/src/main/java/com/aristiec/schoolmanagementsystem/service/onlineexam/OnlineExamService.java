package com.aristiec.schoolmanagementsystem.service.onlineexam;

import java.time.LocalDate;

import com.aristiec.schoolmanagementsystem.dto.onlineexam.*;

import java.util.List;

public interface OnlineExamService {
    OnlineExaminationDto createExam(OnlineExaminationDto dto);

    ExamContentDto addContent(Long examId, ExamContentDto dto);

    List<OnlineExaminationDto> getAllExams(Long courseId, Long subjectId, LocalDate startDate,
            LocalDate endDate);

    List<OnlineExaminationDto> getExamsByCourseAndSemester(Long courseId, Integer semester);

    List<OnlineExaminationStudentDto> getAllExamsForStudent(Long studentId);

    List<ExamContentDto> getContentsWithAnswersByExamId(Long examId);

    OnlineExaminationDto getExamById(Long id);

    List<ExamContentDto> getOnlyContentsByExamId(Long examId);

    ExamSubmissionDto submitExam(Long examId, SubmitExamDto dto);

    ExamSubmissionDto getSubmission(Long submissionId);

    List<ExamSubmissionDto> listSubmissions(Long examId, Long studentId);

    ExamSubmissionDto gradeSubmission(Long examId, Long submissionId, GradeDto grade);

    void deleteAllExams();
}
