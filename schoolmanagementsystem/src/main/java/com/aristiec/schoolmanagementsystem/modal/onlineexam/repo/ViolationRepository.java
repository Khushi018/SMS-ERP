package com.aristiec.schoolmanagementsystem.modal.onlineexam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aristiec.schoolmanagementsystem.modal.onlineexam.ExamViolation;

import java.util.List;

public interface ViolationRepository extends JpaRepository<ExamViolation, Long> {
    List<ExamViolation> findByStudentId(Long studentId);
}