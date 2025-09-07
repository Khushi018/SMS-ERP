package com.aristiec.schoolmanagementsystem.repository.assignment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aristiec.schoolmanagementsystem.modal.assignment.AssignmentSubmission;

@Repository
public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission,Long> {
    List<AssignmentSubmission> findByStudentId(Long studentId);
    
    
  @Query("SELECT COUNT(s) FROM AssignmentSubmission s WHERE s.student.id = :studentId")
long countByStudentId(@Param("studentId") Long studentId);

@Query("""
    SELECT a
    FROM AssignmentSubmission a
    WHERE a.student.id = :studentId
    AND a.assignment.subject.semester = :semester
""")
List<AssignmentSubmission> findByStudentIdAndSemester(Long studentId, Integer semester);



}
