package com.aristiec.schoolmanagementsystem.repository.onlineexam;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aristiec.schoolmanagementsystem.modal.onlineexam.ExamContent;

public interface ExamContentRepository extends JpaRepository<ExamContent, Long> {
}
