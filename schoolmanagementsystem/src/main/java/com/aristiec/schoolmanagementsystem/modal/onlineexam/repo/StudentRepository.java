package com.aristiec.schoolmanagementsystem.modal.onlineexam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aristiec.schoolmanagementsystem.modal.onlineexam.Student;
public interface StudentRepository extends JpaRepository<Student, Long> {
}
