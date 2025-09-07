package com.aristiec.schoolmanagementsystem.repository.onlineexam;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aristiec.schoolmanagementsystem.modal.onlineexam.ExamSubmission;

public interface ExamSubmissionRepository extends JpaRepository<ExamSubmission, Long> {
    List<ExamSubmission> findByExamId(Long examId);

    List<ExamSubmission> findByStudentId(Long studentId);

    Optional<ExamSubmission> findByIdAndExamId(Long id, Long examId);
}
