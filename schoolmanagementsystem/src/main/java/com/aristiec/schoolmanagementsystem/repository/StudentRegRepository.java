package com.aristiec.schoolmanagementsystem.repository;

import com.aristiec.schoolmanagementsystem.modal.persist.StudentRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRegRepository extends JpaRepository<StudentRegistration, Long> {
}
