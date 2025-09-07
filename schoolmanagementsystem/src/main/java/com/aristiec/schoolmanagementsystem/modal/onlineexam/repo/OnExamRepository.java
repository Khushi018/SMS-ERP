package com.aristiec.schoolmanagementsystem.modal.onlineexam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aristiec.schoolmanagementsystem.modal.onlineexam.OnlineExam;
public interface OnExamRepository extends JpaRepository<OnlineExam, Long> {
}
