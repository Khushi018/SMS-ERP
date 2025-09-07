package com.aristiec.schoolmanagementsystem.modal.onlineexam.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aristiec.schoolmanagementsystem.modal.onlineexam.ExamSession;
public interface ExamSessionRepository extends JpaRepository<ExamSession, Long> {
    Optional<ExamSession> findByStudentIdAndExamId(Long studentId, Long examId);
}
