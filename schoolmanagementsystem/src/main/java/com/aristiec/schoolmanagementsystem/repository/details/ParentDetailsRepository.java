package com.aristiec.schoolmanagementsystem.repository.details;

import com.aristiec.schoolmanagementsystem.modal.admission.ParentDetails;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface ParentDetailsRepository extends JpaRepository<ParentDetails, Long> {
    @Query("SELECT p FROM ParentDetails p WHERE p.studentDetails.id = :studentId")
    ParentDetails findByStudentId(@Param("studentId") Long studentId);
        Optional<ParentDetails> findByEmail(String email);


}